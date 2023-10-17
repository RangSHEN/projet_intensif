package com.rang.selfstarter.web;

import com.rang.selfstarter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/users")
    public boolean checkIfEmailExists(@RequestParam(name = "email", defaultValue = "") String email){
         return userService.loadUserByEmail(email) != null;

    }
}
