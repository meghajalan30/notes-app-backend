package com.test.notes.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.test.notes.service.UsersService;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/users").permitAll()
		.anyRequest().authenticated()
		.and().addFilter(getAuthenticationFilter())     //registering AuthenticationFilter
		.addFilter(new AuthorizationFilter(authenticationManager(),env)); 
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception{
		AuthenticationFilter authenticationFilter=new AuthenticationFilter(usersService,env);
		authenticationFilter.setAuthenticationManager(authenticationManager());
		return authenticationFilter;
	}
	
	//to validate the credentials
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);   //calls the loadUserByUsername method in UserServiceImpl (need to extend the UserDetailService class to override the method)
    }

}
