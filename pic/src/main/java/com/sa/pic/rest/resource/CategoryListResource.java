package com.sa.pic.rest.resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sa.pic.rest.message.ResponseMessage;
import com.sa.pic.rest.resource.CategoryDetailResource.picPost;

//post  pageid from 0
public class CategoryListResource extends ServerResource implements
		ApiDefinition {

	List<Cate> list = new ArrayList<Cate>();

	@SuppressWarnings("unchecked")
	@Override
	protected void doInit() throws ResourceException {
		// TODO Auto-generated method stub
		super.doInit();
		ObjectMapper mapper = new ObjectMapper();
		String str;
		try {
			str = FileUtils.readFileToString(new File(PicPath+"Category.txt"));
			list = mapper.readValue(str, new TypeReference<List<Cate>>() {
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected Representation post(Representation entity)
			throws ResourceException {
		// TODO Auto-generated method stub
		ResponseMessage response = new ResponseMessage();
		Form form = new Form(entity);
		int pageid = Integer.valueOf(StringUtils.trimToEmpty(form
				.getFirstValue("pageid")));
		List<CategoryPost> posts = new ArrayList<CategoryPost>();
		int startIndex = getstartIndex(pageid);
		System.out.println(list.size());
		if (startIndex < list.size()) {
			for (int i = startIndex; i < startIndex + 20; i++) {
				if (i < list.size()) {
					posts.add(new CategoryPost(list.get(i).getC(), list.get(i)
							.getN(), 0));
				} else {
					break;
				}
			}
		}
		response.setMessage(ResponseMessage.SUCCESS);
		response.setResult(posts);
		response.setErrorCode(0);
		Representation rep = new JacksonRepresentation<ResponseMessage>(
				response);
		rep.setCharacterSet(CharacterSet.UTF_8);
		return rep;
	}

	private int getstartIndex(int pageid) {
		// TODO Auto-generated method stub
		return pageid * 20;
	}

	private static class Cate {
		private int c;
		private String n;
		private int max;

		public int getC() {
			return c;
		}

		public void setC(int c) {
			this.c = c;
		}

		public String getN() {
			return n;
		}

		public void setN(String n) {
			this.n = n;
		}

		public int getMax() {
			return max;
		}

		public void setMax(int max) {
			this.max = max;
		}

	}

	class CategoryPost {
		// category id
		private int c;
		// category name
		private String n;
		private int status;

		public CategoryPost(int c, String n, int status) {
			super();
			this.c = c;
			this.n = n;
			this.status = status;
		}

		public int getC() {
			return c;
		}

		public void setC(int c) {
			this.c = c;
		}

		public String getN() {
			return n;
		}

		public void setN(String n) {
			this.n = n;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

	}

}
