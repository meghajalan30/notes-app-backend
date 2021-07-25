package com.test.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.notes.model.Owner;

@Repository
public interface OwnersRepository extends JpaRepository<Owner,Integer>{
	
	Owner findByUsername(String usrname);

}
