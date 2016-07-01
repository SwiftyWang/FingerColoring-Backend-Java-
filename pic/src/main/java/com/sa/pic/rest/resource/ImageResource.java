package com.sa.pic.rest.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.restlet.data.MediaType;
import org.restlet.representation.ByteArrayRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//get add categoryid and imageid  "/%d/f_%d.png"
public class ImageResource extends ServerResource implements ApiDefinition {
	private static final Logger log = LoggerFactory
			.getLogger(ImageResource.class);

	@Get("image/png")
	public void getIcon() {
		String cate = getQueryValue("category");
		String pic = getQueryValue("image");
		log.info(cate + "," + pic);
		// read "any" type of image (in this case a png file)
		BufferedImage image;
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			if (pic.toLowerCase().contains("t_")) {
				image = ImageIO.read(new File(PicPath + "/" + cate + "/"
						+ pic + ".jpg"));
				ImageIO.write(image, "jpg", b);
			} else {
				image = ImageIO.read(new File(PicPath + "/" + cate + "/"
						+ pic + ".png"));
				ImageIO.write(image, "png", b);
			}
			byte[] your_images_bytes = b.toByteArray();
			ByteArrayRepresentation bar = new ByteArrayRepresentation(
					your_images_bytes, MediaType.IMAGE_PNG);
			getResponse().setEntity(bar);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
