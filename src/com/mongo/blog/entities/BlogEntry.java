package com.mongo.blog.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Reference;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.mongo.util.CalcChildrenVisitor;
import com.mongo.util.SimpleVisitableImpl;
import com.mongo.util.State;
import com.mongo.util.Visitable;
import com.mongo.util.Visitor;


@Entity("blog")
public class BlogEntry {
	
	@Id
	private ObjectId blogId;

	private String title;
	private String body;
	private Date postedDate;
	
	@Reference("comment")
	private List<Comment> comments = new ArrayList<Comment>();
	
	public BlogEntry(){}
	
	public void addComment(Comment comment){
		comments.add(comment);
	}
	
	public ObjectId getBlogId() {
		return blogId;
	}

	public void setBlogId(ObjectId blogId) {
		this.blogId = blogId;
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}
	
	@Deprecated
	public void treeSort(){
		//Will contain (parentId, children->Collection<Comment>)
		Multimap<ObjectId, Comment> rels = LinkedHashMultimap.create();
		List<Comment> roots = new ArrayList<Comment>();
		for(Comment comment: comments){
			if(comment.getParentId() == null) {
				roots.add(comment);
			} else {
				rels.put(comment.getParentId(), comment);
			}
		}
		
		List<Comment> treeSortedComments = new ArrayList<Comment>();
		for(Comment root : roots){
			root.setLevel(1);
			treeSortedComments.add(root);
			traverse(root, rels, treeSortedComments);
		}
		this.comments = treeSortedComments;
	}
	
	@Deprecated
	private void traverse(Comment parent, Multimap<ObjectId, Comment> map, List<Comment> list){
		Collection<Comment> children = map.get(parent.getCommentId());
	    if(children == null) return;
	    for(Comment child: children){
	    	child.setLevel(parent.getLevel()+1);
	    	list.add(child);
	        traverse(child, map, list);
	    }
	}
	
	public void calcChildrenComments(){
		CalcChildrenVisitor childrenCalculator = new CalcChildrenVisitor(comments);
		processForest(childrenCalculator);
		comments =  new ArrayList<Comment>(childrenCalculator.getTopology().values());
	}
			
	public void processForest(Visitor<Comment, State> visitor){
		Multimap<ObjectId, Comment> rels = LinkedHashMultimap.create();
		List<Comment> roots = new ArrayList<Comment>();
		for(Comment comment: comments){
			if(comment.getParentId() == null) {
				roots.add(comment);
			} else {
				rels.put(comment.getParentId(), comment);
			}
		}
		
		for(Comment root : roots){
			root.setLevel(1);
			traverseTree(root, rels, visitor);
		}
	}
	
	private void traverseTree(Comment node, Multimap<ObjectId, Comment> map, Visitor<Comment, State> visitor){
		 Visitable<Comment, State> visitableComment = new SimpleVisitableImpl<Comment, State>(node, State.DISCOVERED);
		 visitableComment.accept(visitor);
		 
		 Collection<Comment> children = map.get(node.getCommentId());
		 if(children == null || children.isEmpty()) {
			 visitableComment.setState(State.PROCESSING);
			 visitableComment.accept(visitor);
			 return;
		 }
		 for(Comment child: children){
			 child.setLevel(node.getLevel()+1);
			 traverseTree(child, map, visitor);
		 }
		 visitableComment.setState(State.PROCESSED);
		 visitableComment.accept(visitor);
	}
}
