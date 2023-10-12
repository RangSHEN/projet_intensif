package com.rang.selfstarter.dao;

import com.rang.selfstarter.entity.Role;
import com.rang.selfstarter.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User,Long> {

    User findByEmail(String email);
}
