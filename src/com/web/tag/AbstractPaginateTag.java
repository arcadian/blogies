package com.web.tag;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.tagext.TagSupport;

public abstract class AbstractPaginateTag<T> extends TagSupport {
	private static final long serialVersionUID = 1L;
	protected static final Integer PAGE_SIZE = 10;
	
	protected List<T> container;
	
	protected Integer pageNum;
	
	protected List<T> filter(){
		List<T> result = new ArrayList<T>();
		if(showFirstChunk()){
			for(T entry: container){
				result.add(entry);
				if(result.size() >= PAGE_SIZE) break;
			}
		} else {
			int begin = (pageNum-1) * PAGE_SIZE;
			int end = pageNum * PAGE_SIZE;
			for(int i=begin; i<end; i++){
				if(i==container.size()) break;
				result.add(container.get(i));
			}
		}
		return result;
	}
	
	protected boolean showFirstChunk(){
		return (pageNum == null) 
				|| pageNum <= 1 
				|| ((pageNum-1) * PAGE_SIZE) >= container.size();
	}
	
	protected void fixPageNum(){
		if(showFirstChunk()) setPageNum(1);
	}
	
	public List<T> getContainer() {
		return container;
	}

	public void setContainer(List<T> container) {
		this.container = container;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

}
