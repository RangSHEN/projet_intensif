package com.rang.selfstarter.dao;

import com.rang.selfstarter.entity.Devis;
import com.rang.selfstarter.entity.Facture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DevisDao extends JpaRepository<Devis,Long> {


    @Query(value = "select d from Devis as d where d.devisName like %:keyword% ")
    Page<Devis> findDevisByDevisNameContains(String keyword, Pageable pageable);

    @Query(value = "select d from Devis as d where d.entrepreneur.entrepreneurId =:entrepreneurId ")
    Page<Devis> getDevisByEntrepreneurId(@Param("entrepreneurId") Long entrepreneurId, Pageable pageable);

    @Query(value = "select d from Devis as d where d.client.clientId =:clientId ")
    Page<Devis> getDevisByClientId(@Param("clientId") Long clientId, Pageable pageable);
}
