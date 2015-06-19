package net.zum.slick;

import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables=SlingHttpServletRequest.class)
public class User {
	
	@Inject
	private String userId;
	
	@Inject @Optional
	private SlingHttpServletRequest request;
	
	@Inject @Optional
	private Resource resource;
	
	public Resource user;
	
	public String getUserId(){
		return userId;
	}
	
	public Resource getUser()
	{
		String resourcePath = "/system/userManager/user/" + userId;
		ResourceResolver resourceResolver = resource.getResourceResolver();
		return resourceResolver.getResource(resourcePath);
	}
}