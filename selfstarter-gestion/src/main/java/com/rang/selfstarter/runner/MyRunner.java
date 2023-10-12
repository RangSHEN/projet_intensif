package com.rang.selfstarter.runner;

import com.rang.selfstarter.dto.EntrepreneurDTO;
import com.rang.selfstarter.dto.UserDTO;
import com.rang.selfstarter.entity.Entrepreneur;
import com.rang.selfstarter.service.EntrepreneurService;
import com.rang.selfstarter.service.RoleService;
import com.rang.selfstarter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class MyRunner implements CommandLineRunner {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private EntrepreneurService entrepreneurService;

    @Override
    public void run(String... args) throws Exception {
        //createRoles();
        //createAdmin();
        createEntrepreneurs();
    }


    private void createRoles(){
        Arrays.asList("Admin","Client","Entrepreneur").forEach(role-> roleService.createRole(role));
    }

    private void createAdmin() {
        userService.createUser("admin@gmail.com","1234");
        userService.assignRoleToUser("admin@gmail.com","Admin");
    }


    private void createEntrepreneurs() {
        for (int i = 0; i < 10; i++) {
            EntrepreneurDTO entrepreneurDTO = new EntrepreneurDTO();
            entrepreneurDTO.setFirstName("entrepreneur"+ i + "Rang");
            entrepreneurDTO.setLastName("entrepreneur"+ i + "Alex");
            entrepreneurDTO.setCompanyName("company"+ i);
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail("entrepreneur"+ i +"@gmail.com");
            userDTO.setPassword("1234");
            entrepreneurDTO.setUser(userDTO);
            entrepreneurService.createEntrepreneur(entrepreneurDTO);
        }
        
    }

}
