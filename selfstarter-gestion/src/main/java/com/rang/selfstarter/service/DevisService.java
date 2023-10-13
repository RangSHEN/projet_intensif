package com.rang.selfstarter.service;

import com.rang.selfstarter.dto.DevisDTO;
import com.rang.selfstarter.dto.FactureDTO;
import com.rang.selfstarter.entity.Devis;
import com.rang.selfstarter.entity.Facture;
import org.springframework.data.domain.Page;

public interface DevisService {

    Devis loadDevisById(Long devisId);

    DevisDTO createDevis(DevisDTO devisDTO);

    DevisDTO updateDevis(DevisDTO devisDTO);

    Page<DevisDTO> findDevisByDevisName(String keyword, int page, int size);

    Page<DevisDTO> fetchDevisForClient(Long clientId, int page, int size);

    Page<DevisDTO> fetchDevisForEntrepreneur(Long entrepreneurId, int page, int size);

    void removeDevis(Long factureId);
}
