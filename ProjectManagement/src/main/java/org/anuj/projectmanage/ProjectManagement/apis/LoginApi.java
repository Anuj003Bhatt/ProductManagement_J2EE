package org.anuj.projectmanage.ProjectManagement.apis;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/login")
public class LoginApi {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String login(String userCreds) {
		return "{'error':'Invalid Credentials'}";
	}
	
}
