package com.mathcode.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var authorization = request.getHeader("Authorization");
        var user_password = authorization.substring("Basic".length()).trim();
        byte[] authDecode = Base64.getDecoder().decode(user_password);
        var authString = new String(authDecode);
        String[] authInfos = authString.split(":");
        var username = authInfos[0];
        var password = authInfos[1];
        System.out.println(username + password);
    }


    
}
