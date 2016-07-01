package com.sa.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;
import org.restlet.data.Form;
import org.restlet.engine.util.Base64;
import org.restlet.resource.ClientResource;

public class TestRestful {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Form form = new Form();
		File file = new File("./aa.jpg");
		String photo = Base64.encode(FileUtils.readFileToByteArray(file), false);
		photo = URLEncoder.encode(photo, "utf-8");
		// photo = "aa";
		System.out.println(photo);
		String name = "test.jpg";
		String category = "test";
		form.add("photo", photo);
		form.add("name", "test.jpg");
		form.add("cate", "test");
		String url = "http://localhost:8080/pic/extAPI/upload";
		ClientResource resource = new ClientResource(url);
		resource.post(form);
		//
		// File file = new File("./aa.jpg");
		//
		// try {
		// // Reading a Image file from file system
		// FileInputStream imageInFile = new FileInputStream(file);
		//
		//
		// // Converting Image byte array into Base64 String
		// String imageDataString =
		// Base64.encode(FileUtils.readFileToByteArray(file), false);
		//
		// // Converting a Base64 String into Image byte array
		//
		// byte[] imageByteArray = Base64.decode(imageDataString);
		//
		// // Write a image byte array into file system
		// FileOutputStream imageOutFile = new FileOutputStream(
		// "./a1.jpg");
		//
		// imageOutFile.write(imageByteArray);
		//
		// imageInFile.close();
		// imageOutFile.close();
		//
		// System.out.println("Image Successfully Manipulated!");
		// } catch (FileNotFoundException e) {
		// System.out.println("Image not found" + e);
		// } catch (IOException ioe) {
		// System.out.println("Exception while reading the Image " + ioe);
		// }
	}

}
