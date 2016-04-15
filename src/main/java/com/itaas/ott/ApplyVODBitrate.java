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

public class ApplyVODBitrate {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		

		
		HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/ApplyVODBitrate");
		
		HttpResponse httpResponse2 = null;
		DefaultHttpClient httpClient2 = new DefaultHttpClient();
		httpPost1.setHeader("Cookie", "JSESSIONID="+args[0]);
		JSONObject object = new JSONObject();
		object.put("userToken", args[0]);
		object.put("subscriberId", "ott19");
		object.put("maxBitrate", 5);	
		object.put("minBitrate",0 );	
		object.put("contentId", "1783");
     String content = object.toString();
 	System.out.println(" ApplyVODBitrate Request ::"+content);	
		httpPost1.setEntity(new StringEntity(content, "UTF-8"));
		httpResponse2 = httpClient2.execute(httpPost1);
		
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(httpResponse2.getEntity().getContent()));
		String inputLine2;
		StringBuffer response2 = new StringBuffer();
    
		while ((inputLine2 = reader2.readLine()) != null) {
		
			response2.append(inputLine2);
		}
		reader2.close();
		System.out.println(" ApplyVODBitrate Response ::"+ response2.toString());
		
	
	
	}
}
