package com.rang.selfstarter.mapper;

import com.rang.selfstarter.dto.EntrepreneurDTO;
import com.rang.selfstarter.entity.Entrepreneur;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class EntrepreneurMapper {

    public EntrepreneurDTO fromEntrepreneur(Entrepreneur entrepreneur){
        EntrepreneurDTO entrepreneurDTO = new EntrepreneurDTO();
        BeanUtils.copyProperties(entrepreneur,entrepreneurDTO);
        return entrepreneurDTO;
    }

    public Entrepreneur fromEntrepreneurDTO(EntrepreneurDTO entrepreneurDTO){
        Entrepreneur entrepreneur = new Entrepreneur();
        BeanUtils.copyProperties(entrepreneurDTO,entrepreneur);
        return entrepreneur;
    }
}
