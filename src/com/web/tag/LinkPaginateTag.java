package com.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.mongo.blog.entities.BlogEntry;

public class LinkPaginateTag extends AbstractPaginateTag<BlogEntry> {
	private static final long serialVersionUID = 1L;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			fixPageNum();
			StringBuilder sb = new StringBuilder();
			for(BlogEntry post: filter()){
				sb.append("<ul id=\"postlist\">");
				sb.append("<li id=\"").append(post.getBlogId().toString()).append("\">");
				sb.append("<a href=\"viewpost.htm?blogId=").append(post.getBlogId().toString()).append("\" >").append(post.getTitle()).append("</a></li>");
				sb.append("</ul>");
			}
			printPageLinks(sb);
			out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	private void printPageLinks(StringBuilder sb){
		if(pageNum > 1){
			sb.append("<div>").append("<a href=\"posts.htm?pageNum=").append(pageNum-1).append("\">").append("Previous Posts</a></div>");
		}
		if((pageNum)*PAGE_SIZE < container.size()){
			sb.append("<div>").append("<a href=\"posts.htm?pageNum=").append(pageNum+1).append("\">").append("Next Posts</a></div>");
		}
	}
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}
