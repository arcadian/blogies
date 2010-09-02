package com.mongo.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.mongo.blog.dao.impl.BlogEntryDAOMorphiaImpl;
import com.mongo.blog.dao.impl.CommentDAOMorphiaImpl;
import com.mongo.blog.entities.BlogEntry;
import com.mongo.blog.entities.Comment;


public class EasyController implements Controller{

	private BlogEntryDAOMorphiaImpl blogDAO;
	private CommentDAOMorphiaImpl commentDAO;
	
	
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		System.out.println("Returning easy view");
		BlogEntry blog = new BlogEntry();
		blog.setTitle("First entry");
		blog.setBody("Bonjour!!");
		
		List<Comment> comments = new ArrayList<Comment>();
		Comment firstComment = new Comment();
		firstComment.setUserId("efellios");
		firstComment.setText("way to go!!");
		
		Comment secondComment = new Comment();
		secondComment.setUserId("makis");
		secondComment.setText("Kudos makis!!");
		
		Comment thirdComment = new Comment();
		thirdComment.setUserId("mama");
		thirdComment.setText("Kudos mama!!");
		
		Comment fourthComment = new Comment();
		fourthComment.setUserId("daddy");
		fourthComment.setText("Kudos daddy!!");
		
		Comment fifthComment = new Comment();
		fifthComment.setUserId("daddy friend");
		fifthComment.setText("Kudos friend!!");
		
		comments.add(firstComment);
		comments.add(secondComment);
		comments.add(thirdComment);
		comments.add(fourthComment);
		comments.add(fifthComment);
		commentDAO.save(firstComment);
		
		System.out.println("First :"+firstComment.getCommentId());
		secondComment.setParentId(firstComment.getCommentId());
		commentDAO.save(secondComment);
		System.out.println("Second:"+secondComment.getParentId());
		//thirdComment.setParentId(firstComment.getCommentId());
		commentDAO.save(thirdComment);
		System.out.println("Third:"+thirdComment.getParentId());
		fourthComment.setParentId(firstComment.getCommentId());
		commentDAO.save(fourthComment);
		System.out.println("Fourth:"+secondComment.getParentId());
		fifthComment.setParentId(fourthComment.getCommentId());
		commentDAO.save(fifthComment);
		blog.setComments(comments);
		blogDAO.save(blog);
		System.out.println("Done");
		
		BlogEntry outBlog = blogDAO.get(blog.getBlogId());
		//outBlog.treeSort();
		if(!outBlog.getComments().isEmpty()) {
			String text = outBlog.getComments().get(0).getText();
			System.out.println("Adding text: "+text);
		}
        return new ModelAndView("easy", "blogEntry", outBlog);
	}

	public BlogEntryDAOMorphiaImpl getBlogDAO() {
		return blogDAO;
	}

	public void setBlogDAO(BlogEntryDAOMorphiaImpl blogDAO) {
		this.blogDAO = blogDAO;
	}
	
	public CommentDAOMorphiaImpl getCommentDAO() {
		return commentDAO;
	}

	public void setCommentDAO(CommentDAOMorphiaImpl commentDAO) {
		this.commentDAO = commentDAO;
	}

}
