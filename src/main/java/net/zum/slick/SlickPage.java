package net.zum.slick;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Named;
import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.Model;

@Model(adaptables = Resource.class)
public class SlickPage
{
	private final Resource resource;
	
	@Inject
    private String title;
	
	@Inject
    private String content;
	
	@Inject @Named("jcr:created")
    private Calendar date;
	
	public String path;
	
	public ValueMap properties;
	
	public String guid;
	
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
}