package com.rang.selfstarter.dao;

import com.rang.selfstarter.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClientDao extends JpaRepository<Client,Long> {

    @Query(value = "select c from Client as c where c.firstName like %:name% or c.lastName like %:name%")
    Page<Client> findClientsByName(@Param("name") String name, PageRequest pageRequest);

    @Query(value = "select c from Client as c where c.user.email=:email")
    Client findClientsByEmail(@Param("email") String email);
}
