package com.test.notes;

import com.test.notes.model.Note;
import com.test.notes.repository.NotesRepository;
import com.test.notes.service.NotesService;
import com.test.notes.service.impl.NotesServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NotesServiceTests {

	@InjectMocks
	NotesServiceImpl notesService;
	
	@Mock
	NotesRepository notesRepository;
	
	@Test
	public void getNotesTest() {
		
		List<Note> notesList=new ArrayList<Note>();
		notesList.add(new Note(1,"Note 1","Test note","study",null,null,new Date(),new Date()));
		notesList.add(new Note(2,"Note 2","this is another test note.","play",null,null,new Date(),new Date()));
		
		Mockito.when(this.notesRepository.findAll()).thenReturn(notesList);
		
		List<Note> resultList=this.notesService.getNotes(null, null).getBody();
		
		assertThat(resultList).isNotEmpty().isEqualTo(notesList);
		
		
	}
}

