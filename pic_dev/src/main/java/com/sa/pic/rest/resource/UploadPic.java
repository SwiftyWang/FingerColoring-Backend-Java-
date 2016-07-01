package com.sa.pic.rest.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.texen.util.FileUtil;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sa.pic.rest.domain.PicComment;
import com.sa.pic.rest.message.ResponseMessage;

@Service(value = "picUploadRestResource")
public class UploadPic extends ServerResource implements ApiDefinition {
	private static final Logger log = LoggerFactory.getLogger(UploadPic.class);

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
	}

	// @Post
	// public Representation uploadPic() throws Exception {
	// ResponseMessage response = new ResponseMessage();
	// Form form = getReference().getQueryAsForm();
	// String catename =
	// StringUtils.trimToEmpty(form.getFirstValue("catename"));
	// String picname = StringUtils.trimToEmpty(form.getFirstValue("picname"));
	// String pic64 = StringUtils.trimToEmpty(form.getFirstValue("pic64"));
	// System.out.println(catename + "," + picname + "," + pic64);
	// if (checkDataNotFill(catename, picname, pic64)) {
	// response.setMessage(ResponseMessage.FAIL);
	// response.setErrorCode(-100);
	// Representation rep = new
	// JacksonRepresentation<ResponseMessage>(response);
	// rep.setCharacterSet(CharacterSet.UTF_8);
	// return rep;
	// }
	// String path = FileUtil.mkdir(PicLocation + "/" + catename + "/");
	// File file = FileUtil.file(path, picname);
	// if (file.exists()) {
	// file.createNewFile();
	// }
	// byte[] data = Base64.decode(pic64);
	// try (OutputStream stream = new FileOutputStream(file)) {
	// stream.write(data);
	// }
	// response.setMessage(ResponseMessage.SUCCESS);
	// response.setErrorCode(0);
	// Representation rep = new
	// JacksonRepresentation<ResponseMessage>(response);
	// rep.setCharacterSet(CharacterSet.UTF_8);
	// return rep;
	// }

	@Override
	protected Representation post(Representation entity) throws ResourceException {
		// TODO Auto-generated method stub
		ResponseMessage response = new ResponseMessage();
		Form form = new Form(entity);
		String pic64 = form.getFirstValue("pic64");
		String picname = form.getFirstValue("picname");
		String catename = form.getFirstValue("catename");
		log.info("photo: {}", pic64);
		log.info("name: {}", picname);
		log.info("category: {}", catename);
		try {
			byte[] decoded = decode(pic64);
			// Write a image byte array into file system
			String path = PicPath + "/" + catename + "/";
			try {
				FileUtil.mkdir(PicPath + "/" + catename + "/");
			} catch (Exception e) {
				log.info("path", e.toString());
			}
			FileOutputStream imageOutFile = new FileOutputStream(path + picname);
			// FileOutputStream imageOutFile = new
			// FileOutputStream("tmp/pic/a1.jpg");

			imageOutFile.write(decoded);
			imageOutFile.close();
			response.setMessage(ResponseMessage.SUCCESS);
			response.setErrorCode(0);
		} catch (Exception ex) {
			response.setMessage(ResponseMessage.FAIL + " : " + ex.toString());
			response.setErrorCode(-111);
			log.error(ex.getMessage(), ex);
		}
		// response.setResult(posts);
		Representation rep = new JacksonRepresentation<ResponseMessage>(response);
		rep.setCharacterSet(CharacterSet.UTF_8);
		return rep;
	}

	// @Post
	// public Representation findUserByPageId() throws Exception {
	// ResponseMessage response = new ResponseMessage();
	// // Form form = getReference().getQueryAsForm();
	//
	// String pic64 = (String) getRequestAttributes().get("pic64");
	// String picname = (String) getRequestAttributes().get("picname");
	// String catename = (String) getRequestAttributes().get("catename");
	// log.info("photo: {}", pic64);
	// log.info("name: {}", picname);
	// log.info("category: {}", catename);
	// try {
	// byte[] decoded = Base64.decode(pic64);
	//
	// // Write a image byte array into file system
	// // FileOutputStream imageOutFile = new FileOutputStream(
	// // FileLocation+"/"+category+"/"+name);
	//
	// FileOutputStream imageOutFile = new FileOutputStream("/pic2/a1.jpg");
	//
	// imageOutFile.write(decoded);
	// imageOutFile.close();
	// } catch (Exception ex) {
	// log.error(ex.getMessage(), ex);
	// }
	// response.setMessage(ResponseMessage.SUCCESS);
	// // response.setResult(posts);
	// response.setErrorCode(0);
	// Representation rep = new
	// JacksonRepresentation<ResponseMessage>(response);
	// rep.setCharacterSet(CharacterSet.UTF_8);
	// return rep;
	//
	// }

	@Get
	public Representation notAccess() throws Exception {
		ResponseMessage response = new ResponseMessage();
		response.setMessage(ResponseMessage.FAIL);
		response.setErrorCode(-103);
		Representation rep = new JacksonRepresentation<ResponseMessage>(response);
		rep.setCharacterSet(CharacterSet.UTF_8);
		return rep;
	}

	private boolean checkDataNotFill(String catename, String picname, String pic64) {
		// TODO Auto-generated method stub
		if (catename != null && !catename.isEmpty() && picname != null && !picname.isEmpty() && pic64 != null
				&& !pic64.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * 解码
	 * 
	 * @param str
	 * @return string
	 */
	public byte[] decode(String str) {
		byte[] bt = null;
		try {
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			bt = decoder.decodeBuffer(str);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bt;
	}
}
