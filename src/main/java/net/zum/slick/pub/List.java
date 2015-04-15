package net.zum.slick.pub;

import java.util.Iterator;

import javax.inject.Inject;

import net.zum.slick.SlickPage;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables = Resource.class)
public class List {

	private final Resource resource;
	
	@Inject
	@Optional
    private String slickType;
	
	public String formattedValue;
	
	public Iterator<SlickPage> children;
	
	public List(final Resource resource) {
        this.resource = resource;
    }
	
	public Iterator<SlickPage> getChildren() {
		ResourceResolver resourceResolver = resource.getResourceResolver();
		Iterator<Resource> childs = resourceResolver.findResources("/jcr:root/content/slick/" + slickType + "/element(*) order by @jcr:created descending", "xpath");
		return ResourceUtil.adaptTo(childs,SlickPage.class);
	}
}