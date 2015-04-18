package net.zum.slick.auth;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class Header {
	private final Resource resource;
	
	public Header(final Resource resource) {
		String resourcePath = "/content/slick/auth";
		ResourceResolver resourceResolver = resource.getResourceResolver();
		this.resource = resourceResolver.getResource(resourcePath);
    }
	
	public Iterable<Resource> getAdminHeader() {
		return this.resource.getChildren();		
    }
	
	public String getPath() {
		return this.resource.getPath();
    }
}