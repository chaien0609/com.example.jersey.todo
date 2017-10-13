package com.example.jersey.todo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.swagger.jaxrs.config.BeanConfig;

public class SwaggerBootstrap extends HttpServlet{

	@Override
	public void init() throws ServletException {
		super.init();
		
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("0.0.1");
		beanConfig.setSchemes(new String[]{"http","https"});
		beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/com.example.jersey.todo/api");
		beanConfig.setResourcePackage("com.example.jersey.todo");
		beanConfig.setPrettyPrint(true);
		beanConfig.setScan(true);
	}
}
