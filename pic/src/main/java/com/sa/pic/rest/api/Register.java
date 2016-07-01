package com.sa.pic.rest.api;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.ext.jackson.JacksonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.Statement;
import com.sa.pic.rest.message.ResponseMessage;
import com.sa.pic.rest.resource.ApiDefinition;

//@Service(value = "register")
public class Register extends ServerResource implements ApiDefinition {
	// public static void main(String[] args) {

//	@Override
//	protected void doInit() throws ResourceException {
//		super.doInit();
//	}

	// register for QQ or Facebook
	@SuppressWarnings("unchecked")
	@Override
	public Representation post(Representation userData)  throws ResourceException {
		String token="";
		// Parse the given representation and retrieve data
		Form form = new Form(userData);
		String type = StringUtils.trimToEmpty(form.getFirstValue("type")); // "facebook" or "qq"
		String uid = StringUtils.trimToEmpty(form.getFirstValue("uid")); 
		String usericon = StringUtils.trimToEmpty(form.getFirstValue("usericon"));
		String gender = StringUtils.trimToEmpty(form.getFirstValue("gender"));
		String location = StringUtils.trimToEmpty(form.getFirstValue("location"));
		String name = StringUtils.trimToEmpty(form.getFirstValue("name"));
		// try to add user and get token 
		String time = StringUtils.trimToEmpty(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
		int userid=0;
		try {
			userid = addUser(type, uid, usericon, gender, location, name, time);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (userid!=0)
			try {
				token=getToken(userid, uid,time);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		Map<String, String> res = new HashMap<String, String>();
//		res.put("token", "token");
		// List<String[]> list = new ArrayList<String[]>();
		// list.add((new String[]{"hi","hi"}));
		// list.add((new String[]{"hey","hey"}));
		res.put("token", token);
		res.put("uid", uid);
		res.put("usericon", usericon);
		res.put("gender", gender);
		res.put("location", location);
		res.put("name", name);
		
		if (token.equals("")) {
			ResponseMessage response = new ResponseMessage();
			response.setMessage(ResponseMessage.FAIL);
			response.setResult("Cannot register!");
			response.setErrorCode(1);
			Representation rep = new JacksonRepresentation<ResponseMessage>(response);
			rep.setCharacterSet(CharacterSet.UTF_8);
			return rep;
		} else {
			ResponseMessage response = new ResponseMessage();
			response.setMessage(ResponseMessage.SUCCESS);
			response.setResult(res);
			response.setErrorCode(0);
			Representation rep = new JacksonRepresentation<ResponseMessage>(response);
			rep.setCharacterSet(CharacterSet.UTF_8);
			return rep;
		}
	}
	
	public static int addUser(String type, String uid, String usericon, String gender, String location, String name, String time)
			throws SQLException {
		String sql_selectType="", sql_insertUser="", sql_insertType="", sql_insertToken="", sql_updateUser="";
		int userid = 0;
		Connection conn = DriverManager.getConnection(DB_url, DB_user, DB_pwd);
		// get exist uid from qq / facebook id
		if (type.equals("facebook")) {
			sql_selectType = "SELECT uid FROM sg_facebook WHERE fuid=?";
		} else if (type.equals("qq")) {
			sql_selectType = "SELECT uid FROM sg_qq WHERE quid=?";
		}
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql_selectType);
			pstmt.setString(1,uid);
			ResultSet resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
			userid=resultSet.getInt("uid");
			}
			pstmt.close();
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
//	insert user by qq / facebook id
		if (userid==0) {
			sql_insertUser = "INSERT INTO sg_users (name,icon,gender,location,status,date_added,date_modified) VALUES (?,?,?,?,?,?,?)";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql_insertUser, Statement.RETURN_GENERATED_KEYS);
				// pstmt.setString(1, type);
				pstmt.setString(1, name);
				pstmt.setString(2, usericon);
				pstmt.setString(3, gender);
				pstmt.setString(4, location);
				pstmt.setString(5, "active");
				pstmt.setString(6, time);
				pstmt.setString(7, time);
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Creating user failed, no rows affected.");
				}
	
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						userid = generatedKeys.getInt(1);
						// user.setId(generatedKeys.getLong(1));
					} else {
						throw new SQLException("Creating user failed, no ID obtained.");
					}
				}
				pstmt.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				return 0;
			}
			if (userid != 0) {
				// add into table facebook / qq
				if (type.equals("facebook")) {
					sql_insertType = "INSERT INTO sg_facebook (uid,fuid) VALUES (?,?)";
				} else if (type.equals("qq")) {
					sql_insertType = "INSERT INTO sg_qq (uid,quid) VALUES (?,?)";
				}
				try {
					PreparedStatement pstmt = conn.prepareStatement(sql_insertType);
					pstmt.setInt(1, userid);
					pstmt.setString(2, uid);
					pstmt.executeUpdate();
					pstmt.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
					return 0;
				}
				
				//		String Token = hmac-sha1( Hash(Pasword + Salt) + RequestUrl + UserName )
				// create token for this user
				try {
					String token = "";
					sql_insertToken = "INSERT INTO sg_token (uid,token) VALUES (?,?)";
					PreparedStatement pstmt = conn.prepareStatement(sql_insertToken);
					pstmt.setInt(1, userid);
					pstmt.setString(2, token);
					pstmt.executeUpdate();
					pstmt.close();
				} catch (SQLException e) {
					System.err.println(e.getMessage());
					return 0;
				}
				
			}
		} else {
// update user by qq / facebook id since uid is exist	
			sql_updateUser = "UPDATE sg_users SET name=?,icon=?,gender=?,location=?,status=?,date_modified=? WHERE uid = ?";
			try {
				PreparedStatement pstmt = conn.prepareStatement(sql_updateUser);
				// pstmt.setString(1, type);
				pstmt.setString(1, name);
				pstmt.setString(2, usericon);
				pstmt.setString(3, gender);
				pstmt.setString(4, location);
				pstmt.setString(5, "active");
				pstmt.setString(6, time);
				pstmt.setInt(7, userid);
				int affectedRows = pstmt.executeUpdate();
				if (affectedRows == 0) {
					throw new SQLException("Creating user failed, no rows affected.");
				}

				pstmt.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				return 0;
			}			
		}
		conn.close();
		return userid;
	}
	
    private String getToken (int userid, String uid, String time) throws Exception
    {
		
		// get exist uid from qq / facebook id
    	String token="";
    	Connection conn = DriverManager.getConnection(DB_url, DB_user, DB_pwd);
		String sql_selectType = "SELECT token FROM sg_token WHERE uid=?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql_selectType);
			// pstmt.setString(1, type);
			pstmt.setInt(1, userid);
			ResultSet resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				token=resultSet.getString("token");
			}
			pstmt.close();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return "";
		}	
		
		if (token.equals("") || token == null || token.isEmpty()) {
		// generate token for this uid
	    	String password = uid+time;
	    	
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        md.update(password.getBytes());
	        
	        byte byteData[] = md.digest();
	 
	        //convert the byte to hex format method 1
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
	     
	        try {
	        	token = sb.toString();
				String sql_insertToken = "INSERT INTO sg_token (uid,token) VALUES (?,?)";
				PreparedStatement pstmt = conn.prepareStatement(sql_insertToken);
				pstmt.setInt(1, userid);
				pstmt.setString(2, sb.toString());
				pstmt.executeUpdate();
				pstmt.close();
			} catch (SQLException e) {
				System.err.println(e.getMessage());
				return "";
			}	        
		}
		conn.close();
        return token;
        //convert the byte to hex format method 2
//        StringBuffer hexString = new StringBuffer();
//    	for (int i=0;i<byteData.length;i++) {
//    		String hex=Integer.toHexString(0xff & byteData[i]);
//   	     	if(hex.length()==1) hexString.append('0');
//   	     	hexString.append(hex);
//    	}
//    	System.out.println("Digest(in hex format):: " + hexString.toString());
    }
}