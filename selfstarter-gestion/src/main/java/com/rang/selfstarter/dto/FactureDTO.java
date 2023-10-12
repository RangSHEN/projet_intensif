package com.rang.selfstarter.dto;

import lombok.Data;

@Data
public class FactureDTO {

    private Long factureId;
    private String factureName;
    private String factureDescription;
    private Long total;
    private EntrepreneurDTO entrepreneur;
    private ClientDTO client;
}
