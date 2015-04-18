package net.zum.slick;

import java.util.Calendar;
import java.util.Iterator;

import net.zum.slick.libs.Link;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables = Resource.class)
public class Page
{
	private final Resource resource;
	
	@Inject @Optional
    private String title;
	
	@Inject @Optional
    private String content;
	
	@Inject @Optional @Named("created")
    private Calendar date;
	
	@Inject @Optional
    private String slickType;
	
	@Optional
	public String name;
	
	@Optional
	public String path;
	
	public String link;
	
	public ValueMap properties;
	
	public String guid;
	
	public Iterator<Page> children;
	
	public Page(final Resource resource) {
        this.resource = resource;
    }
	
	public String getName() {
		return resource.getName();
    }
	
	public String getPath() {
		return resource.getPath();
    }
	
	public String getLink() {
		Link link = new Link();
		return link.getUri(resource.getPath());
	}
	
	public String getGuid() throws RepositoryException {
		Node node = resource.adaptTo(Node.class);
		return node.getIdentifier();
	}
	
	public String getTitle() {
        return title;
    }
	
	public String getContent() {
        return content;
    }

    public ValueMap getProperties()
    {
        return resource.adaptTo(ValueMap.class);
    }
    
    public Calendar getDate()
    {
    	return date;
    }
    
    public String getSlickType()
    {
    	return slickType;
    }
    
    public Iterator<Page> getChildren()
    {
    	Iterator<Resource> childs = resource.getChildren().iterator();
    	return ResourceUtil.adaptTo(childs,Page.class);
    }
}