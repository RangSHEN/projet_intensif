package com.rang.selfstarter.mapper;

import com.rang.selfstarter.dto.DevisDTO;
import com.rang.selfstarter.dto.FactureDTO;
import com.rang.selfstarter.entity.Devis;
import com.rang.selfstarter.entity.Facture;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class DevisMapper {

    private EntrepreneurMapper entrepreneurMapper;

    private ClientMapper clientMapper;

    public DevisMapper(EntrepreneurMapper entrepreneurMapper, ClientMapper clientMapper) {
        this.entrepreneurMapper = entrepreneurMapper;
        this.clientMapper = clientMapper;
    }

    public DevisDTO fromDevis(Devis devis){
        DevisDTO devisDTO = new DevisDTO();
        BeanUtils.copyProperties(devis,devisDTO);
        devisDTO.setEntrepreneur(entrepreneurMapper.fromEntrepreneur(devis.getEntrepreneur()));
        devisDTO.setClient(clientMapper.fromClient(devis.getClient()));
        return devisDTO;
    }

    public Devis fromDevisDTO(DevisDTO devisDTO){
        Devis devis = new Devis();
        BeanUtils.copyProperties(devisDTO,devis);
        return devis;
    }
}
