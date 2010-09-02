package com.mongo.web.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

public class XmlView implements View {
 
	public XmlView(){ }
  
	public String getContentType() {
		return "text/xml";
    }
  
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String content = (String) model.get("content");
		response.setContentType(getContentType());
	    response.setContentLength(content.length());
	    PrintWriter out = new PrintWriter(response.getOutputStream());
	    out.print(content);
	    out.flush();
	    out.close();    
	}
}