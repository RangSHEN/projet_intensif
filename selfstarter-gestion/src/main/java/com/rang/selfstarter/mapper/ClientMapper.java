package com.rang.selfstarter.mapper;

import com.rang.selfstarter.dto.ClientDTO;
import com.rang.selfstarter.dto.EntrepreneurDTO;
import com.rang.selfstarter.entity.Client;
import com.rang.selfstarter.entity.Entrepreneur;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class ClientMapper {

    public ClientDTO fromClient(Client client){
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(client,clientDTO);
        return clientDTO;
    }

    public Client fromClientDTO(ClientDTO clientDTO){
        Client client = new Client();
        BeanUtils.copyProperties(clientDTO,client);
        return client;
    }
}
