package com.test.notes.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.test.notes.model.Note;

public interface NotesService {
	
	public ResponseEntity<Note> saveNote(Note note);
	public ResponseEntity<List<Note>> getNotes(String title, String tag1);

}
