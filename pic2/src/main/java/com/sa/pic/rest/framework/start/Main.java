package com.sa.pic.rest.framework.start;

import org.restlet.Component;
import org.restlet.data.Protocol;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sa.pic.rest.framework.ExternalApiApplication;


public class Main {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Component component = new Component();
		
		// Add a new HTTP server listening on port 8182.
		component.getServers().add(Protocol.HTTP, 8182);
		
		// Attach the application.
		component.getDefaultHost().attach("/extAPI", new ExternalApiApplication());
		
	
		// Start the component.
		component.start();
	}
}