package com.rang.selfstarter.service.impl;

import com.rang.selfstarter.dao.ClientDao;
import com.rang.selfstarter.dao.EntrepreneurDao;
import com.rang.selfstarter.dto.EntrepreneurDTO;
import com.rang.selfstarter.entity.Client;
import com.rang.selfstarter.entity.Entrepreneur;
import com.rang.selfstarter.entity.User;
import com.rang.selfstarter.mapper.ClientMapper;
import com.rang.selfstarter.mapper.EntrepreneurMapper;
import com.rang.selfstarter.service.DevisService;
import com.rang.selfstarter.service.EntrepreneurService;
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

@Transactional
@Service
public class EntrepreneurServiceImpl implements EntrepreneurService {

    private EntrepreneurDao entrepreneurDao;

    private EntrepreneurMapper entrepreneurMapper;

    private UserService userService;

    private FactureService factureService;

    private DevisService devisService;

    public EntrepreneurServiceImpl(EntrepreneurDao entrepreneurDao, EntrepreneurMapper entrepreneurMapper, UserService userService, FactureService factureService, DevisService devisService) {
        this.entrepreneurDao = entrepreneurDao;
        this.entrepreneurMapper = entrepreneurMapper;
        this.userService = userService;
        this.factureService = factureService;
        this.devisService = devisService;
    }

    @Override
    public Entrepreneur loadEntrepreneurById(Long entrepreneurId) {
        return entrepreneurDao.findById(entrepreneurId).orElseThrow(() -> new EntityNotFoundException("Instructor with ID" + entrepreneurId + " not found"));
    }

    @Override
    public Page<EntrepreneurDTO> findEntrepreneursByName(String name, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Entrepreneur> entrepreneurPage = entrepreneurDao.findEntrepreneursByName(name, pageRequest);
        return new PageImpl<>(entrepreneurPage.getContent().stream().map(entrepreneur -> entrepreneurMapper.fromEntrepreneur(entrepreneur)).collect(Collectors.toList()),pageRequest,entrepreneurPage.getTotalElements());
    }

    @Override
    public EntrepreneurDTO loadEntrepreneurByEmail(String email) {
        Entrepreneur entrepreneursByEmail = entrepreneurDao.findEntrepreneursByEmail(email);
        return entrepreneurMapper.fromEntrepreneur(entrepreneursByEmail);
    }

    @Override
    public EntrepreneurDTO createEntrepreneur(EntrepreneurDTO entrepreneurDTO) {
        User user = userService.createUser(entrepreneurDTO.getUser().getEmail(), entrepreneurDTO.getUser().getPassword());
        userService.assignRoleToUser(user.getEmail(),"Entrepreneur");
        Entrepreneur entrepreneur = entrepreneurMapper.fromEntrepreneurDTO(entrepreneurDTO);
        entrepreneur.setUser(user);
        Entrepreneur savedEntrepreneur = entrepreneurDao.save(entrepreneur);
        return entrepreneurMapper.fromEntrepreneur(savedEntrepreneur);
    }

    @Override
    public EntrepreneurDTO updateEntrepreneur(EntrepreneurDTO entrepreneurDTO) {
        Entrepreneur loadedEntrepreneur = loadEntrepreneurById(entrepreneurDTO.getEntrepreneurId());
        Entrepreneur entrepreneur = entrepreneurMapper.fromEntrepreneurDTO(entrepreneurDTO);
        entrepreneur.setUser(loadedEntrepreneur.getUser());
        entrepreneur.setFactures(loadedEntrepreneur.getFactures());
        entrepreneur.setDevis(loadedEntrepreneur .getDevis());
        Entrepreneur updatedEntrepreneur =  entrepreneurDao.save( entrepreneur);
        return entrepreneurMapper.fromEntrepreneur(updatedEntrepreneur);
    }

    @Override
    public List<EntrepreneurDTO> fetchEntrepreneurs() {
        return  entrepreneurDao.findAll().stream().map(entrepreneur ->  entrepreneurMapper.fromEntrepreneur(entrepreneur)).collect(Collectors.toList());
    }

    @Override
    public void removeEntrepreneur(Long entrepreneurId) {

        Entrepreneur entrepreneur = loadEntrepreneurById(entrepreneurId);
        entrepreneur.getDevis().forEach(devis -> devisService.removeDevis(devis.getDevisId()));
        entrepreneur.getFactures().forEach(facture -> factureService.removeFacture(facture.getFactureId()));

        entrepreneurDao.deleteById(entrepreneurId);
    }
}
