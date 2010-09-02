package com.mongo.dao.impl;

import org.bson.types.ObjectId;

import com.google.code.morphia.DAO;
import com.google.code.morphia.Datastore;
import com.mongo.entities.Person;

public class PersonDAOMorphiaImpl extends DAO<Person, ObjectId>{

	public PersonDAOMorphiaImpl(Datastore ds){
		super(ds);
	}

	@Override
	public void save(Person person){
		save(Person.class.getSimpleName(), person);
	}
	
	public void save(String kind, Person person){
		ds.save(kind, person);
	}
	
	/*@Override
	public Person get(ObjectId id){
		return ds.get(Person.class.getSimpleName(), Person.class, id);
	}
	
	public Person get(Class<? extends Person> clazz, ObjectId id){
		return ds.get(clazz, id);
	}*/
}
