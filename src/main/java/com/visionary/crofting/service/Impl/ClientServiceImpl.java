package com.visionary.crofting.service.Impl;

import com.visionary.crofting.dto.LoginDTO;
import com.visionary.crofting.entity.Client;
import com.visionary.crofting.exceptions.BusinessException;
import com.visionary.crofting.repository.ClientRepository;
import com.visionary.crofting.repository.SupplierRepository;
import com.visionary.crofting.requests.ClientDTO;
import com.visionary.crofting.response.ApiResponse;
import com.visionary.crofting.service.IClientService;
import com.visionary.crofting.entity.User;
import com.visionary.crofting.util.PasswordHasher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientServiceImpl implements IClientService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public Client save(ClientDTO request) {
            //todo add business validation
            Client client = new Client();
            client.setUuid(UUID.randomUUID().toString());
            client.setName(request.getName());
            client.setEmail(request.getEmail());
            client.setPassword(request.getPassword());
            client.setPhone(request.getPhone());
            return clientRepository.save(client);
    }

    @Override
    public Optional<Client> findClientByUuid(String uuid)  {
       return clientRepository.findByUuid(uuid);
    }

    @Override
    public List<Client> findAll()  {
        List<Client> clients = clientRepository.findAll();
        return clients;
    }

    @Override
    public void delete(String uuid) throws BusinessException {
        Optional<Client> optionalClient=clientRepository.findByUuid(uuid);
        if(!optionalClient.isPresent())
            throw new BusinessException("client with uuid not found");
        clientRepository.delete(optionalClient.get());
    }


    @Override
    public Client update(String uuid, ClientDTO clientDto) throws BusinessException {
        Optional<Client> optionalClient=findClientByUuid(clientDto.getUuid());
            if(!optionalClient.isPresent())
            throw new BusinessException("invalid token");
            Client clientToUpdate=optionalClient.get();
            clientToUpdate.setEmail(clientDto.getEmail());
            clientToUpdate.setName(clientDto.getName());
            clientToUpdate.setPassword(PasswordHasher.hash(clientDto.getPassword()));
            clientToUpdate.setPhone(clientDto.getPhone());
           return clientRepository.save(clientToUpdate);
    }


    public Optional<User> login(LoginDTO loginDTO) throws BusinessException {
            Optional<User> optionalUser = clientRepository.findByEmail(loginDTO.getEmail());
            if(optionalUser.isPresent())
                return optionalUser;
            else
                throw new BusinessException("invalide credentials", Arrays.asList("invalid user name","invalid password"));

    }

    public boolean validateUUID(String uuid){
        Pattern emailPattern =Pattern.compile("[a-f0-9]{8}(?:-[a-f0-9]{4}){4}[a-f0-9]{8}");
        Matcher emailMatcher = emailPattern.matcher(uuid);
        if(!emailMatcher.matches() || uuid.isEmpty() || uuid.isBlank() ){
            return false;
        }
        return true;
    }

}
