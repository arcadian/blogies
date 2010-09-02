package com.mongo.util;

public interface Visitor<N, S> {
	public void visit(Visitable<N, S> visitable);	
}
