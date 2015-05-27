package net.zum.slick.libs;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;

import net.zum.slick.models.Settings;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.commons.json.JSONException;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple Dispatcher cache invalidation service.
 * You must be running a CQ Dispatcher for this to work.
 */

@SlingServlet(
    resourceTypes = {"slick:auth:flush"},
    methods = {"GET"}
)
@Properties({
    @Property(name="service.pid", value="net.zum.slick.FlushCache",propertyPrivate=false),
    @Property(name="service.description",value="The Slick Dispatcher cache flushing service.", propertyPrivate=false),
    @Property(name="service.vendor",value="Zum, LLC", propertyPrivate=false)
})
@Model(adaptables=SlingHttpServletRequest.class)
public class FlushCache extends SlingAllMethodsServlet {
	
	private static final long serialVersionUID = 1L;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
	public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
		
		String data = null;
		HttpURLConnection urlConn = null;
		InputStream inStream = null;
		
		// Get Settings
		Resource resource = request.getResource();
		Settings settings = resource.adaptTo(Settings.class);
		String domain = settings.getProperties().get("url",String.class);
				
		try {
			
			final URL url = new URL(domain + "/dispatcher/invalidate.cache");
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setAllowUserInteraction(false);
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty("accept", "application/json");
			urlConn.setRequestProperty("CQ-Action", "Activate");
			urlConn.setRequestProperty("CQ-Handle", "/content");
			
			//final int responseCode = urlConn.getResponseCode();
			final String responseMessage = urlConn.getResponseMessage();
						
			// Create the JSON object response
			JSONObject jsonResponse = new JSONObject();
			jsonResponse.put("flush_status", responseMessage);
		
			data = jsonResponse.toString();
			
		} catch (final MalformedURLException e) {
			logger.error("URL not valid.", e);
		} catch (final IOException e) {
			logger.error("IO Exception: " + e.getMessage(), e);
		} catch (JSONException e) {
			// We couldn't make a JSON response
			e.printStackTrace();
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (final IOException e) {
					logger.error("IOException while closing stream: " + e.getMessage(), e);
				}
			}
			if (urlConn != null) {
				urlConn.disconnect();
			}
		}
		
		response.setContentType("application/json");
		response.getWriter().write(data);
	}
}

