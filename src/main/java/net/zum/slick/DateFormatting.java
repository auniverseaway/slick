package net.zum.slick;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.Model;

@Model(adaptables=SlingHttpServletRequest.class)
public class DateFormatting {
	
	@Inject
	private String dateFormat;
	
	@Inject
	private Calendar date;
	
	public String formattedValue;
	
	@PostConstruct
	protected void init() {
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
	    formattedValue = formatter.format(date.getTime());
	}
}