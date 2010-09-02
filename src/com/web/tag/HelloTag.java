package com.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public class HelloTag implements Tag {
	   private PageContext pageContext;
	   private Tag parent;

	   public HelloTag() {
	      super();
	   }

	   public int doStartTag() throws JspException {
	      try {
	         pageContext.getOut().print("This is my first tag!");
	      } catch (IOException ioe) {
	         throw new JspException("Error:IOException while writing to client" + ioe.getMessage());
	      }
	      return SKIP_BODY;
	   }

	   public int doEndTag() throws JspException {
	      return EVAL_PAGE;
	   }

	   public void release() {
	   }

	   public void setPageContext(PageContext 
	   pageContext) {
	      this.pageContext = pageContext;
	   }

	   public void setParent(Tag parent) {
	      this.parent = parent;
	   }

	   public Tag getParent() {
	      return parent;
	   }
	}