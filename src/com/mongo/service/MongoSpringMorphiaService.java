package com.mongo.service;

import org.bson.types.ObjectId;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.code.morphia.DAO;
import com.mongo.dao.impl.JobDAOMorphiaImpl;
import com.mongo.dao.impl.PersonDAOMorphiaImpl;
import com.mongo.entities.Address;
import com.mongo.entities.Job;
import com.mongo.entities.Person;
import com.mongo.entities.Worker;


public class MongoSpringMorphiaService {

	public static void main(String args[]) throws Exception{
		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(getSpringResources());
		PersonDAOMorphiaImpl dao = (PersonDAOMorphiaImpl)springContext.getBean("personDAO");
		JobDAOMorphiaImpl jobDao = (JobDAOMorphiaImpl)springContext.getBean("jobDAO");
		Job job = new Job("boss");
		jobDao.save(job);
		Person person = new Person("efakis", "mellios");
		person.setAddress(new Address("clinton", "US", 717));
		person.setJob(job);
		dao.save(person);
		ObjectId id = person.getId();
		System.out.println("Retrieving id: "+id);
		Person person2 = dao.get(id);
		Address add2 = person2.getAddress();
		Job job2 = person2.getJob();
		//Person person2 = dao.get("4c72d76d92d4ec6078869863");
		System.out.println("Retrieved name: "+person2.getFirstName());
		
		Worker worker = new Worker("takis", "thanos", "Mr");
		worker.setAddress(person.getAddress());
		
		dao.save(worker);
		System.out.println(dao.get(worker.getId()).getClass().toString());
		System.out.println(dao.get(worker.getId()).getLastName());
		
		worker = new Worker("maria", "hanos", "Mr");
		worker.setAddress(person.getAddress());
		dao.save(worker.getClass().getSimpleName(), worker);
	}
	
	 public static String[] getSpringResources(String... resources){
		 String[] defaultRsrcs = {
	        			"/spring/database.xml",
	        			"/spring/dao.xml"
	        		};

		 String[] rsrcs = new String[defaultRsrcs.length+(resources != null ? resources.length : 0)];
		 int idx = 0;
		 for(String rsrc : defaultRsrcs){
    		rsrcs[idx++] = rsrc;
		 }
		 if(resources != null){
			 for(String rsrc : resources){
				 rsrcs[idx++] = rsrc;
			 }
		 }
		 return rsrcs;
	}

}
