package com.mongo.blog.dao.impl;


import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.DAO;
import com.google.code.morphia.Datastore;
import com.mongo.blog.entities.Comment;

public class CommentDAOMorphiaImpl extends DAO<Comment, ObjectId>{

	public CommentDAOMorphiaImpl(Datastore ds){
		super(ds);
	}
	
	@Override
	public void save(Comment comment){
		comment.setCommentDate(new Date());
		super.save(comment);
	}
}
