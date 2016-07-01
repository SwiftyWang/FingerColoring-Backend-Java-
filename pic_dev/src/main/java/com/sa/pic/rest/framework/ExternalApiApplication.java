package com.sa.pic.rest.framework;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.restlet.security.MapVerifier;
import com.sa.pic.rest.resource.CategoryDetailResource;
import com.sa.pic.rest.resource.CategoryListResource;
import com.sa.pic.rest.resource.CategoryThumbResource;
import com.sa.pic.rest.resource.ImageResource;
import com.sa.pic.rest.resource.PicRestResource;
import com.sa.pic.rest.resource.UploadPic;
import com.sa.pic.rest.api.Register;
import com.sa.pic.rest.api.Login;

public class ExternalApiApplication extends Application {
	@Override
	public synchronized Restlet createInboundRoot() {
		// Create a router Restlet that routes each call to a new instance
		Router router = new Router(getContext());
		App.getInstance().getContext();
		router.attach("/uploadpic",UploadPic.class);
		router.attach("/pic",PicRestResource.class);
		
		router.attach("/imageres",ImageResource.class);
		router.attach("/categorythumb",CategoryThumbResource.class);
		router.attach("/category",CategoryListResource.class);
		router.attach("/list",CategoryDetailResource.class);
		router.attach("/register",Register.class);		
		router.attach("/login",Login.class);
		return router;
	}
}
