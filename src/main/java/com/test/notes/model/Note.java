package com.test.notes.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name="notes")
public class Note {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(name = "title")
	@NotEmpty
	private String title;
	@Column(name = "description")
	@NotEmpty
	private String description;
	@Column(name = "tag1")
	private String tag1;
	@Column(name = "tag2")
	private String tag2;
	@Column(name = "tag3")
	private String tag3;
	@Column(name = "reminder")
	private Date reminder;
	@Column(name = "createdDate")
	private Date createdDate;
	
	public Note(int id, @NotEmpty String title, @NotEmpty String description, String tag1, String tag2, String tag3,
			Date reminder, Date createdDate) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.tag1 = tag1;
		this.tag2 = tag2;
		this.tag3 = tag3;
		this.reminder = reminder;
		this.createdDate = createdDate;
	}
	
	
	public Date getReminder() {
		return reminder;
	}
	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTag1() {
		return tag1;
	}
	public void setTag1(String tag1) {
		this.tag1 = tag1;
	}
	public String getTag2() {
		return tag2;
	}
	public void setTag2(String tag2) {
		this.tag2 = tag2;
	}
	public String getTag3() {
		return tag3;
	}
	public void setTag3(String tag3) {
		this.tag3 = tag3;
	}
	
	
	
}

