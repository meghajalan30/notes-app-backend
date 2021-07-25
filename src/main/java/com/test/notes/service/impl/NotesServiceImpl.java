package com.test.notes.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.test.notes.exceptions.NoteNotFoundException;
import com.test.notes.model.Note;
import com.test.notes.repository.NotesRepository;
import com.test.notes.service.NotesService;


@Service
@Transactional
public class NotesServiceImpl implements NotesService{
	
	int idCount=0;
	
	@Autowired
	NotesRepository notesRepository;

	public ResponseEntity<Note> saveNote(Note note){	
		Date date=new Date();
		
//		note.setId(++idCount);
		note.setCreatedDate(date);
		Note result=notesRepository.save(note);
		
		return new ResponseEntity<Note>(result,HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<List<Note>> getNotes(String title, String tag1) {
		
		List<Note> notesList=new ArrayList<Note>();
		notesList=notesRepository.findAll();
		
		List<Note> filteredList=new ArrayList<Note>();
		
		if(title==null && tag1==null) {
			Collections.sort(notesList, (o1,o2) -> o1.getCreatedDate().compareTo(o2.getCreatedDate()));
			return new ResponseEntity<List<Note>>(notesList,HttpStatus.OK);
		}
		
		if(title != null) {
			notesList.forEach((note) -> {
				if(note.getTitle().equals(title)) {
					filteredList.add(note);
				}
			});
		}
		
		if(tag1 != null) {
			notesList.forEach((note) -> {
				if(note.getTag1().equals(tag1) || note.getTag2().equals(tag1) || note.getTag3().equals(tag1) ) {
					filteredList.add(note);
				}
			});		
		}
		
		if(filteredList.isEmpty()) {
			try {
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			throw new NoteNotFoundException("note not found");
			}catch(NoteNotFoundException ex) {
				
			}
		}
		
		else {
			Collections.sort(filteredList, (o1,o2) -> o1.getCreatedDate().compareTo(o2.getCreatedDate()));
			return new ResponseEntity<List<Note>>(filteredList,HttpStatus.OK);
		}
		return null;
	}

}
