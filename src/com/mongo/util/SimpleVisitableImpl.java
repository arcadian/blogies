package com.mongo.util;

public class SimpleVisitableImpl<N, S> implements  Visitable<N, S> {

	private N node;
	private S state;
	
	public SimpleVisitableImpl(N node){
		this.node = node;
	}
	public SimpleVisitableImpl (N node, S state){
		this.node = node;
		this.state = state;
	}
	
	public N getNode() {
		return this.node;
	}
	
	public S getState() {
		return this.state;
	}
	
	public void setState(S state) {
		this.state = state;
		
	}
	
	public void accept(Visitor<N, S> visitor) {
		visitor.visit(this);	
	}
}
