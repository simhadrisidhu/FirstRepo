package com.itaas.ott;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class Authorization {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/Authorization");
		
		HttpResponse httpResponse2 = null;
		DefaultHttpClient httpClient2 = new DefaultHttpClient();
		httpPost1.setHeader("Cookie", "JSESSIONID=71B4414B28379F085421816B5C6365D6");
		JSONObject object = new JSONObject();
		//object.put("businesstype",2);
		object.put("businesstype",5);
		object.put("playtype",4);
		
		object.put("contenttype", "VIDEO_CHANNEL");
		object.put("contentid", "82");
		//object.put("contentid", channelId);
		String content = object.toString();
		
		System.out.println("Authorization Request :: "+ content);
		httpPost1.setEntity(new StringEntity(content, "UTF-8"));
		httpResponse2 = httpClient2.execute(httpPost1);
		
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(httpResponse2.getEntity().getContent()));
		String inputLine2;
		StringBuffer response2 = new StringBuffer();
    
		while ((inputLine2 = reader2.readLine()) != null) {
		
			response2.append(inputLine2);
		}
		reader2.close();
		System.out.println("Authorization Response :: "+ response2.toString());
	}

}
