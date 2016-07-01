package com.sa.pic.rest.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.restlet.data.MediaType;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//get category=???
public class CategoryThumbResource extends ServerResource implements ApiDefinition {
	private static final Logger log = LoggerFactory.getLogger(CategoryThumbResource.class);

	@Get("image/png")
	public void getIcon() {
		String cate = getQueryValue("category");
		log.info(cate);
		// read "any" type of image (in this case a png file)
		BufferedImage image;
		try {
			image = ImageIO.read(new File(PicPath + "/" + cate + "/" + "category.png"));
			// write it to byte array in-memory (jpg format)
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			ImageIO.write(image, "png", b);
			byte[] your_images_bytes = b.toByteArray();
			ByteArrayRepresentation bar = new ByteArrayRepresentation(your_images_bytes, MediaType.IMAGE_PNG);
			getResponse().setEntity(bar);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



	}
}
