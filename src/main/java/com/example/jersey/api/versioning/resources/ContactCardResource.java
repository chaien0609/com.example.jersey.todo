package com.example.jersey.api.versioning.resources;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;

import com.example.jersey.api.versioning.domains.ContactCard;

@Path("/contact")
public class ContactCardResource {

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getContact(@NotNull @PathParam("id") String contactid) {
		System.out.println("OUT: " + contactid);
		return "Response";
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String registerContact(@Valid @NotNull ContactCard contact) {
		if(contact == null) {
			contact = new ContactCard();
		}
		System.out.println("OUT: " + contact.getId());
		return "Response";
	}
}
