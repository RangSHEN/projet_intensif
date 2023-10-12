package com.rang.selfstarter.service;

import com.rang.selfstarter.dto.ClientDTO;
import com.rang.selfstarter.entity.Client;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {

    Client loadClientById(Long clientId);

    Page<ClientDTO> findClientsByName(String name, int page, int size);

    ClientDTO loadClientByEmail(String email);

    ClientDTO createClient(ClientDTO clientDTO);

    ClientDTO updateClient(ClientDTO clientDTO);

    List<ClientDTO> fetchClients();

    void removeClient(Long clientId);
}
