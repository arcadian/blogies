package com.mongo.dao.impl;

import org.bson.types.ObjectId;

import com.google.code.morphia.DAO;
import com.google.code.morphia.Datastore;
import com.mongo.entities.Job;

public class JobDAOMorphiaImpl extends DAO<Job, ObjectId>{

	public JobDAOMorphiaImpl(Datastore ds){
		super(ds);
	}

	@Override
	public void save(Job job){
		save(Job.class.getSimpleName(), job);
	}
	
	public void save(String kind, Job job){
		ds.save(kind, job);
	}
}
