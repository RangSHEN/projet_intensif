package com.rang.selfstarter.service;

import com.rang.selfstarter.dto.FactureDTO;
import com.rang.selfstarter.entity.Facture;
import org.springframework.data.domain.Page;

public interface FactureService {

    Facture loadFactureById(Long factureId);

    FactureDTO createFacture(FactureDTO factureDTO);

    FactureDTO updateFacture(FactureDTO factureDTO);

    Page<FactureDTO> findFactureByFactureName(String keyword, int page, int size);

    Page<FactureDTO> fetchFacturesForClient(Long clientId, int page, int size);

    Page<FactureDTO> fetchFacturesForEntrepreneur(Long entrepreneurId, int page, int size);

    void removeFacture(Long factureId);


}
