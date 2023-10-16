package com.mathcode.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mathcode.todolist.user.IUserRepository;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        var servletPath = request.getServletPath();

        if(servletPath.equals("/tasks/")){
            var authorization = request.getHeader("Authorization");
            var user_password = authorization.substring("Basic".length()).trim();
            byte[] authDecode = Base64.getDecoder().decode(user_password);
            var authString = new String(authDecode);
            String[] authInfos = authString.split(":");
            var username = authInfos[0];
            var password = authInfos[1];
            
            var user = this.userRepository.findByUsername(username);

            if(user == null) {
            response.sendError(401);
            } else {
                var passwordValid = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(passwordValid.verified){
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(401);
                }
                }
        } else {
            filterChain.doFilter(request, response);
        }
       

    }


    
}
