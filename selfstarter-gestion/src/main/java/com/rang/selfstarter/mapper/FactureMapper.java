package com.rang.selfstarter.mapper;

import com.rang.selfstarter.dto.FactureDTO;
import com.rang.selfstarter.entity.Facture;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class FactureMapper {

    private EntrepreneurMapper entrepreneurMapper;

    private ClientMapper clientMapper;

    public FactureMapper(EntrepreneurMapper entrepreneurMapper, ClientMapper clientMapper) {
        this.entrepreneurMapper = entrepreneurMapper;
        this.clientMapper = clientMapper;
    }

    public FactureDTO fromFacture(Facture facture){
        FactureDTO factureDTO = new FactureDTO();
        BeanUtils.copyProperties(facture,factureDTO);
        factureDTO.setEntrepreneur(entrepreneurMapper.fromEntrepreneur(facture.getEntrepreneur()));
        factureDTO.setClient(clientMapper.fromClient(facture.getClient()));
        return factureDTO;
    }

    public Facture fromFactureDTO(FactureDTO factureDTO){
        Facture facture = new Facture();
        BeanUtils.copyProperties(factureDTO,facture);
        return facture;
    }
}
