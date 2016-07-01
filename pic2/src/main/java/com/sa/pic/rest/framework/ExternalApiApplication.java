package com.sa.pic.rest.framework;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

import com.sa.pic.rest.resource.PicRestResource;
import com.sa.pic.rest.resource.PicUploadRepoResource;



public class ExternalApiApplication extends Application {
	@Override
	public synchronized Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a new instance
		Router router = new Router(getContext());
		App.getInstance().getContext();
		router.attach("/pic",PicRestResource.class);
		router.attach("/upload",PicUploadRepoResource.class);
		
		return router;
	}
}