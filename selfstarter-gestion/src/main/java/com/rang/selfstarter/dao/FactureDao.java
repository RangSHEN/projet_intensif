package com.rang.selfstarter.dao;

import com.rang.selfstarter.entity.Facture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FactureDao extends JpaRepository<Facture,Long> {

    Page<Facture> findFacturesByFactureNameContains(String keyword, Pageable pageable);

    @Query(value = "select f from Facture as f where f.entrepreneur.entrepreneurId =:entrepreneurId ")
    Page<Facture> getFacturesByEntrepreneurId(@Param("entrepreneurId") Long entrepreneurId, Pageable pageable);

    @Query(value = "select f from Facture as f where f.client.clientId =:clientId ")
    Page<Facture> getFacturesByClientId(@Param("clientId") Long clientId, Pageable pageable);


}
