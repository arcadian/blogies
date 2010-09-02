package com.mongo.service;

import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;

import com.google.code.morphia.DAO;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.DatastoreImpl;
import com.google.code.morphia.Morphia;
import com.mongo.dao.impl.PersonDAOMorphiaImpl;
import com.mongo.entities.Address;
import com.mongo.entities.Person;
import com.mongodb.Mongo;

public class MongoMorphiaService {

	public static void main(String args[]) throws Exception{
		@SuppressWarnings("rawtypes")
		Set<Class> mappedClasses = new HashSet<Class>();
		mappedClasses.add(Person.class);
		mappedClasses.add(Address.class);
		Datastore ds = new DatastoreImpl(new Morphia(mappedClasses), new Mongo(), "test");
		DAO<Person, ObjectId> dao = new PersonDAOMorphiaImpl(ds);
		Person person = new Person("efakis", "mellios");
		person.setAddress(new Address("clinton", "US", 717));
		dao.save(person);
		ObjectId id = person.getId();
		System.out.println("Retrieving id: "+id);
		Person person2 = dao.get(id);
		//Person person2 = dao.get("4c72d76d92d4ec6078869863");
		System.out.println("Retrieved name: "+person2.getFirstName());
	}
}
