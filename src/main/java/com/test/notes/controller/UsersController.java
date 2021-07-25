package com.test.notes.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.notes.model.Owner;
import com.test.notes.repository.OwnersRepository;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api")
public class UsersController {
	
	@Autowired
	OwnersRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/users")
	public ResponseEntity<List<Owner>> getUsers(){
		List<Owner> users= userRepository.findAll();
		return new ResponseEntity<List<Owner>>(users, HttpStatus.OK);
	}
	
	@PostMapping("/users")
	public ResponseEntity<Owner> saveUser(@RequestBody Owner user){
	
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setOwnerId(UUID.randomUUID().toString());
		user=userRepository.save(user);
		return new ResponseEntity<Owner>(user, HttpStatus.OK);
	}

}
