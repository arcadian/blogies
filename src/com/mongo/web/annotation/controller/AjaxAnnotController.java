package com.mongo.web.annotation.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AjaxAnnotController{

	@RequestMapping("/ajax")
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		System.out.println("Returning xml....");
		return new ModelAndView("xmlView","content", "<div>Sweet Success</div>");
	}
}
