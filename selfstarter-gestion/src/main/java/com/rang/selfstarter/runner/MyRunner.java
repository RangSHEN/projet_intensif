package com.rang.selfstarter.runner;

import com.rang.selfstarter.dto.*;
import com.rang.selfstarter.entity.Client;
import com.rang.selfstarter.entity.Entrepreneur;
import com.rang.selfstarter.service.*;
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

    @Autowired
    private FactureService factureService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private DevisService devisService;

    @Override
    public void run(String... args) throws Exception {
        //createRoles();
        //createAdmin();
        //createEntrepreneurs();
        //createFactures();
        //createDevis();
        //createClient();

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

    private void createDevis(){
        for (int i = 0; i < 20; i++) {
            DevisDTO devisDTO = new DevisDTO();
            devisDTO.setDevisDescription(i + "automobile");
            devisDTO.setDevisName("devis" + i);
            devisDTO.setTotal(200L);
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setClientId(1L);
            devisDTO.setClient(clientDTO);
            EntrepreneurDTO entrepreneurDTO = new EntrepreneurDTO();
            entrepreneurDTO.setEntrepreneurId(6L);
            devisDTO.setEntrepreneur(entrepreneurDTO);
            devisService.createDevis(devisDTO);
        }

    }


    private void createFactures(){
        for (int i = 0; i < 5; i++) {
            FactureDTO factureDTO = new FactureDTO();
            factureDTO.setFactureDescription(i + "automobile");
            factureDTO.setFactureName("facture" + i);
            factureDTO.setTotal(100L);
            ClientDTO clientDTO = new ClientDTO();
            clientDTO.setClientId(2L);
            factureDTO.setClient(clientDTO);
            EntrepreneurDTO entrepreneurDTO = new EntrepreneurDTO();
            entrepreneurDTO.setEntrepreneurId(8L);
            factureDTO.setEntrepreneur(entrepreneurDTO);
            factureService.createFacture(factureDTO);
        }

    }

    private ClientDTO createClient() {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setFirstName("clientFN2");
        clientDTO.setLastName("clientLN2");
        clientDTO.setIsPremium("yes");
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("client2@gmail.com");
        userDTO.setPassword("12344");
        clientDTO.setUser(userDTO);
        return clientService.createClient(clientDTO);
    }

}
