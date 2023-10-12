package com.rang.selfstarter.service.impl;

import com.rang.selfstarter.dao.ClientDao;
import com.rang.selfstarter.dao.DevisDao;
import com.rang.selfstarter.dao.EntrepreneurDao;
import com.rang.selfstarter.dao.FactureDao;
import com.rang.selfstarter.dto.DevisDTO;
import com.rang.selfstarter.entity.Client;
import com.rang.selfstarter.entity.Devis;
import com.rang.selfstarter.entity.Entrepreneur;
import com.rang.selfstarter.entity.Facture;
import com.rang.selfstarter.mapper.DevisMapper;
import com.rang.selfstarter.mapper.FactureMapper;
import com.rang.selfstarter.service.DevisService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Service
@Transactional
public class DevisServiceImpl implements DevisService {

    private DevisDao devisDao;
    private DevisMapper devisMapper;
    private EntrepreneurDao entrepreneurDao;
    private ClientDao clientDao;

    public DevisServiceImpl(DevisDao devisDao, DevisMapper devisMapper, EntrepreneurDao entrepreneurDao, ClientDao clientDao) {
        this.devisDao = devisDao;
        this.devisMapper = devisMapper;
        this.entrepreneurDao = entrepreneurDao;
        this.clientDao = clientDao;
    }

    @Override
    public Devis loadDevisById(Long devisId) {
        return devisDao.findById(devisId).orElseThrow(()->new EntityNotFoundException("Facture with ID"+ devisId +"Not Found"));
    }

    @Override
    public DevisDTO createDevis(DevisDTO devisDTO) {
        Devis devis = devisMapper.fromDevisDTO(devisDTO);
        Entrepreneur entrepreneur = entrepreneurDao.findById(devisDTO.getEntrepreneur().getEntrepreneurId()).orElseThrow(
                () -> new EntityNotFoundException("Entrepreneur with Id" + devisDTO.getEntrepreneur().getEntrepreneurId() + "Not Found"));
        Client client = clientDao.findById(devisDTO.getClient().getClientId()).orElseThrow(() -> new EntityNotFoundException("Entrepreneur with Id" + devisDTO.getClient().getClientId() + "Not Found"));
        devis.setEntrepreneur(entrepreneur);
        devis.setClient(client);
        return devisMapper.fromDevis(devis);
    }

    @Override
    public DevisDTO updateFacture(DevisDTO devisDTO) {
        Devis devis = loadDevisById(devisDTO.getDevisId());
        Entrepreneur entrepreneur = entrepreneurDao.findById(devisDTO.getEntrepreneur().getEntrepreneurId()).orElseThrow(
                () -> new EntityNotFoundException("Entrepreneur with Id" + devisDTO.getEntrepreneur().getEntrepreneurId() + "Not Found"));
        Client client = clientDao.findById(devisDTO.getClient().getClientId()).orElseThrow(() -> new EntityNotFoundException("Entrepreneur with Id" + devisDTO.getClient().getClientId() + "Not Found"));
        devis.setEntrepreneur(entrepreneur);
        devis.setClient(client);
        Devis updatedDevis = devisDao.save(devis);
        return devisMapper.fromDevis(updatedDevis);
    }

    @Override
    public Page<DevisDTO> findDevisByDevisName(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Devis> devisPage = devisDao.findDevisByDevisNameContains(keyword,pageRequest);
        return new PageImpl<>(devisPage.getContent().stream().map(devis-> devisMapper.fromDevis(devis)).collect(Collectors.toList()),pageRequest,devisPage.getTotalElements());
    }

    @Override
    public Page<DevisDTO> fetchDevisForClient(Long clientId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Devis> clientDevisPage = devisDao.getDevisByClientId(clientId, pageRequest);
        return new PageImpl<>(clientDevisPage .getContent().stream().map(devis -> devisMapper.fromDevis(devis)).collect(Collectors.toList()),pageRequest,clientDevisPage.getTotalElements());
    }

    @Override
    public Page<DevisDTO> fetchDevisForEntrepreneur(Long entrepreneurId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Devis> entrepreneurFacturesPage = devisDao.getDevisByEntrepreneurId(entrepreneurId, pageRequest);
        return new PageImpl<>(entrepreneurFacturesPage.getContent().stream().map(devis -> devisMapper.fromDevis(devis)).collect(Collectors.toList()),pageRequest,entrepreneurFacturesPage.getTotalElements());
    }

    @Override
    public void removeDevis(Long devisId) {
        devisDao.deleteById(devisId);
    }
}
