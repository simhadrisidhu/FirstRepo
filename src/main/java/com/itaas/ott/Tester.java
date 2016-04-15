package com.itaas.ott;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class Tester {
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/ChannelList");
		HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/AllChannel");
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/VodList");
		HttpResponse httpResponse2 = null;
		DefaultHttpClient httpClient2 = new DefaultHttpClient();
		httpPost1.setHeader("Cookie", "JSESSIONID="+args[0]);
		
		httpResponse2 = httpClient2.execute(httpPost1);
		
		BufferedReader reader2 = new BufferedReader(new InputStreamReader(httpResponse2.getEntity().getContent()));
		String inputLine2;
		StringBuffer response2 = new StringBuffer();

		while ((inputLine2 = reader2.readLine()) != null) {
			response2.append(inputLine2);
		}
		reader2.close();
		System.out.println(" Response"+ response2.toString());
		
		/*JSONObject object = new JSONObject(response2.toString());
		JSONArray obj = object.getJSONArray("channellist");
		System.out.println(obj.length());
		
		for(int i=0;i<obj.length();i++){		
			JSONObject json= (JSONObject) obj.get(i);			
			String channelid = json.getString("id");
			String name = json.getString("name");
			String hd = json.getString("HDCPEnable");
			System.out.println("ChannelId ="+ channelid +"  name :" +name +"  hd :"+hd);
			
		}*/
		
		
	}

}
