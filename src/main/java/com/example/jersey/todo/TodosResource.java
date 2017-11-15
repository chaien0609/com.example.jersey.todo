package com.example.jersey.todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import com.example.jersey.todo.Todo;
import com.example.jersey.todo.TodoDao;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.BasicAuthDefinition;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.SecurityDefinition;

// Will map the resource to the URL todos
@Api(value="/todos")
@Path("/todos")
public class TodosResource {

    // Allows to insert contextual objects into the class,
    // e.g. ServletContext, Request, Response, UriInfo
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    // Return the list of todos to the user in the browser
    @GET
    @ApiOperation(value="Get all Todos", notes="Get all Todos")
    @Produces(MediaType.TEXT_XML)
    public List<Todo> getTodosBrowser() {
        List<Todo> todos = new ArrayList<Todo>();
        todos.addAll(TodoDao.instance.getModel().values());
        return todos;
        
    }

    // Return the list of todos for applications
    @GET
    @ApiOperation(value="Get all Todos for Application XML", notes="Get all Todos for Application XML")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Todo> getTodos() {
        List<Todo> todos = new ArrayList<Todo>();
        todos.addAll(TodoDao.instance.getModel().values());
        return todos;
    }

    // retuns the number of todos
    // Use http://localhost:8080/jp.co.recomot.jersey.todo/api/todos/count
    // to get the total number of records
    @GET
    @Path("count")
    @ApiOperation(value="Count取得", notes="Get count for Todos")
    @Produces(MediaType.APPLICATION_JSON)
    public String getCount() {
        int count = TodoDao.instance.getModel().size();
        return String.valueOf(count);
    }

    @POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void newTodo(@FormParam("id") String id,
            @FormParam("summary") String summary,
            @FormParam("description") String description,
            @Context HttpServletResponse servletResponse) throws IOException {
        Todo todo = new Todo(id, summary);
        if (description != null) {
            todo.setDescription(description);
        }
        TodoDao.instance.getModel().put(id, todo);

        servletResponse.sendRedirect("../create_todo.html");
    }

    // Defines that the next path parameter after todos is
    // treated as a parameter and passed to the TodoResources
    // Allows to type http://localhost:8080/jp.co.recomot.jersey.todo/api/todos/1
    // 1 will be treaded as parameter todo and passed to TodoResource
    @GET
    @ApiOperation(
    		value="Find Todo by id",
    		notes="Find Todo by id",
    		response=TodoResource.class,
    		authorizations = {
    		        @Authorization(value = "basicAuth")
    		    })
    @Path("{todo}")
    @Produces({MediaType.APPLICATION_JSON})
    public TodoResource getTodo(
    		@ApiParam(value = "Find by id", required = true)
    		@PathParam("todo") String id) {
        return new TodoResource(uriInfo, request, id);
    }

}