package net.zum.slick.auth.users;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

@Model(adaptables=SlingHttpServletRequest.class)
public class UserEdit {
	
	private SlingHttpServletRequest request;
	
	private Resource resource;
	
	public String userId;
	
	public Resource user;
	
	public ValueMap properties;
	
	public String lastName;
	
	public String firstName;
	
	public String email;
	
	public UserEdit(SlingHttpServletRequest request) throws Exception {
        this.request = request;
        resource = request.getResource();
        
        // Grab the user if we need to
        userId = request.getParameter("user");
        if(userId != null) {
        	user = getUser();
        	properties = getProperties();
        }
    }
	
	public Resource getUser()
	{
		String resourcePath = "/system/userManager/user/" + userId;
		ResourceResolver resourceResolver = resource.getResourceResolver();
		return resourceResolver.getResource(resourcePath);
	}
	
	public ValueMap getProperties()
	{
		return user.adaptTo(ValueMap.class);
	}
	
	public String getLastName()
	{
		return properties.get("lastName", String.class);
	}
	
	public String getFirstName()
	{
		return properties.get("firstName", String.class);
	}
	
	public String getEmail()
	{
		return properties.get("email", String.class);
	}
	
}