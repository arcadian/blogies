package com.mongo.aop;

import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;

public class CacheAnnotationMatchingPointcut extends AnnotationMatchingPointcut{

	public CacheAnnotationMatchingPointcut(){
		super(null, Cacheable.class);
	}
}
