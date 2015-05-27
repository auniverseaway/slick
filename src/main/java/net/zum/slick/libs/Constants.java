package net.zum.slick.libs;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class Constants
{
	private final Resource resource;
	
	public ValueMap properties;
	
	@Inject
	public String url;
	
	@Inject
	public Constants(final Resource resource) {
		String resourcePath = "/content/slick/auth/settings";
		ResourceResolver resourceResolver = resource.getResourceResolver();
		this.resource = resourceResolver.getResource(resourcePath);
		properties = getProperties();
    }
	
	public ValueMap getProperties()
    {
        return resource.adaptTo(ValueMap.class);
    }
	
	public String getUrl()
	{
		return properties.get("url", String.class);
	}
	
	
	
	
}