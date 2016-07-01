package com.sa.pic.rest.framework.start;

import java.lang.reflect.Method;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Description;

import com.sa.pic.dao.domain.BuzzPostData;
import com.sa.pic.rest.framework.ExternalApiApplication;


public class Main {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Component component = new Component();
		
		// Add a new HTTP server listening on port 8182.
		component.getServers().add(Protocol.HTTP, 8183);
		
		// Attach the application.
		component.getDefaultHost().attach("/extAPI", new ExternalApiApplication());
		
	
		// Start the component.
		component.start();
		
	}
}