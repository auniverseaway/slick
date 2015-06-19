package net.zum.slick.auth.users;

import java.util.Iterator;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables=SlingHttpServletRequest.class)
public class UserList {
	
	private static final Logger logger = LoggerFactory.getLogger(UserList.class);
	
	private final SlingHttpServletRequest request;
	
	private final Resource resource;
	
	public Iterator<Resource> children;
	
	public String[] users;
	
	public UserList(SlingHttpServletRequest request) throws Exception {
        this.request = request;
        Resource currentRes = request.getResource();
        String resourcePath = "/system/userManager/user/";
		ResourceResolver resourceResolver = currentRes.getResourceResolver();
		this.resource = resourceResolver.getResource(resourcePath);
    }
	
	public Iterable<Resource> getChildren()
	{
		return resource.getChildren();
	}
	
}