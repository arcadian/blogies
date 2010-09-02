package com.mongo.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class CacheAdderAdvice implements MethodInterceptor {

	public Object invoke(MethodInvocation invocation) throws Throwable {
		String cacheRegion = getAnnotationValue(invocation);
		//add to cache
		if(cacheRegion==null){
			System.out.println("could not find default region to add");
			cacheRegion = "generalArea";
		} else {
			System.out.println("Adding to region: "+cacheRegion);
			
		}
        try {
            return invocation.proceed();
        } finally {
        	//add to cache
        }
	}

	 private String getAnnotationValue(MethodInvocation invocation) {
		 Cacheable annotation =  invocation.getMethod().getAnnotation(Cacheable.class);
		 return annotation == null ? null : annotation.value();
	 }
}
