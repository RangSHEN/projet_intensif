package com.rang.selfstarter.service.impl;

import com.rang.selfstarter.dao.ClientDao;
import com.rang.selfstarter.dto.ClientDTO;
import com.rang.selfstarter.entity.Client;
import com.rang.selfstarter.entity.User;
import com.rang.selfstarter.mapper.ClientMapper;
import com.rang.selfstarter.service.ClientService;
import com.rang.selfstarter.service.DevisService;
import com.rang.selfstarter.service.FactureService;
import com.rang.selfstarter.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {


    private ClientDao clientDao;

    private ClientMapper clientMapper;

    private UserService userService;

    private FactureService factureService;

    private DevisService devisService;

    public ClientServiceImpl(ClientDao clientDao, ClientMapper clientMapper, UserService userService, FactureService factureService, DevisService devisService) {
        this.clientDao = clientDao;
        this.clientMapper = clientMapper;
        this.userService = userService;
        this.factureService = factureService;
        this.devisService = devisService;
    }

    @Override
    public Client loadClientById(Long clientId) {
        return clientDao.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Instructor with ID" + clientId + " not found"));
    }

    @Override
    public Page<ClientDTO> findClientsByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Client> clientPage = clientDao.findClientsByName(name, pageRequest);
        return new PageImpl<>(clientPage.getContent().stream().map(client -> clientMapper.fromClient(client)).collect(Collectors.toList()),pageRequest,clientPage.getTotalElements());
    }

    @Override
    public ClientDTO loadClientByEmail(String email) {
        Client clientsByEmail = clientDao.findClientsByEmail(email);
        return clientMapper.fromClient(clientsByEmail);
    }

    @Override
    public ClientDTO createClient(ClientDTO clientDTO) {
        User user = userService.createUser(clientDTO.getUser().getEmail(), clientDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(),"Client");
        Client client = clientMapper.fromClientDTO(clientDTO);
        client.setUser(user);
        Client savedClient = clientDao.save(client);
        return clientMapper.fromClient(savedClient);
    }

    @Override
    public ClientDTO updateClient(ClientDTO clientDTO) {
        Client loadedClient = loadClientById(clientDTO.getClientId());
        Client client = clientMapper.fromClientDTO(clientDTO);
        client.setUser(loadedClient.getUser());
        client.setFactures(loadedClient.getFactures());
        client.setDevis(loadedClient.getDevis());
        Client updatedClient = clientDao.save(client);
        return clientMapper.fromClient(updatedClient);

    }

    @Override
    public List<ClientDTO> fetchClients() {
        return clientDao.findAll().stream().map(client -> clientMapper.fromClient(client)).collect(Collectors.toList());
    }

    @Override
    public void removeClient(Long clientId) {
        Client client = loadClientById(clientId);
        client.getDevis().forEach(devis -> devisService.removeDevis(devis.getDevisId()));
        client.getFactures().forEach(facture -> factureService.removeFacture(facture.getFactureId()));

        clientDao.deleteById(clientId);
    }
}
