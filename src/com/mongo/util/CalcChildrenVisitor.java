package com.mongo.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bson.types.ObjectId;

import com.mongo.blog.entities.Comment;

public class CalcChildrenVisitor implements Visitor<Comment, State>{
	private Map<ObjectId, Comment> topology;
		
	public CalcChildrenVisitor(Collection<Comment> topology){
		this.topology = new LinkedHashMap<ObjectId, Comment>();
		for(Comment comment: topology){
			comment.setChildren(0);
			this.topology.put(comment.getCommentId(), comment);
		}
	}
				
	public void visit(Visitable<Comment, State> visitable) {
		Comment parent = topology.get(visitable.getNode().getParentId());
		if(parent == null) return;
		switch(visitable.getState()){
			case PROCESSING :
					parent.setChildren(Utils.zeroNull(parent.getChildren())+1);
					break;
			case PROCESSED :
				parent.setChildren(Utils.zeroNull(parent.getChildren())+Utils.zeroNull(topology.get(visitable.getNode().getCommentId()).getChildren())+1);
					break;
			default:
				break;
		}
	}
	
	public Map<ObjectId, Comment> getTopology(){
		return topology;
	}		
}