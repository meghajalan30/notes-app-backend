package com.test.notes.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.notes.model.Owner;
import com.test.notes.service.UsersService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


//will be triggered everytime the user sends a request with username and password to login
//we need to register this filter with http security
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	
	UsersService usersService;
	Environment env;
	
	  public AuthenticationFilter(UsersService usersService, Environment env) {
		this.usersService = usersService;
		this.env = env;
	}

	//reads the username and password and calls the authenticate() method on the authentication manager
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Owner creds = new ObjectMapper().readValue(request.getInputStream(), Owner.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
    
  //called when authentication is successful
    //this will generate the JWT token for subsequent requests. this token will be added to http response header
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String username=((User)authResult.getPrincipal()).getUsername();
        Owner userDetails=usersService.getUserDetailsByUsername(username);

        String token= Jwts.builder().setSubject(userDetails.getOwnerId())
                .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong(env.getProperty("token.expiration_time"))))
                .signWith(SignatureAlgorithm.HS512,env.getProperty("token.secret"))
                .compact();

        response.addHeader("token",token);
        response.addHeader("userId",userDetails.getOwnerId());
    }

}
