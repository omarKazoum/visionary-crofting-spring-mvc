package com.visionary.crofting.controller;

import com.visionary.crofting.dto.LoginDTO;
import com.visionary.crofting.entity.Client;
import com.visionary.crofting.entity.User;
import com.visionary.crofting.requests.ClientDTO;
import com.visionary.crofting.response.ApiResponse;
import com.visionary.crofting.service.IService;
import com.visionary.crofting.service.Impl.ClientServiceImpl;
import com.visionary.crofting.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ClientController {
/*

    @Qualifier("clientServiceImpl")
    @Autowired
    IService service ;

    @Autowired
    ClientServiceImpl clientService ;

    @PostMapping("/client")
    public ModelAndView saveClient( @Valid @ModelAttribute("client") ClientDTO clientRequest ){
            //ApiResponse<Client> response = service.save(clientRequest);
        new ModelAndView("");
    }

    @GetMapping("/client/{uuid}")
    public ModelAndView getClientById(@PathVariable String uuid ){
        try {
            ApiResponse<Client> response = service.find(uuid);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<Client> clientApiResponse = new ApiResponse<>() ;
            clientApiResponse.setResponseCode(ApiResponse.ResponseCode.ERROR_TECHNIQUE);
            return new ResponseEntity<>(clientApiResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/clients")
    public ModelAndView getListClients(){
        try {
            ApiResponse<List<Client>> response = service.findAll();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<List<Client>> clientApiResponse = new ApiResponse<>();
            clientApiResponse.setResponseCode(ApiResponse.ResponseCode.ERROR_TECHNIQUE);
            return new ResponseEntity<>(clientApiResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/client/{uuid}")
    public ModelAndView deleteClientById(@PathVariable String uuid){
        try {
            ApiResponse<Client> response = service.delete(uuid);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<Client> clientApiResponse = new ApiResponse<>() ;
            clientApiResponse.setResponseCode(ApiResponse.ResponseCode.ERROR_TECHNIQUE);
            return new ResponseEntity<>(clientApiResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/client/{uuid}")
    public ModelAndView updateClient(@PathVariable String uuid,@RequestBody ClientDTO clientRequest){
        try {
            ApiResponse<Client> response = service.update(uuid,clientRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            ApiResponse<Client> clientApiResponse = new ApiResponse<>() ;
            clientApiResponse.setResponseCode(ApiResponse.ResponseCode.ERROR_TECHNIQUE);
            return new ResponseEntity<>(clientApiResponse, HttpStatus.OK);
        }
    }
*/


}
