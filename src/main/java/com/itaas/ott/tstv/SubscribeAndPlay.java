package com.itaas.ott.tstv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class SubscribeAndPlay {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
	HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/SubscribeAndPlay");
		HttpResponse httpResponse2 = null;
		DefaultHttpClient httpClient2 = new DefaultHttpClient();
		httpPost1.setHeader("Cookie", "JSESSIONID="+args[0]);
		JSONObject object = new JSONObject();
		     // object.put("productid","1000001068");
		       // object.put("productId",1000001068);
		object.put("productId",1000001068);
				object.put("continueType", 0);
			//	object.put("contentId","82");  //channel id
				object.put("contentId","99271"); //Program id
				object.put("contentType","PROGRAM");
				object.put("businessType",5);
				object.put("mediaId","000000000000000006612");
				String content = object.toString();
				
				httpPost1.setEntity(new StringEntity(content, "UTF-8"));
				httpResponse2 = httpClient2.execute(httpPost1);
				
				BufferedReader reader2 = new BufferedReader(new InputStreamReader(httpResponse2.getEntity().getContent()));
				String inputLine2;
				StringBuffer response2 = new StringBuffer();
		    
				while ((inputLine2 = reader2.readLine()) != null) {
				
					response2.append(inputLine2);
				}
				reader2.close();
				System.out.println(" SubscribeAndPlay Response"+ response2.toString());
	}

}
