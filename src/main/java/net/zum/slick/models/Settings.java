package net.zum.slick.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

/**
 * Our settings model.
 */
@Model(adaptables = Resource.class)
public class Settings
{
	
	/** The resource. */
	private final Resource resource;
	
	/** The properties. */
	public ValueMap properties;
		
	/**
	 * Instantiates settings.
	 *
	 * @param resource the resource
	 */
	public Settings(final Resource resource) {
		// No matter where we are, lets grab our settings resource.
		String resourcePath = "/content/slick/auth/settings";
		ResourceResolver resourceResolver = resource.getResourceResolver();
		this.resource = resourceResolver.getResource(resourcePath);
    }
	
	/**
	 * Gets the properties.
	 *
	 * @return the properties
	 */
	public ValueMap getProperties()
	{
		return resource.adaptTo(ValueMap.class);
	}
		
}