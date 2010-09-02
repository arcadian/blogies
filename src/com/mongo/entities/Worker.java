package com.mongo.entities;

import com.google.code.morphia.annotations.Entity;


@Entity("person")
public class Worker extends Person{

	private String title;
	
	public Worker(){}
	
	public Worker(String firstName, String lastName, String title){
		super(firstName, lastName);
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
