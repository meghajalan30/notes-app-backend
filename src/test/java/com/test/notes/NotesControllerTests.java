package com.test.notes;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;
import com.test.notes.controller.NotesController;
import com.test.notes.model.Note;
import com.test.notes.service.NotesService;

@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest
class NotesControllerTests {
	
	@Autowired
	NotesController controller;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private NotesService notesservice;

	@Test
	void contextLoads() throws Exception{
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void testGreeting() throws Exception{
		this.mockMvc.perform(get("/greet")).andDo(print()).andExpect(status().isOk()).andExpect(content().string(containsString("Hello!")));
	}
	
	@Test
	public void testGetNotes() throws Exception{
		List<Note> notesList=new ArrayList<Note>();
		notesList.add(new Note(1,"Note 1","Test note","study",null,null,new Date(),new Date()));
		notesList.add(new Note(2,"Note 2","this is another test note.","play",null,null,new Date(),new Date()));
		
		Mockito.when(this.notesservice.getNotes(null, null)).thenReturn(new ResponseEntity<>(notesList,HttpStatus.OK));
		
		this.mockMvc.perform(MockMvcRequestBuilders.get("/notes")).andDo(print()).andExpect(status().isOk())
		.andExpect(jsonPath("$", hasSize(notesList.size())))
		.andExpect(jsonPath("$[0].id", is(1)))
        .andExpect(jsonPath("$[0].title", is("Note 1")));
	}
	
}
