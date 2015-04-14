package net.zum.slick;

import javax.jcr.Session;

import org.apache.jackrabbit.api.security.user.User;
import org.apache.jackrabbit.api.security.user.UserManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.jcr.base.util.AccessControlUtil;
import org.apache.sling.models.annotations.Model;

@Model(adaptables=SlingHttpServletRequest.class)
public class SlickRequest {
	
	private SlingHttpServletRequest request;
	private Resource resource;
	
	private final User user;

    public String userid;

	public SlickRequest(SlingHttpServletRequest request) throws Exception {
        this.request = request;
        this.resource = request.getResource();
        
        final Session session = resource.getResourceResolver().adaptTo(Session.class);
        final UserManager userManager = AccessControlUtil.getUserManager(session);
        user = (User) userManager.getAuthorizable(session.getUserID());
        userid = user.getID();
    }
	
	public String getTest()
	{
		return request.getQueryString();
	}
    
}