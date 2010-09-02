package com.web.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.mongo.blog.entities.BlogEntry;
import com.mongo.blog.entities.Comment;
import com.mongo.util.State;
import com.mongo.util.Utils;
import com.mongo.util.Visitable;
import com.mongo.util.Visitor;



public class SimpleTreeCommentTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private BlogEntry blogEntry;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			StringBuilder buffer = new StringBuilder();
			processBeginComments(buffer);
			PrintTreeVisitor printer = new PrintTreeVisitor();
			blogEntry.processForest(printer);
			buffer.append(printer.getTrail());
			out.println(buffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.println(processEndComments());
		} catch (IOException io){
			io.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	private void processBeginComments(StringBuilder sb){
		sb.append("<div id=\"commentArea\"><div id=\"commentContainer\">");
	}
	
	private String processEndComments(){
		return "</div></div>";
	}
	
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}
	
	static class PrintTreeVisitor implements Visitor<Comment, State>{
		
		private StringBuilder sb = new StringBuilder();
		
		public String getTrail(){
			return sb.toString();
		}
		
		public void visit(Visitable<Comment, State> visitable) {
			switch(visitable.getState()){
				case DISCOVERED : 
						printElem(visitable.getNode());
						break;
				case PROCESSING :
				case PROCESSED :
						sb.append("</div>");
						break;
				default:
						break;
			}
		}
		
		private void printElem(Comment node){
			sb.append("<div class=\"commentinfo\">");
			sb.append("<a name=\"").append(node.getCommentId()).append("\"></a>");
			sb.append("<p><a href=\"#").append(node.getCommentId()).append("\">").append(node.getUserId()).append("</a>").append(node.getCommentDate());
			sb.append("<a onclick=\"hidecomment(\'").append(node.getCommentId()).append("\',this);\" href=\"#\">[-]</a>");
			sb.append(Utils.zeroNull(node.getChildren())).append("children");
			sb.append("</p>");
			sb.append("<div class=\"commentcontents collapsed\" id=\"com-cont-").append(node.getCommentId());
			sb.append("\">").append(node.getText()).append("</div>");
			sb.append("<div><a href=\"newcomment.htm?commentId=").append(node.getCommentId().toString()).append("&blogId=").append(node.getBlogId().toString()).append("\">Reply</a></div>");
			if(node.getParentId() != null){
				sb.append("<div><a href=\"#").append(node.getParentId()).append("\">parent</a></div>");
			}
		}
	}
}
