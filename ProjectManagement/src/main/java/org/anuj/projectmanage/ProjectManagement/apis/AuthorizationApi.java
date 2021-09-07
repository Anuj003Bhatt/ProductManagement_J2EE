package org.anuj.projectmanage.ProjectManagement.apis;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/**
 * 
 * @author Anuj Narayan Bhatt
 *
 * This class implements the rest resources for authorization using roles.
 *
 */
// will be called with api/rest 
@Path("/auth")
public class AuthorizationApi {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getAuth() {	
		return "false";
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getAuthPost(String data) {
		System.out.println(data);
		return data;
	}
	
	
	
}
