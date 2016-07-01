package com.sa.pic.rest.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.restlet.Request;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.engine.header.Header;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;
import org.springframework.stereotype.Service;

import com.sa.pic.rest.message.ResponseMessage;
import com.sa.pic.rest.resource.ApiDefinition;

@Service(value = "login")
public class Login extends ServerResource implements ApiDefinition {

	@Override
	protected void doInit() throws ResourceException {
		super.doInit();
	}
	
	@Get("application/json")
	public Representation Login() throws Exception {
		String token="";
//		Form form = new Form(data);
//		String token = StringUtils.trimToEmpty(form.getFirstValue("token")); // "facebook" or "qq"
//		String token = getQueryValue("token");
//		String token = request.getHeader("token");
//		Form headers = (Form) getRequestAttributes().get("org.restlet.http.headers");
//		String token = headers.getFirstValue("token");
		try {
			Series<Header> series = (Series<Header>)getRequestAttributes().get("org.restlet.http.headers");
		    token = series.getFirst("token").getValue();
			System.out.println(token);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		String sql_selectType="", name="", usericon="", location="", gender="";
		Connection conn;
		if (!(token.equals("") || token.isEmpty())) {
			try {
				conn = DriverManager.getConnection(DB_url);
				sql_selectType = "SELECT u.name,u.icon,u.gender,u.location  FROM sg_users u, sg_token t where t.token=? and u.uid=t.uid";
				PreparedStatement pstmt = conn.prepareStatement(sql_selectType);
				pstmt.setString(1,token);
				ResultSet resultSet = pstmt.executeQuery();
				if (!resultSet.next()) {
					pstmt.close();
				} else {
					name=resultSet.getString("name");
					usericon=resultSet.getString("icon");
					gender=resultSet.getString("gender");
					location=resultSet.getString("location");
					pstmt.close();
					Map<String, String> res = new HashMap<String, String>();
					res.put("usericon", usericon);
					res.put("gender", gender);
					res.put("location", location);
					res.put("name", name);
					
					ResponseMessage response = new ResponseMessage();
					response.setMessage(ResponseMessage.SUCCESS);
					response.setResult(res);
					response.setErrorCode(CODE_SUCCESS);
					Representation rep = new JacksonRepresentation<ResponseMessage>(response);	
					rep.setCharacterSet(CharacterSet.UTF_8);
					return rep;
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		ResponseMessage response = new ResponseMessage();
		response.setMessage(ResponseMessage.FAIL);
		response.setResult("");
		response.setErrorCode(ERR_LOGIN_FAIL);	
		Representation rep = new JacksonRepresentation<ResponseMessage>(response);	
		rep.setCharacterSet(CharacterSet.UTF_8);
		return rep;
	}
}