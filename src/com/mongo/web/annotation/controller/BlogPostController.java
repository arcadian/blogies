package com.mongo.web.annotation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mongo.blog.dao.impl.BlogEntryDAOMorphiaImpl;
import com.mongo.blog.dao.impl.CommentDAOMorphiaImpl;
import com.mongo.blog.entities.BlogEntry;
import com.mongo.blog.entities.Comment;

@Controller
public class BlogPostController{

	@Resource @Qualifier("blogEntryDAO") private BlogEntryDAOMorphiaImpl blogDAO;
	@Resource @Qualifier("commentDAO") private CommentDAOMorphiaImpl commentDAO;
	
	@RequestMapping("/blogposts")
	public ModelAndView postBlogs(){
		System.out.println("Returning easy view");
		BlogEntry blog = new BlogEntry();
		blog.setTitle("First entry");
		blog.setBody("Bonjour!!");
		blogDAO.save(blog);
		List<Comment> comments = new ArrayList<Comment>();
		Comment firstComment = new Comment();
		firstComment.setUserId("efellios");
		firstComment.setText("way to go!!");
		firstComment.setBlogId(blog.getBlogId());
		
		Comment secondComment = new Comment();
		secondComment.setUserId("makis");
		secondComment.setText("Kudos makis!!");
		secondComment.setBlogId(blog.getBlogId());
		
		Comment thirdComment = new Comment();
		thirdComment.setUserId("mama");
		thirdComment.setText("Kudos mama!!");
		thirdComment.setBlogId(blog.getBlogId());
		
		Comment fourthComment = new Comment();
		fourthComment.setUserId("daddy");
		fourthComment.setText("Kudos daddy!!");
		fourthComment.setBlogId(blog.getBlogId());
		
		Comment fifthComment = new Comment();
		fifthComment.setUserId("daddy friend");
		fifthComment.setText("Kudos friend!!");
		fifthComment.setBlogId(blog.getBlogId());
		
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
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("blogEntry", outBlog);
		List<BlogEntry> posts = blogDAO.get();
		model.put("posts", posts);
        return new ModelAndView("blogposts", model);
	}
	
	@RequestMapping("/blog")
	public ModelAndView blog(@RequestParam String blogid){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("content", blogDAO.get(new ObjectId(blogid)).getBody());
		return new ModelAndView("xmlView", model);
	}
	
	@RequestMapping("/create")
	public ModelAndView create(){
		return new ModelAndView("create", new HashMap<String, Object>());
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView save(BlogEntry blog, BindingResult result){
		if(result.hasErrors() || !passValidation(blog)){
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("errorMsg", "Validate blog form fields");
			return new ModelAndView("create", model);
		}
		blogDAO.save(blog);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("blogEntry", blog);
		List<BlogEntry> posts = blogDAO.get();
		model.put("posts", posts);
		return new ModelAndView("blogposts", model);
	}
	
	@RequestMapping("/newcomment")
	public ModelAndView newComment(@RequestParam String commentId, @RequestParam String blogId){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("blogId", blogId);
		model.put("parentId", commentId);
		return new ModelAndView("comment", model);
	}
	
	@RequestMapping("/addcomment")
	public ModelAndView addComment(@RequestParam String blogId, @RequestParam String parentId, String text){
		BlogEntry blogRef = blogDAO.get(new ObjectId(blogId));
		Comment comment = new Comment();
		if(parentId != null && parentId.trim().length() > 0){
			comment.setParentId(new ObjectId(parentId));
		}
		comment.setText(text);
		comment.setBlogId(new ObjectId(blogId));
		blogRef.addComment(comment);
		commentDAO.save(comment);
		blogDAO.save(blogRef);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("blogEntry", blogRef);
		List<BlogEntry> posts = blogDAO.get();
		model.put("posts", posts);
		return new ModelAndView("blogposts", model);
	}
	
	@RequestMapping("/posts")
	public ModelAndView getPosts(@RequestParam(required=false) Integer pageNum){
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("posts", blogDAO.get());
		model.put("pageNum", pageNum != null ? pageNum : 0);
		return new ModelAndView("posts", model);
	}
	
	@RequestMapping("/viewpost")
	public ModelAndView viewPost(@RequestParam(required=false) String blogId){
		BlogEntry entry = null;
		if(blogId == null){
			List<BlogEntry> blogList = blogDAO.get();
			if(blogList != null && !blogList.isEmpty()){
				entry = blogList.get(0);
			} 
		} else {
			entry = blogDAO.get(new ObjectId(blogId));
		}
		entry.calcChildrenComments();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("blogEntry", entry);
		return new ModelAndView("viewpost", model);
	}
	
	private boolean passValidation(BlogEntry blog){
		return blog.getTitle().trim().length() > 0 && blog.getBody().trim().length() > 0;
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
