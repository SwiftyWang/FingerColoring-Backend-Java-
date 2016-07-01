package com.sa.pic.rest.resource;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang.StringUtils;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sa.pic.rest.domain.PicComment;
import com.sa.pic.rest.message.ResponseMessage;
import com.sa.pic.rest.resource.PicRestResource.picPost;

// post categoryid
public class CategoryDetailResource extends ServerResource implements ApiDefinition {

	@SuppressWarnings("unchecked")
	@Override
	protected Representation post(Representation entity) throws ResourceException {
		// TODO Auto-generated method stub
		ResponseMessage response = new ResponseMessage();
		Form form = new Form(entity);
		String catetoryId = StringUtils.trimToEmpty(form.getFirstValue("categoryid"));
		List<picPost> posts = new ArrayList<picPost>();
		if (StringUtils.isBlank(catetoryId)) {
			return null;
		}
		System.out.println(PicPath + catetoryId);
		File dir = new File(PicPath + catetoryId);
		List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		int returnMax = getMaxValue(catetoryId);
		// current cursor number;
		int currentCursor = 0;
		// random initial
		Random random = new Random();
		// status 0
		int status0Max = returnMax;
		if (status0Max > files.size()) {
			status0Max = files.size();
		}
		while (currentCursor < status0Max) {
			String name = "f_" + currentCursor+".png";
			File f = new File(PicPath + catetoryId + "/" + name);
			if (f.exists() && !f.isDirectory()) {
				String picUrl = PicUrl + catetoryId + "/" + name;
				picPost post = new picPost();
				post.setId(String.valueOf(currentCursor));
				post.setName(name);
				post.setPicUrl(picUrl);
				post.setLikes((long) random.nextInt(1000));
				post.setComments(200l);
				post.setStatus(0);
				post.setWvHradio(0f);
				post.setShares((long) random.nextInt(300));
				posts.add(post);
			}
			// if (files.get(currentCursor).getName().contains("f_")) {
			// try {
			// String name = files.get(currentCursor).getName();
			// String picUrl = PicUrl + catetoryId + "/" + name;
			// picPost post = new picPost();
			// post.setId(paseNametoId(name));
			// post.setName(name);
			// post.setPicUrl(picUrl);
			// post.setLikes((long)random.nextInt(1000));
			// post.setComments(200l);
			// post.setStatus(0);
			// post.setWvHradio(0f);
			// post.setShares((long)random.nextInt(300));
			// posts.add(post);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			currentCursor++;
		}

		// status 1
		int status1Max = returnMax + 2;
		if (status1Max > files.size()) {
			status1Max = files.size();
		}
		while (currentCursor < status1Max) {
			String name = "f_" + currentCursor+".png";
			File f = new File(PicPath + catetoryId + "/" + name);
			if (f.exists() && !f.isDirectory()) {
				String picUrl = PicUrl + catetoryId + "/" + name;
				picPost post = new picPost();
				post.setId(String.valueOf(currentCursor));
				post.setName(name);
				post.setPicUrl(picUrl);
				post.setLikes((long) random.nextInt(1000));
				post.setComments(200l);
				post.setStatus(1);
				post.setWvHradio(0f);
				post.setShares((long) random.nextInt(300));
				posts.add(post);
			}
			// if (files.get(currentCursor).getName().contains("f_")) {
			// try {
			// String name = files.get(currentCursor).getName();
			// String picUrl = PicUrl + catetoryId + "/" + name;
			// picPost post = new picPost();
			// post.setId(paseNametoId(name));
			// post.setName(name);
			// post.setPicUrl(picUrl);
			// post.setLikes((long)random.nextInt(1000));
			// post.setComments(200l);
			// post.setStatus(1);
			// post.setWvHradio(0f);
			// post.setShares((long)random.nextInt(300));
			// posts.add(post);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			currentCursor++;
		}
		response.setMessage(ResponseMessage.SUCCESS);
		response.setResult(posts);
		response.setErrorCode(0);

		Representation rep = new JacksonRepresentation<ResponseMessage>(response);
		rep.setCharacterSet(CharacterSet.UTF_8);
		return rep;

	}

	private int getMaxValue(String catetoryId) {
		// TODO Auto-generated method stub
		try {
			if (Integer.valueOf(catetoryId) > 280) {
				return 7;
			} else if (Integer.valueOf(catetoryId) > 200) {
				return 9;
			} else if (Integer.valueOf(catetoryId) > 150) {
				return 11;
			} else if (Integer.valueOf(catetoryId) > 100) {
				return 13;
			} else {
				return 15;
			}
		} catch (Exception e) {
			return 99;
		}
	}

	private String paseNametoId(String name) {
		// TODO Auto-generated method stub
		return name.replace(".png", "").replace("f_", "");
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String testParam(PrintWriter out, @RequestParam("username") String username) {
		out.println(username);
		return username;
	}

	class picPost {
		private String pic_id;
		private String name;
		private String PicUrl;
		private Long likes;
		private Long comments;
		private Long shares;
		private int status;
		private float WvHradio;
		private List<PicComment> commentList;

		public picPost() {

		}

		public String getId() {
			return pic_id;
		}

		public void setId(String id) {
			this.pic_id = id;
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

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public float getWvHradio() {
			return WvHradio;
		}

		public void setWvHradio(float wvHradio) {
			WvHradio = wvHradio;
		}

	}
}
