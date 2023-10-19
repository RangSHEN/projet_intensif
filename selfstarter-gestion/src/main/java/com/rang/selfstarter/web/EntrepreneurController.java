package com.rang.selfstarter.web;


import com.rang.selfstarter.dto.DevisDTO;
import com.rang.selfstarter.dto.EntrepreneurDTO;
import com.rang.selfstarter.dto.FactureDTO;
import com.rang.selfstarter.entity.Entrepreneur;
import com.rang.selfstarter.entity.User;
import com.rang.selfstarter.service.DevisService;
import com.rang.selfstarter.service.EntrepreneurService;
import com.rang.selfstarter.service.FactureService;
import com.rang.selfstarter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/entrepreneur")
@CrossOrigin("*")
public class EntrepreneurController {

    @Autowired
    private EntrepreneurService entrepreneurService;

    @Autowired
    private UserService userService;

    @Autowired
    private DevisService devisService;

    @Autowired
    private FactureService factureService;

    //view page with search tab
    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public Page<EntrepreneurDTO> searchEntrepreneurs(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                     @RequestParam(name = "size", defaultValue = "5") int size){

        return entrepreneurService.findEntrepreneursByName(keyword,page,size);

    }

    //get a list of entrepreneurs to create and update form
    // select the entrepreneurs of the devis in a dropdown

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('Admin')")
    public List<EntrepreneurDTO> findAllEntrepreneurs(){
        return entrepreneurService.fetchEntrepreneurs();
    }

    @DeleteMapping("/{entrepreneurId}")
    @PreAuthorize("hasAuthority('Admin')")
    public void deleteEntrepreneur(@PathVariable Long entrepreneurId){
        entrepreneurService.removeEntrepreneur(entrepreneurId);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('Admin')")
    public EntrepreneurDTO saveEntrepreneur(@RequestBody EntrepreneurDTO entrepreneurDTO){
        User user = userService.loadUserByEmail(entrepreneurDTO.getUser().getEmail());
        if (user != null) throw new RuntimeException("Email Already Exist");

        return entrepreneurService.createEntrepreneur(entrepreneurDTO);
    }


    @PutMapping("/{entrepreneurId}")
    @PreAuthorize("hasAnyAuthority('Admin','Entrepreneur')")
    public EntrepreneurDTO updateEntrepreneur(@RequestBody EntrepreneurDTO entrepreneurDTO, @PathVariable Long entrepreneurId){
        entrepreneurDTO.setEntrepreneurId(entrepreneurId);
        return entrepreneurService.updateEntrepreneur(entrepreneurDTO);
    }

    //localhost:9000/entrepreneur/5/devis?size=10
    @GetMapping("/{entrepreneurId}/devis")
    @PreAuthorize("hasAnyAuthority('Admin','Entrepreneur')")
    public Page<DevisDTO> devisByEntrepreneurId(@PathVariable Long entrepreneurId,
                                                @RequestParam(name = "page", defaultValue = "0") int page,
                                                @RequestParam(name = "size", defaultValue = "5") int size){

        return devisService.fetchDevisForEntrepreneur(entrepreneurId,page,size);

    }

    @GetMapping("/{entrepreneurId}/factures")
    @PreAuthorize("hasAnyAuthority('Admin','Entrepreneur')")
    public Page<FactureDTO> FacturesByEntrepreneurId(@PathVariable Long entrepreneurId,
                                                     @RequestParam(name = "page", defaultValue = "0") int page,
                                                     @RequestParam(name = "size", defaultValue = "5") int size){

        return factureService.fetchFacturesForEntrepreneur(entrepreneurId,page,size);

    }


    @GetMapping("/find")
    @PreAuthorize("hasAnyAuthority('Admin','Entrepreneur')")
    public EntrepreneurDTO loadEntrepreneurByEmail(@RequestParam(name = "email", defaultValue = "") String email){
        return entrepreneurService.loadEntrepreneurByEmail(email);
    }


}
