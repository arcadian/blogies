package com.web.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.mongo.blog.entities.BlogEntry;



public class PostNavigatorTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private List<BlogEntry> posts;
	

	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			StringBuilder sb = new StringBuilder();
			for(BlogEntry post: posts){
				sb.append("<ul id=\"postlist\">");
				sb.append("<li id=\"").append(post.getBlogId().toString()).append("\">");
				sb.append("<a href=\"#\" onclick=\"return showpost(\'").append(post.getBlogId().toString()).append("\');\">").append(post.getTitle()).append("</a></li>");
				sb.append("</ul>");
			}
			
			out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
		
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
	
	public List<BlogEntry> getPosts() {
		return posts;
	}

	public void setPosts(List<BlogEntry> posts) {
		this.posts = posts;
	}
}
