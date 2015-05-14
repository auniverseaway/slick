package net.zum.slick.auth;

import java.util.Iterator;

import javax.jcr.query.Query;

import net.zum.slick.Page;
import net.zum.slick.libs.Link;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class Header {
	private final Resource resource;
	
	public String link;
	
	private ResourceResolver resourceResolver;
	
	public Header(final Resource resource) {
		String resourcePath = "/content/slick/auth";
		resourceResolver = resource.getResourceResolver();
		this.resource = resourceResolver.getResource(resourcePath);
    }
	
	public Iterator<Page> getAdminHeader() {
		String query = "SELECT * FROM [nt:unstructured] AS s WHERE [title] IS NOT NULL and ISCHILDNODE(s,'/content/slick/auth') ORDER BY [menuOrder] ASC";
		Iterator<Resource> childs = resourceResolver.findResources(query, Query.JCR_SQL2);
		return ResourceUtil.adaptTo(childs,Page.class);
    }
	
	public String getPath() {
		return this.resource.getPath();
    }
	
	public String getLink() {
		Link link = new Link();
		return link.getUri(this.resource.getPath());
	}
}