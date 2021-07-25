package com.test.notes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.test.notes.model.Note;

@Repository
public interface NotesRepository extends JpaRepository<Note,Integer>{

}
