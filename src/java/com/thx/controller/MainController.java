package com.thx.controller;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MainController {

	@GetMapping("/hello")
	public String err() throws JSONException {
		System.out.println("55555");
		return "forword:404.html";
	}

	@GetMapping("/test")
	public void test(@PathVariable String name, HttpServletResponse response) throws IOException {
		response.sendRedirect("/404.html");
	}
}
