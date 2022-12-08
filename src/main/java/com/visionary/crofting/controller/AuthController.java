package com.visionary.crofting.controller;

import com.visionary.crofting.dto.LoginDTO;
import com.visionary.crofting.service.IAuthService;
import com.visionary.crofting.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {
    @Autowired
    IAuthService authService;
    @GetMapping("/login")
    public ModelAndView login(){
        Map<String,Object> model=new HashMap<>();
        LoginDTO loginDTO=new LoginDTO();
        loginDTO.setPassword("please enetr your password here");
        loginDTO.setEmail("example@email.Com");
        loginDTO.setRememberMe(false);
        model.put("loginDto",loginDTO);
        return new ModelAndView("login",model);
    }
    @PostMapping("/login")
    public ModelAndView loginSubmit(@Valid @ModelAttribute("loginDto") LoginDTO loginDTO, BindingResult bindingResult, HttpServletRequest request){
        if(bindingResult.hasErrors() || !authService.isLoginInfoValid(loginDTO)){
            //we should redirect the user back to the login page
            bindingResult.rejectValue(null,"400","Invalid credentials");
            return new ModelAndView("login");
        }

        AuthUtil.login(loginDTO,request);
        return new ModelAndView(new RedirectView("/"));

    }

}
