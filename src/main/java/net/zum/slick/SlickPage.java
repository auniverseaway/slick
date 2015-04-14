package net.zum.slick;

import java.util.Calendar;
import java.util.Iterator;

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
public class SlickPage
{
	private final Resource resource;
	
	@Inject
    private String title;
	
	@Inject
    private String content;
	
	@Inject @Optional @Named("jcr:created")
    private Calendar date;
	
	@Inject @Optional
    private String slickType;
	
	public String path;
	
	public ValueMap properties;
	
	public String guid;
	
	public Iterator<SlickPage> children;
	
	public SlickPage(final Resource resource) {
        this.resource = resource;
    }
	
	public String getPath() {
		return resource.getPath();
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
    
    public Iterator<SlickPage> getChildren()
    {
    	Iterator<Resource> childs = resource.getChildren().iterator();
    	return ResourceUtil.adaptTo(childs,SlickPage.class);
    }
}