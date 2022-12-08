package com.visionary.crofting.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @GetMapping("/error")
    ModelAndView getErrorPage(HttpServletRequest request, HttpServletResponse response){
        Object errorCode= request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        System.out.println("error code "+errorCode.getClass().getName());
        return new ModelAndView("error");
    }
}
