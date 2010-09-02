package com.web.tag;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.bson.types.ObjectId;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.mongo.blog.entities.Comment;



public class TreeCommentTag extends TagSupport {
	private static final long serialVersionUID = 1L;
	private static final String NEW_LINE = "\n";
	private List<Comment> comments;
	private static Integer counter = 0;
	
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			
			Map<ObjectId, Comment> nodes = new HashMap<ObjectId, Comment>();
			Multimap<ObjectId, Comment> rels = LinkedHashMultimap.create();
			List<Comment> roots = new ArrayList<Comment>();
			for(Comment comment: comments){
				//The following line is needed to clear the comments
				//because the following algorithm assumes no knowledge
				//about the number of children in the database. It builds
				//the structure and computes the children on the fly :)
				comment.setChildren(0);
				nodes.put(comment.getCommentId(), comment);
				if(comment.getParentId() == null) {
					roots.add(comment);
				} else {
					rels.put(comment.getParentId(), comment);
				}
			}
			//Will contain the nodes in the order we process them 
			//to be used for the placeholder for children in the formatting
			List<Comment> queue = new ArrayList<Comment>();
			StringBuilder finalBuilder = new StringBuilder();
			processBeginComments(finalBuilder);
			for(Comment root : roots){
				StringBuilder sb = new StringBuilder();
				root.setLevel(1);
				processComment(root, sb);
				traverse(root, rels, sb, nodes, queue);
				if(counter != queue.size()){
					System.out.println("Some problems here");
				}
				
				counter = 0;
				Object[] obj = new Object[queue.size()];
				int i=0;
				for(Comment temp : queue){
					obj[i++] = zeroNull(temp.getChildren());
				}
				finalBuilder.append(MessageFormat.format(sb.toString(), obj));
				
				queue = new ArrayList<Comment>();
			}
			out.println(finalBuilder.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	private Integer zeroNull(Integer num){
		return num == null ? 0 : num;
	}
	
	private void traverse(Comment parent, Multimap<ObjectId, Comment> map, StringBuilder buffer, 
							Map<ObjectId, Comment> nodes, List<Comment> queue){
		Collection<Comment> children = map.get(parent.getCommentId());
		queue.add(nodes.get(parent.getCommentId()));
	    if(children == null || children.isEmpty()) {
			buffer.append("</div>").append(NEW_LINE);
	        Comment par = nodes.get(parent.getParentId());
	        if(par != null) {
	        	par.setChildren(zeroNull(par.getChildren())+1);
	        }
	        return;
	    }
	    for(Comment child: children){
	    	child.setLevel(parent.getLevel()+1);
	    	processComment(child, buffer);
	    	traverse(child, map, buffer, nodes, queue);
	    }
	    Comment par = nodes.get(parent.getParentId());
	    if(par != null){
	    	par.setChildren(zeroNull(par.getChildren())+zeroNull(nodes.get(parent.getCommentId()).getChildren())+1);
	    }
	    buffer.append("</div>").append(NEW_LINE);
	}
	
	//remember to double the single quotes since after the MessageFormat the single quotes are eliminated
	//so for js argments use '' instead of ' and only escape in the string the first one
	//example hidecomment('asb', this); should be "hidecomments(\'',"+"abc"+"\'', this);\"";
	private void processComment(Comment node, StringBuilder sb){
		sb.append("<div class=\"commentinfo\">").append(NEW_LINE);
		sb.append("<a name=\"").append(node.getCommentId()).append("\"></a>").append(NEW_LINE);
		sb.append("<p><a href=\"#").append(node.getCommentId()).append("\">").append(node.getUserId()).append("</a>").append(node.getCommentDate());
		sb.append("<a onclick=\"hidecomment(\''").append(node.getCommentId()).append("\'',this);\" href=\"#\">[-]</a>");
		sb.append("{").append(counter++).append(",number,#}").append("children");
		sb.append("</p>").append(NEW_LINE);
		sb.append("<div class=\"commentcontents collapsed\" id=\"com-cont-").append(node.getCommentId());
		sb.append("\">").append(node.getText()).append("</div>");
		sb.append("<div><a href=\"newcomment.htm?commentId=").append(node.getCommentId().toString()).append("&blogId=").append(node.getBlogId().toString()).append("\">Reply</a></div>");
		if(node.getParentId() != null){
			sb.append("<div><a href=\"#").append(node.getParentId()).append("\">parent</a></div>").append(NEW_LINE);
		}
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
		sb.append("<div id=\"commentArea\">").append(NEW_LINE).
		append("<div id=\"commentContainer\">").append(NEW_LINE);
	}
	
	private String processEndComments(){
		StringBuilder sb = new StringBuilder();
		sb.append("</div>").append(NEW_LINE).append("</div>").append(NEW_LINE);
		return sb.toString();
	}
	
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
}
