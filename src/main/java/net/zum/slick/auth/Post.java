package net.zum.slick.auth;

import net.zum.slick.libs.FlushCache;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.jackrabbit.api.security.principal.PrincipalManager;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.jcr.base.util.AccessControlUtil;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.security.Privilege;
import javax.servlet.ServletException;

import java.io.IOException;
import java.security.Principal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
 
@SlingServlet(
    resourceTypes = {"slick:auth:post"},
    methods = {"GET", "POST"}
)
@Properties({
    @Property(name="service.pid", value="net.zum.slick.PostTest",propertyPrivate=false),
    @Property(name="service.description",value="Testing A Post Servlet", propertyPrivate=false),
    @Property(name="service.vendor",value="Zum, LLC", propertyPrivate=false)
})
@Model(adaptables=SlingHttpServletRequest.class)
public class Post extends SlingAllMethodsServlet
{
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(Post.class);
	
	private Resource resource;
	
	private Session session;
	
	@Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException
    {
		
    }
 
    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException
    {
    	this.resource = request.getResource();
		this.session = resource.getResourceResolver().adaptTo(Session.class);
		
		ResourceResolver resolver = this.resource.getResourceResolver();
		String name = request.getParameter(":name");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String slickType = request.getParameter("slickType");
		Calendar date = Calendar.getInstance();
		String draft = request.getParameter("draft");
		
		Resource myResource = resolver.getResource("/content/slick/" + slickType);
		Map<String,Object> properties = new HashMap<String,Object>();
		
		properties.put("jcr:primaryType", "nt:unstructured");
		properties.put("created", date);
		properties.put("sling:resourceType", "slick:pub:view");
		properties.put("title", title);
		properties.put("content", content);
		properties.put("slickType", slickType);
		
		Resource dummy = resolver.create(myResource, name, properties);
		
		if(draft != null){
			makePrivate(dummy);
			logger.info("MAKING PRIVATE");
		}
		
		resolver.commit();
		
		JSONObject jsonResponse = new JSONObject();
      
		try {
			jsonResponse.put("success", true);
			jsonResponse.put("name", name);
			jsonResponse.put("title", title);
			jsonResponse.put("content", content);
			jsonResponse.put("slickType", slickType);
			jsonResponse.put("created", date);
			jsonResponse.put("draft", draft);
			response.getWriter().write(jsonResponse.toString(2));
			
			// Flush the cache
			FlushCache fc = request.adaptTo(FlushCache.class);
			fc.doGet(request, response);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
    
    private void makePrivate(Resource privateResource) {
    	try {
    		String privatePath = privateResource.getPath();
        	final PrincipalManager principalManager = AccessControlUtil.getPrincipalManager(session);
            final Principal principal = principalManager.getPrincipal("anonymous");
			this.modifyAce(session, privatePath, principal, Privilege.JCR_READ, false);
		} catch (RepositoryException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    private void modifyAce(Session session, final String resourcePath, final Principal principal, final String privilege, final boolean granted)
    throws RepositoryException {        
        
        final String[] grantedPrivilegeNames;
        final String[] deniedPrivilegeNames;
        if ( granted ) {
            grantedPrivilegeNames = new String[] {privilege};
            deniedPrivilegeNames = null;
        } else {
            grantedPrivilegeNames = null;
            deniedPrivilegeNames = new String[] {privilege};
        }

        AccessControlUtil.replaceAccessControlEntry(session, resourcePath, principal,
                grantedPrivilegeNames,
                deniedPrivilegeNames,
                null,
                null);
        if (session.hasPendingChanges()) {
            session.save();
        }
    }
}