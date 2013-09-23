package com.dmitrynikol.services;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.dmitrynikol.model.User;

/**
 * UserService represent a Jersey resources that will be 
 * manipulated by different HTTP methods.
 * 
 * @author Dmitry Nikolaenko
 *
 */
@Path("/user")
public class UserService {
	
	/**
	 * Method handling HTTP GET requests, returned object will be
	 * sent to the client as "text/plain" media type.
	 * 
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/stub")
	public String getStub() {
		return "stub";
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public Response getAllUsers(@QueryParam("page") @DefaultValue("1") final String page) {
		// # See all the users in the system 
		// > curl -X GET http://localhost:8080/RESTfulApp/rest/user/all?page=3
		List<User> users = new ArrayList<User>();
		final User user1 = new User("1", "Tom", "Jenkins", "tom.jenkins@gmail.com");
		final User user2 = new User("2", "Red", "Balloon", "red.balloon@gmail.com");
		users.add(user1);
		users.add(user2);
	    return Response.ok(users).build();
	}
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(final User user) throws URISyntaxException {
		// # Create and save new user
		// > curl -X POST http://localhost:8080/RESTfulApp/rest/user/create
		return Response.ok(user).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public Response getUserById(@PathParam("id") final String id) throws URISyntaxException {
		// # Get the information of the user
		// > curl -X GET http://localhost:8080/RESTfulApp/rest/user/id/12345
		final User user = new User("12345", "Max", "Liano", "maxliano@gmail.com");
		return Response.ok(user).build();
	}
	
	// POST is used create a new resource, PUT method updates the state of a known resource. 
	@PUT
	@Path("/id/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("id") final String id, final User user)  throws URISyntaxException {
		// # Update user information
		// > curl -X PUT http://localhost:8080/RESTfulApp/rest/user/id/12345
		return Response.ok(user).build();
	}
	
	@DELETE
	@Path("/id/{id}")
	public Response deleteUser(@PathParam("id") final String id) throws URISyntaxException {
		// create a new ResponseBuilder with an OK status and appropriate a Response instance
		// Delete user
		// > curl -X DELETE http://localhost:8080/RESTfulApp/rest/user/id/12345
		return Response.ok().entity("User with ".concat(id).concat(" is deleted successfully")).build();
	}
}