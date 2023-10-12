package com.rang.selfstarter.dto;

import lombok.Data;

@Data
public class EntrepreneurDTO {

    private Long entrepreneurId;
    private String firstName;
    private String lastName;
    private String companyName;
    private UserDTO user;
}
