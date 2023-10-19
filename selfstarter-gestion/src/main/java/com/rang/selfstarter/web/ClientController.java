package com.rang.selfstarter.web;


import com.rang.selfstarter.dto.ClientDTO;
import com.rang.selfstarter.dto.DevisDTO;
import com.rang.selfstarter.dto.EntrepreneurDTO;
import com.rang.selfstarter.dto.FactureDTO;
import com.rang.selfstarter.entity.Devis;
import com.rang.selfstarter.entity.User;
import com.rang.selfstarter.service.ClientService;
import com.rang.selfstarter.service.DevisService;
import com.rang.selfstarter.service.FactureService;
import com.rang.selfstarter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("client")
@CrossOrigin("*")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    UserService userService;

    @Autowired
    FactureService factureService;

    @Autowired
    DevisService devisService;

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public Page<ClientDTO> searchClients(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size){

        return clientService.findClientsByName(keyword,page,size);
    }

    @DeleteMapping("/{clientId}")
    @PreAuthorize("hasAuthority('Admin')")
    public void deleteClient(@PathVariable Long clientId){
        clientService.removeClient(clientId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('Admin')")
    public ClientDTO saveClient(@RequestBody ClientDTO clientDTO){
        User user = userService.loadUserByEmail(clientDTO.getUser().getEmail());
        if(user != null) throw new RuntimeException("Email already Exist");
        return  clientService.createClient(clientDTO);
    }


    @PutMapping("/{clientId}")
    @PreAuthorize("hasAnyAuthority('Admin','Client')")
    public ClientDTO updateClient(@RequestBody ClientDTO clientDTO, @PathVariable Long clientId){
        clientDTO.setClientId(clientId);
        return clientService.updateClient(clientDTO);
    }

    @GetMapping("/{clientId}/devis")
    @PreAuthorize("hasAnyAuthority('Admin','Client')")
    public Page<DevisDTO> DevisByClientId(@PathVariable Long clientId,
                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "5") int size){

        return devisService.fetchDevisForClient(clientId,page,size);

    }

    @GetMapping("/{clientId}/factures")
    @PreAuthorize("hasAnyAuthority('Admin','Client')")
    public Page<FactureDTO> FacturesByClientId(@PathVariable Long clientId,
                                             @RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "5") int size){

        return factureService.fetchFacturesForClient(clientId,page,size);

    }

    @GetMapping("/find")
    @PreAuthorize("hasAnyAuthority('Admin','Client')")
    public ClientDTO loadClientByEmail(@RequestParam(name = "email", defaultValue = "") String email){
        return clientService.loadClientByEmail(email);
    }
}
