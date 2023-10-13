package com.rang.selfstarter.service.impl;

import com.rang.selfstarter.dao.RoleDao;
import com.rang.selfstarter.dao.UserDao;
import com.rang.selfstarter.entity.Role;
import com.rang.selfstarter.entity.User;
import com.rang.selfstarter.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    private UserDao userDao;
    private RoleDao roleDao;
   // private PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public User loadUserByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User createUser(String email, String password) {

       // String encodePassword = passwordEncoder.encode(password);
       // return userDao.save(new User(email,encodePassword));
        return userDao.save(new User(email,password));
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user = loadUserByEmail(email);
        Role role = roleDao.findByName(roleName);
        user.assignRoleToUser(role);
    }
}
