package net.zum.slick;

import javax.inject.Inject;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Reference;
import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.jcr.base.util.AccessControlUtil;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;

@Model(adaptables=SlingHttpServletRequest.class)
public class SlickRequest {
	
	@Reference
	private ResourceResolverFactory resolverFactory;
	
	private SlingHttpServletRequest request;
	private Resource resource;
	
	private User user;

    public String userid;
    
    @Inject @Optional
    public String edit;
    
    @Inject @Optional
    public Page page;

	public SlickRequest(SlingHttpServletRequest request) throws Exception {
        this.request = request;
        this.resource = request.getResource();
    }
	
	public String getEdit()
	{
		return request.getParameter("edit");
	}
	
	public String getUserid() throws Exception {
		final Session session = resource.getResourceResolver().adaptTo(Session.class);
        final UserManager userManager = AccessControlUtil.getUserManager(session);
        user = (User) userManager.getAuthorizable(session.getUserID());
        return user.getID();
	}
	
	public Page getPage() throws LoginException
	{
		String ed = request.getParameter("edit"); // Get the parameter because the constructor isn't doing it fast enough.
		ResourceResolver resourceResolver = request.getResourceResolver();
		return resourceResolver.getResource(ed).adaptTo(Page.class);
	}
    
}