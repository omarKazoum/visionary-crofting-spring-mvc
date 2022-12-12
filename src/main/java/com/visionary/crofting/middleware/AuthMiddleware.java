package com.visionary.crofting.middleware;

import com.visionary.crofting.config.AuthConfig;
import com.visionary.crofting.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class AuthMiddleware extends OncePerRequestFilter {
    @Autowired
    AuthConfig authConfig;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("a request was received ");
        if(!authConfig.isEnabled()) {
            filterChain.doFilter(request, response);
            return;
        }
        HttpSession session=request.getSession(false);
        String url=request.getRequestURI().replace(request.getContextPath(),"");

        if(session==null || session.getAttribute(Constants.SESSION_KEY_USER)==null) {
            //if the user is not logged in let's redirect him to login page
            if(!url.equals("/login"))
            response.sendRedirect("/login");
            else
                filterChain.doFilter(request, response);
        }else if(url.equals("/login")){
            response.sendRedirect("/");
        }else
            filterChain.doFilter(request, response);

    }


}
