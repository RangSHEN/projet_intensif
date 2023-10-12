package com.rang.selfstarter.service;

import com.rang.selfstarter.dto.ClientDTO;
import com.rang.selfstarter.dto.EntrepreneurDTO;
import com.rang.selfstarter.entity.Client;
import com.rang.selfstarter.entity.Entrepreneur;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EntrepreneurService {

    Entrepreneur loadEntrepreneurById(Long entrepreneurId);

    Page<EntrepreneurDTO> findEntrepreneursByName(String name, int page, int size);

    EntrepreneurDTO loadEntrepreneurByEmail(String email);

    EntrepreneurDTO createEntrepreneur(EntrepreneurDTO entrepreneurDTO);

    EntrepreneurDTO updateEntrepreneur(EntrepreneurDTO entrepreneurDTO);

    List<EntrepreneurDTO> fetchEntrepreneurs();

    void removeEntrepreneur(Long entrepreneurId);
}
