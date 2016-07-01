package com.sa.pic.rest.framework.start;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.sa.pic.rest.framework.App;


public class StartupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		App.getInstance().getContext();
		
		// find driver first
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
