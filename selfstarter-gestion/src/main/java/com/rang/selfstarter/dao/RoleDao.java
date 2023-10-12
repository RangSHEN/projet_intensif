package com.rang.selfstarter.dao;

import com.rang.selfstarter.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<Role,Long> {

    Role findByName(String name);
}
