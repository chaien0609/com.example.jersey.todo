package com.example.jersey.todo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import io.swagger.annotations.BasicAuthDefinition;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.config.BeanConfig;

@SwaggerDefinition(
        securityDefinition = @SecurityDefinition(
                basicAuthDefinitions = {
                        @BasicAuthDefinition(key = "basicAuth")}
        )
)
public class SwaggerBootstrap extends HttpServlet{

	@Override
	public void init() throws ServletException {
		super.init();
		
		BeanConfig beanConfig = new BeanConfig();
		//beanConfig.setVersion("0.0.1");
		//beanConfig.setSchemes(new String[]{"http","https"});
		//beanConfig.setHost("localhost:8080");
		beanConfig.setBasePath("/com.example.jersey.todo/api");
		beanConfig.setResourcePackage("com.example.jersey.todo,"
				+ "com.example.jersey.api.versioning.resources");
		beanConfig.setPrettyPrint(true);
		beanConfig.setScan(true);
	}
}
