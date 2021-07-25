package com.test.notes.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.test.notes.model.Owner;

public interface UsersService extends UserDetailsService{

	Owner getUserDetailsByUsername(String username);

}
