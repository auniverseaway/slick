package net.zum.slick.pub;

import java.util.Iterator;

import javax.inject.Inject;
import javax.jcr.query.Query;
import javax.script.Bindings;

import net.zum.slick.Page;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.scripting.sightly.pojo.Use;

public class List implements Use {

	private Resource resource;
	
	@Inject
	@Optional
    private String slickType;
	
	public String formattedValue;
	
	public Iterator<Page> children;
	
	@Override
    public void init(Bindings bindings) {
		this.resource = (Resource)bindings.get("resource");
		this.slickType = (String)bindings.get("slickType");
	}
	
	public Iterator<Page> getChildren() {
		ResourceResolver resourceResolver = resource.getResourceResolver();
		String query = "SELECT * FROM [nt:unstructured] AS s "
				+ "WHERE [title] IS NOT NULL and "
				+ "ISDESCENDANTNODE(s,'/content/slick/" + slickType + "') "
				+ "ORDER BY [created] DESC";
		Iterator<Resource> childs = resourceResolver.findResources(query, Query.JCR_SQL2);
		return ResourceUtil.adaptTo(childs,Page.class);
	}
}