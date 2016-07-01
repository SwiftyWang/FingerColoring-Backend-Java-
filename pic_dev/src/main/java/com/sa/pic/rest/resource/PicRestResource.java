package com.sa.pic.rest.resource;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sa.pic.rest.domain.PicComment;
import com.sa.pic.rest.message.ResponseMessage;


@Service(value = "picRestResource")
public class PicRestResource extends ServerResource implements ApiDefinition {

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
	}

	@Get
	public Representation findUserByPageId() throws Exception {
		ResponseMessage response = new ResponseMessage();
		Form form = getReference().getQueryAsForm();
		String keyword = StringUtils.trimToEmpty(form.getFirstValue("keyword"));
		System.out.println(keyword);
		List<PicComment> comments = new ArrayList<PicComment>();
		comments.add(new PicComment(1l,"comment1","good"));
		comments.add(new PicComment(2l,"comment2","nice"));
		comments.add(new PicComment(3l,"comment3","great"));
		List<picPost> posts = new ArrayList<picPost>();
		for(int i =0;i<10;i++){
		picPost post = new picPost();
		post.setId((long) i);
		post.setName("author"+ i);
		post.setLikes(1000l);
		post.setComments(200l);
		post.setShares(300l);
		post.setCommentList(comments);
		posts.add(post);
		}
		response.setMessage(ResponseMessage.SUCCESS+keyword);
		response.setResult(posts);
		response.setErrorCode(0);
		Representation rep = new JacksonRepresentation<ResponseMessage>(response);
		rep.setCharacterSet(CharacterSet.UTF_8);
		return rep;

	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String testParam(PrintWriter out, @RequestParam("username") String username) {
		out.println(username);
		return username;
	}
	class picPost{
		private Long id;
		private String name;
		private String PicUrl;
		private Long likes;
		private Long comments;
		private Long shares;
		private List<PicComment> commentList;
		
		public picPost(){
			
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
