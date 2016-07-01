package com.sa.pic.rest.resource;

public interface ApiDefinition {

	public static final String FacebookSourceType = "1";
	public static final String InstagramSourceType = "2";
	public static final String WeiboSourceType = "3";
	public static final String NewsAndForumSolrData = "4";
	public static final String TwitterSourceType = "5";

	static final String DBSToken = "QbOeYjdX5sq54vzdjGHXeEu7datMprCZADd1BOPZBv4n0kpLvupmQzwwRLmG2dSjoihZCxrKqQrv6";
	static final String CNToken = "QbOeYjdX5sq54vzdjGHXeEu7datMprCZADd1BOPZBv4n0kpLvupmQzwwRLmG2ASVASQZCaCAR65RA";
	static final String SeanToken = "QbOeYjdX5sq54vzdjGHXeEu7datMprCZADd1BOPZBv4n0kpLvupmQzwwRLmG2ASVASQZCaCAR65ST";
	
	static final String PicPath="/var/lib/tomcat6/webapps/picdata/";
	static final String PicUrl="http://sgdaemon.cloudapp.net/picdata/";
	
	static final String DB_url = "jdbc:mysql://localhost/secretgarden_dev";
	static final String DB_user = "root";
	static final String DB_pwd = "root";
	
}