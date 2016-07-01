package com.sa.pic.rest.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.engine.util.Base64;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sa.pic.rest.domain.PicComment;
import com.sa.pic.rest.message.ResponseMessage;

@Service(value = "picUploadRepoResource")
public class PicUploadRepoResource extends ServerResource implements ApiDefinition {
	private static final Logger log = LoggerFactory.getLogger(PicUploadRepoResource.class);

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
	}

	@Post
	public Representation findUserByPageId() throws Exception {
		ResponseMessage response = new ResponseMessage();
		// Form form = getReference().getQueryAsForm();
		try {
			String photo = (String) getRequestAttributes().get("photo");
			String name = (String) getRequestAttributes().get("name");
			String category = (String) getRequestAttributes().get("cate");
			log.info("photo: {}", photo);
			log.info("name: {}", name);
			log.info("category: {}", category);
			byte[] decoded = Base64.decode(photo);

			// Write a image byte array into file system
			// FileOutputStream imageOutFile = new FileOutputStream(
			// FileLocation+"/"+category+"/"+name);

			FileOutputStream imageOutFile = new FileOutputStream("/tmp/pic/a1.jpg");

			imageOutFile.write(decoded);
			imageOutFile.close();
			response.setMessage(ResponseMessage.SUCCESS);
		} catch (Exception ex) {
			response.setMessage(ResponseMessage.FAIL + ":" + ex.getMessage());
			log.error(ex.getMessage(), ex);
		}

		// response.setResult(posts);
		response.setErrorCode(0);
		Representation rep = new JacksonRepresentation<ResponseMessage>(response);
		rep.setCharacterSet(CharacterSet.UTF_8);
		return rep;

	}

	class picPost {
		private Long id;
		private String name;
		private String PicUrl;
		private Long likes;
		private Long comments;
		private Long shares;
		private List<PicComment> commentList;

		public picPost() {

		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getPicUrl() {
			return PicUrl;
		}

		public void setPicUrl(String picUrl) {
			PicUrl = picUrl;
		}

		public Long getLikes() {
			return likes;
		}

		public void setLikes(Long likes) {
			this.likes = likes;
		}

		public Long getComments() {
			return comments;
		}

		public void setComments(Long comments) {
			this.comments = comments;
		}

		public Long getShares() {
			return shares;
		}

		public void setShares(Long shares) {
			this.shares = shares;
		}

		public List<PicComment> getCommentList() {
			return commentList;
		}

		public void setCommentList(List<PicComment> commentList) {
			this.commentList = commentList;
		}

	}
}
