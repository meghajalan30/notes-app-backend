package com.test.notes.exceptions;

public class NoteNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoteNotFoundException(String str) {
		super(str);
	}
}
