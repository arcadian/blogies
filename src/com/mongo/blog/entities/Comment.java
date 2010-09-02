package com.mongo.blog.entities;

import java.util.Date;

import org.bson.types.ObjectId;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Transient;

@Entity("comment")
public class Comment {

	@Id
	private ObjectId commentId;
	private ObjectId parentId;
	private String userId;
	private String text;
	private Integer level;
	@Transient private Integer children;
	private ObjectId blogId;
	
	private Date commentDate;
	
	public Comment(){}
	
	public ObjectId getCommentId() {
		return commentId;
	}
	public void setCommentId(ObjectId commentId) {
		this.commentId = commentId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ObjectId getParentId() {
		return parentId;
	}
	public void setParentId(ObjectId parentId) {
		this.parentId = parentId;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	public Date getCommentDate() {
		return commentDate;
	}

	public void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}
	public Integer getChildren() {
		return children;
	}

	public void setChildren(Integer children) {
		this.children = children;
	}
	
	public ObjectId getBlogId() {
		return blogId;
	}

	public void setBlogId(ObjectId blogId) {
		this.blogId = blogId;
	}
}
