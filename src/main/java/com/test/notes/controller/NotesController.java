package com.test.notes.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.test.notes.model.Note;
import com.test.notes.service.NotesService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api")
public class NotesController {

	List<Note> notesList=new ArrayList<Note>();
	int idCount=0;
	
	@Autowired
	private NotesService notesService;
	
	@GetMapping("/greet")
	public String greeting() {
		return "Hello!";
	}
	
	@GetMapping("/notes")
	public ResponseEntity<List<Note>> getNotes(@RequestParam(required=false) String title, @RequestParam(required=false) String tag1){
		return notesService.getNotes(title, tag1);
	}
	
	
	@PostMapping("/notes")
	public ResponseEntity<Note> saveNotes(@Valid @RequestBody Note note) {
		return notesService.saveNote(note);
	}
}
