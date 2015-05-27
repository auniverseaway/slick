package net.zum.slick.libs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
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
		
		Resource resource = request.getResource();
		Constants constants = new Constants(resource);
		String domain = constants.getUrl();
				
		try {
			
			final URL url = new URL(domain + "/dispatcher/invalidate.cache");
			urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setRequestMethod("POST");
			urlConn.setAllowUserInteraction(false);
			urlConn.setDoOutput(true);
			urlConn.setRequestProperty("accept", "text/html");
			urlConn.setRequestProperty("CQ-Action", "Activate");
			urlConn.setRequestProperty("CQ-Handle", "/content");
			
			final int responseCode = urlConn.getResponseCode();
			logger.debug("Response Code: " + responseCode);
			
			if (responseCode == 200) {
				inStream = urlConn.getInputStream();
				data = convertStreamToString(inStream, url.getProtocol() + "://" + url.getHost());
			} else {
				data = "Status:" + responseCode;
			}
		
		} catch (final MalformedURLException e) {
			logger.error("URL not valid.", e);
		} catch (final IOException e) {
			logger.error("IO Exception: " + e.getMessage(), e);
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
		
		response.setContentType("text/html");
		response.getWriter().write(data);
	}
	
	/**
	 * Convert stream to string.
	 * @param inputStream the input stream
	 * @param domain the domain
	 * @return the string
	 */
	private String convertStreamToString(final InputStream inputStream, final String domain) {
		
		BufferedReader br = null;
		final StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			// Read Input
			br = new BufferedReader(new InputStreamReader(inputStream));
			// Read Line
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (final IOException e) {
			logger.error("IOException: " + e.getMessage(), e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (final IOException e) {
					logger.error("IOException while closing stream: " + e.getMessage(), e);
				}
			}
		}
		return sb.toString();
	}
}

