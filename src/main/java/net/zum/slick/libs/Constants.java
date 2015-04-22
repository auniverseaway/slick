package net.zum.slick.libs;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class Constants
{
	private final Resource resource;
	
	public ValueMap properties;
	
	public Constants(final Resource resource) {
		String resourcePath = "/content/slick/auth/settings";
		ResourceResolver resourceResolver = resource.getResourceResolver();
		this.resource = resourceResolver.getResource(resourcePath);
    }
	
	public ValueMap getProperties()
    {
        return resource.adaptTo(ValueMap.class);
    }
	
	
}