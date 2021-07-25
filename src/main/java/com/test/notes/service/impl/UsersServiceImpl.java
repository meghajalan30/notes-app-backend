package com.test.notes.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.User;

import com.test.notes.model.Owner;
import com.test.notes.repository.OwnersRepository;
import com.test.notes.service.UsersService;

@Service
@Transactional
public class UsersServiceImpl implements UsersService{

	
	@Autowired
	OwnersRepository usersRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Owner user=usersRepository.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException(username);
		
		return new User(user.getUsername(),user.getPassword(),true,true,true,true,new ArrayList<>());

}

	@Override
	public Owner getUserDetailsByUsername(String username) {
		Owner user= usersRepository.findByUsername(username);
		if(user == null)
			throw new UsernameNotFoundException(username);
		return user;
	}
}
