package com.rang.selfstarter.web;

import com.rang.selfstarter.dto.DevisDTO;
import com.rang.selfstarter.dto.FactureDTO;
import com.rang.selfstarter.service.DevisService;
import com.rang.selfstarter.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devis")
@CrossOrigin("*")
public class DevisController {

    @Autowired
    private DevisService devisService;

    @GetMapping
    public Page<DevisDTO> searchDevis(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                         @RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "5") int size){

        return devisService.findDevisByDevisName(keyword,page,size);
    }


    @DeleteMapping("/{devisId}")
    public void deleteDevis(@PathVariable Long devisId){

        devisService.removeDevis(devisId);
    }

    @PostMapping
    public DevisDTO saveDevis(@RequestBody DevisDTO devisDTO){

        return devisService.createDevis(devisDTO);
    }

    @PutMapping("/update/{devisId}")
    public DevisDTO updateDevis(@RequestBody DevisDTO devisDTO, @PathVariable Long devisId){

        devisDTO.setDevisId(devisId);
        return devisService.updateDevis(devisDTO);
    }

}
