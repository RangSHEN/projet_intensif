package com.rang.selfstarter.dto;

import lombok.Data;

@Data
public class ClientDTO {

    private Long clientId;
    private String firstName;
    private String lastName;
    private String isPremium;
    private UserDTO user;
}
