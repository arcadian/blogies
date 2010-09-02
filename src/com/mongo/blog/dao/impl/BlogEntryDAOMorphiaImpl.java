package com.mongo.blog.dao.impl;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.DAO;
import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.mongo.blog.entities.BlogEntry;
import com.mongo.blog.entities.Comment;
import com.mongo.aop.Cacheable;
import com.mongodb.Mongo;


public class BlogEntryDAOMorphiaImpl extends DAO<BlogEntry, ObjectId>{

	//needed for AOP :(
	public BlogEntryDAOMorphiaImpl() throws Exception{
		super(BlogEntry.class, new Mongo(),new Morphia(),"test");
	}
	
	public BlogEntryDAOMorphiaImpl(Datastore ds){
		super(ds);
	}
	
	public void save(BlogEntry blogEntry){
		blogEntry.setPostedDate(new Date());
		super.save(blogEntry);
	}
	
	public void updateComment(BlogEntry blogEntry, Comment comment){
		Query<BlogEntry> updateQuery = ds.createQuery(BlogEntry.class).field("_id").equal(blogEntry.getBlogId());
		UpdateOperations<BlogEntry>  ops = ds.createUpdateOperations(BlogEntry.class).add("comment", comment);
		ds.update(updateQuery, ops);
	}
	
	@Cacheable("blog")
	public List<BlogEntry> get(){
		Query<BlogEntry> query = ds.createQuery(BlogEntry.class).order("-postedDate");
		return query.asList();
	}
	
	
}
