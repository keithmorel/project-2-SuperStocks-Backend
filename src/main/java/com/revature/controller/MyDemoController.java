package com.revature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.service.MyDemoService;

@Controller
public class MyDemoController {
	
	@Autowired
	private MyDemoService demoService;
	
	// Constructor for Mockito testing (unit testing with controllers requires mockMVC library to mock HTTP requests)
	public MyDemoController(MyDemoService myDemoService) {
		this.demoService = myDemoService;
	}
	
	@RequestMapping(path="hello", produces= {"application/json"})
	@ResponseBody // serializes request body into JSON
	public ResponseEntity<String> ourFirstEndpoint() {
		return ResponseEntity.ok(demoService.demo());
	}

}
