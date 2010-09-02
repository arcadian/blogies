package com.mongo.entities;

import com.google.code.morphia.annotations.Embedded;

@Embedded
public class Address {

	private String street;
	private String country;
	private Integer number;

	public Address(){
		
	}
	public Address(String street, String country, Integer number){
		this.street = street;
		this.country = country;
		this.number = number;
	}
	
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
}
