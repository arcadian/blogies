package com.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import com.mongo.blog.entities.BlogEntry;

public class PostPaginateTag extends AbstractPaginateTag<BlogEntry> {
	private static final long serialVersionUID = 1L;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			fixPageNum();
			StringBuilder sb = new StringBuilder();
			for(BlogEntry post: filter()){
				sb.append("<div class=\"posttitle\">").append("<p>").append(post.getTitle()).append("</p></div>");
				sb.append("<div class=\"blogentryarea\">").append("<p>").append(post.getBody()).append("</p></div>");
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
}
