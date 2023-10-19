package com.rang.selfstarter.web;

import com.rang.selfstarter.dto.FactureDTO;
import com.rang.selfstarter.service.FactureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facture")
@CrossOrigin("*")
public class FactureController {

    @Autowired
    private FactureService factureService;

    @GetMapping
    @PreAuthorize("hasAuthority('Admin')")
    public Page<FactureDTO> searchFactures(@RequestParam(name = "keyword", defaultValue = "") String keyword,
                                           @RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "5") int size){

        return factureService.findFactureByFactureName(keyword,page,size);
        //    /facture/search?keyword=dd&page=2size=3
    }


    @DeleteMapping("/{factureId}")
    @PreAuthorize("hasAuthority('Admin')")
    public void deleteFacture(@PathVariable Long factureId){

        factureService.removeFacture(factureId);
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('Admin','Client','Entrepreneur')")
    public FactureDTO saveFacture(@RequestBody FactureDTO factureDTO){

        return factureService.createFacture(factureDTO);
    }

    @PutMapping("/update/{factureId}")
    @PreAuthorize("hasAnyAuthority('Admin','Client','Entrepreneur')")
    public FactureDTO updateFacture(@RequestBody FactureDTO factureDTO, @PathVariable Long factureId){

        factureDTO.setFactureId(factureId);
        return factureService.updateFacture(factureDTO);
    }

}
