package netdb.courses.softwarestudio.finalproject.nthuevent.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSON;

public class FacebookService {
	private static String retrieveData(String url) throws IOException {
		HttpURLConnection connection=(HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("GET");
		if(connection.getResponseCode()!=200) return null;
		BufferedReader read=new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String input;
		StringBuffer data=new StringBuffer();
		while((input=read.readLine())!=null)
			data.append(input);
		read.close();
		return data.toString();
	}
	public static String getFacebookId(String accessToken) throws IOException {
		String json=retrieveData("https://graph.facebook.com/me?fields=id&access_token="+accessToken);
		FacebookUser user=JSON.parseObject(json, FacebookUser.class);
		if(user==null) return null;
		return user.id;
	}
	public static FacebookUser getFacebookUser(String accessToken) throws IOException {
		String json=retrieveData("https://graph.facebook.com/me?access_token="+ accessToken);
		FacebookUser user=JSON.parseObject(json, FacebookUser.class);
		return user;
	}
}
