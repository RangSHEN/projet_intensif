package com.rang.selfstarter.service.impl;

import com.rang.selfstarter.dao.ClientDao;
import com.rang.selfstarter.dao.EntrepreneurDao;
import com.rang.selfstarter.dao.FactureDao;
import com.rang.selfstarter.dto.FactureDTO;
import com.rang.selfstarter.entity.Client;
import com.rang.selfstarter.entity.Entrepreneur;
import com.rang.selfstarter.entity.Facture;
import com.rang.selfstarter.mapper.FactureMapper;
import com.rang.selfstarter.service.FactureService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.stream.Collectors;

@Transactional
@Service
public class FactureServiceImpl implements FactureService {

    private FactureDao factureDao;
    private FactureMapper factureMapper;
    private EntrepreneurDao entrepreneurDao;
    private ClientDao clientDao;

    public FactureServiceImpl(FactureDao factureDao, FactureMapper factureMapper, EntrepreneurDao entrepreneurDao, ClientDao clientDao) {
        this.factureDao = factureDao;
        this.factureMapper = factureMapper;
        this.entrepreneurDao = entrepreneurDao;
        this.clientDao = clientDao;
    }

    @Override
    public Facture loadFactureById(Long factureId) {
        return factureDao.findById(factureId).orElseThrow(()->new EntityNotFoundException("Facture with ID"+ factureId +"Not Found"));
    }

    @Override
    public FactureDTO createFacture(FactureDTO factureDTO) {
        Facture facture = factureMapper.fromFactureDTO(factureDTO);
        Entrepreneur entrepreneur = entrepreneurDao.findById(factureDTO.getEntrepreneur().getEntrepreneurId()).orElseThrow(
                () -> new EntityNotFoundException("Entrepreneur with Id" + factureDTO.getEntrepreneur().getEntrepreneurId() + "Not Found"));
        Client client = clientDao.findById(factureDTO.getClient().getClientId()).orElseThrow(() -> new EntityNotFoundException("Entrepreneur with Id" + factureDTO.getClient().getClientId() + "Not Found"));
        facture.setEntrepreneur(entrepreneur);
        facture.setClient(client);
        return factureMapper.fromFacture(facture);
    }

    @Override
    public FactureDTO updateFacture(FactureDTO factureDTO) {
        Facture facture = loadFactureById(factureDTO.getFactureId());
        Entrepreneur entrepreneur = entrepreneurDao.findById(factureDTO.getEntrepreneur().getEntrepreneurId()).orElseThrow(
                () -> new EntityNotFoundException("Entrepreneur with Id" + factureDTO.getEntrepreneur().getEntrepreneurId() + "Not Found"));
        Client client = clientDao.findById(factureDTO.getClient().getClientId()).orElseThrow(() -> new EntityNotFoundException("Entrepreneur with Id" + factureDTO.getClient().getClientId() + "Not Found"));
        facture.setEntrepreneur(entrepreneur);
        facture.setClient(client);
        Facture updatedFacture = factureDao.save(facture);
        return factureMapper.fromFacture(updatedFacture);
    }

    @Override
    public Page<FactureDTO> findFactureByFactureName(String keyword, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Facture> facturesPage = factureDao.findFacturesByFactureNameContains(keyword, pageRequest);
        return new PageImpl<>(facturesPage.getContent().stream().map(facture -> factureMapper.fromFacture(facture)).collect(Collectors.toList()),pageRequest,facturesPage.getTotalElements());
    }

    @Override
    public Page<FactureDTO> fetchFacturesForClient(Long clientId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Facture> clientFacturesPage = factureDao.getFacturesByClientId(clientId, pageRequest);
        return new PageImpl<>(clientFacturesPage.getContent().stream().map(facture -> factureMapper.fromFacture(facture)).collect(Collectors.toList()),pageRequest,clientFacturesPage.getTotalElements());
    }

    @Override
    public Page<FactureDTO> fetchFacturesForEntrepreneur(Long entrepreneurId, int page, int size) {

        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Facture> entrepreneurFacturesPage = factureDao.getFacturesByEntrepreneurId(entrepreneurId, pageRequest);
        return new PageImpl<>(entrepreneurFacturesPage.getContent().stream().map(facture -> factureMapper.fromFacture(facture)).collect(Collectors.toList()),pageRequest,entrepreneurFacturesPage.getTotalElements());
    }

    @Override
    public void removeFacture(Long factureId) {
        factureDao.deleteById(factureId);
    }
}
