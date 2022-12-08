package com.visionary.crofting.service;

import com.visionary.crofting.dto.LoginDTO;
import com.visionary.crofting.entity.Client;
import com.visionary.crofting.entity.User;
import com.visionary.crofting.exceptions.BusinessException;
import com.visionary.crofting.requests.ClientDTO;

import java.util.*;

public interface IClientService {
    public Client save(ClientDTO request) ;

    Optional<Client> findClientByUuid(String uuid);

    public List<Client> findAll();
    public void delete(String uuid) throws BusinessException;
    public Client update(String uuid, ClientDTO Request) throws BusinessException;
    public Optional<User> login(LoginDTO loginDTO) throws BusinessException;
}
