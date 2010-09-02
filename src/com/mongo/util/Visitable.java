package com.mongo.util;

public interface Visitable<N, S> {
	public N getNode();
	public S getState();
	public void setState(S state);
	public void accept(Visitor<N, S> visitor);
}
