package com.rang.selfstarter.dto;

import lombok.Data;

@Data
public class DevisDTO {

    private Long devisId;
    private String devisName;
    private String devisDescription;
    private Long total;
    private EntrepreneurDTO entrepreneur;
    private ClientDTO client;
}
