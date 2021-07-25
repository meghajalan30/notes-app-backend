package com.test.notes.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter{
	
	Environment environment;

    public AuthorizationFilter(AuthenticationManager authenticationManager, Environment environment) {
        super(authenticationManager);
        this.environment=environment;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String authorizationHeader=request.getHeader("Authorization");
        if(authorizationHeader==null || !authorizationHeader.startsWith("Bearer"))
        {
            chain.doFilter(request,response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication=getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    //authenticating the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req)
    {
        String authorizationHeader=req.getHeader("Authorization");
        if(authorizationHeader==null)
            return null;
        String token=authorizationHeader.replace("Bearer","");
        String userId= Jwts.parser()
                .setSigningKey(environment.getProperty("token.secret"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        if(userId==null)
            return null;

        return new UsernamePasswordAuthenticationToken(userId,null,new ArrayList<>());

    }
}
