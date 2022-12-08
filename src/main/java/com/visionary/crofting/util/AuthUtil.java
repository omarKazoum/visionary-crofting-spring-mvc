package com.visionary.crofting.util;

import com.visionary.crofting.dto.LoginDTO;
import com.visionary.crofting.entity.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthUtil {
    public static void login(LoginDTO userDTO, HttpServletRequest request){
        HttpSession session =request.getSession(true);
        session.setAttribute(Constants.SESSION_KEY_USER,userDTO);
    }
    public static void logOut(HttpServletRequest request){
        HttpSession session =request.getSession(true);
        if(session==null)
            session.invalidate();
    }
}
