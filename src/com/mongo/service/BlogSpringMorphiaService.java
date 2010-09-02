package com.mongo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mongo.blog.dao.impl.BlogEntryDAOMorphiaImpl;
import com.mongo.blog.dao.impl.CommentDAOMorphiaImpl;
import com.mongo.blog.entities.BlogEntry;
import com.mongo.blog.entities.Comment;


public class BlogSpringMorphiaService {

	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws Exception{
		ClassPathXmlApplicationContext springContext = new ClassPathXmlApplicationContext(getSpringResources());
		
		CommentDAOMorphiaImpl commentDao = (CommentDAOMorphiaImpl) springContext.getBean("commentDAO");
		BlogEntryDAOMorphiaImpl blogEntryDAO = (BlogEntryDAOMorphiaImpl) springContext.getBean("blogEntryDAO");
		
		BlogEntry blog = new BlogEntry();
		blog.setTitle("First entry");
		blog.setBody("Bonjour!!");
		
		List<Comment> comments = new ArrayList<Comment>();
		Comment firstComment = new Comment();
		firstComment.setUserId("efellios");
		firstComment.setText("way to go!!");
		
		Comment secondComment = new Comment();
		secondComment.setUserId("mprobrien");
		secondComment.setText("Kudos to you dude!!");
		
		Comment thirdComment = new Comment();
		thirdComment.setUserId("mama");
		thirdComment.setText("Kudos to you son!!");
		
		Comment fourthComment = new Comment();
		fourthComment.setUserId("daddy");
		fourthComment.setText("Kudos to you my boy!!");
		
		Comment fifthComment = new Comment();
		fifthComment.setUserId("daddy friend");
		fifthComment.setText("Kudos to you daddy friend!!");
		
		comments.add(firstComment);
		comments.add(secondComment);
		comments.add(thirdComment);
		comments.add(fourthComment);
		comments.add(fifthComment);
		commentDao.save(firstComment);
		
		System.out.println("First :"+firstComment.getCommentId());
		secondComment.setParentId(firstComment.getCommentId());
		commentDao.save(secondComment);
		System.out.println("Second:"+secondComment.getParentId());
		//thirdComment.setParentId(firstComment.getCommentId());
		commentDao.save(thirdComment);
		System.out.println("Third:"+thirdComment.getParentId());
		fourthComment.setParentId(firstComment.getCommentId());
		commentDao.save(fourthComment);
		System.out.println("Fourth:"+secondComment.getParentId());
		fifthComment.setParentId(fourthComment.getCommentId());
		commentDao.save(fifthComment);
		blog.setComments(comments);
		blogEntryDAO.save(blog);
		System.out.println("Done");
		
		BlogEntry outBlog = blogEntryDAO.get(blog.getBlogId());
		outBlog.treeSort();
		List<Comment> outComments= outBlog.getComments();
		if(outComments != null){
			System.out.println(outBlog.getTitle()+"\n");
			System.out.println(outBlog.getBody());
			System.out.println("... Posted on"+outBlog.getPostedDate().toString());
			for(Comment comment: outComments){
				System.out.println(comment.getText());
				System.out.println("..."+comment.getUserId()+"..."+comment.getLevel());
				System.out.println("---- on "+comment.getCommentDate().toString());
			}
		}
		
		Comment sixthComment = new Comment();
		sixthComment.setUserId("daddy's friend friend");
		sixthComment.setText("Kudos to you daddy's friend friend!!");
		sixthComment.setParentId(secondComment.getParentId());
		commentDao.save(sixthComment);
		outBlog.addComment(sixthComment);
		//blogEntryDAO.updateComment(outBlog, sixthComment);
		blogEntryDAO.save(outBlog);
		outBlog.treeSort();
		outComments= outBlog.getComments();
		if(outComments != null){
			System.out.println(outBlog.getTitle()+"\n");
			System.out.println(outBlog.getBody());
			System.out.println("... Posted on"+outBlog.getPostedDate().toString());
			for(Comment comment: outComments){
				System.out.println(comment.getText());
				System.out.println("..."+comment.getUserId()+"..."+comment.getLevel());
				System.out.println("---- on "+comment.getCommentDate().toString());
			}
		}
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
