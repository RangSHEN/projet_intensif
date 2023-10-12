package com.rang.selfstarter.dao;

import com.rang.selfstarter.entity.Entrepreneur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EntrepreneurDao extends JpaRepository<Entrepreneur,Long> {

    @Query(value = "select e from Entrepreneur as e where e.firstName like %:name% or e.lastName like %:name%")
    Page<Entrepreneur> findEntrepreneursByName(@Param("name") String name, PageRequest pageRequest);

    @Query(value = "select e from  Entrepreneur as e where e.user.email =: email ")
    Entrepreneur findEntrepreneursByEmail(@Param("email") String email);
}
