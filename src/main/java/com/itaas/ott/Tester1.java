package com.itaas.ott;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class Tester1 {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/PlayBillList");
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/VodList");
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/CategoryList");
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/ContentDetail");
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/ContentLocked");
		HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/Authorization");
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/AuthorizeAndPlay");
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/SitcomList");
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/PlayBillContextEx");
		//HttpPost httpPost1 = new HttpPost("http://221.226.2.232:17200/EPG/JSON/Subscribe");
		//{"productid":"","continuetype":1,"businesstype":2}
		HttpResponse httpResponse2 = null;
		DefaultHttpClient httpClient2 = new DefaultHttpClient();
		httpPost1.setHeader("Cookie", "JSESSIONID="+args[0]);
		JSONObject object = new JSONObject();
		//object.put("productid","82");
		//object.put("continuetype", 0);
		//object.put("contentid", "524");
		object.put("businesstype",2);
		object.put("playtype",2);
		/*object.put("channelid", "523");
		object.put("date", "20160316");
		object.put("preNumber", "1");
		object.put("nextNumber", "5");*/
		//object.put("vodid", "184");
		//object.put("vod", 184);
		//object.put("channelid", 121);
		//object.put("type", 2);
		//object.put("categoryid","series");
		//object.put("type", "VOD");
	//	object.put("count", 10);
		//object.put("offset", 0);
		//object.put("begintime", "20160311000000");
		//object.put("endtime", "20160318235959");
		/*object.put("contenttype", "VOD");
		object.put("contentid", "184");
		object.put("islocked", 1);
		object.put("iscontrolled", 0);*/
	/*	object.put("contentId", "1783");
		object.put("contentType", "VOD");
		object.put("businessType",1);
		object.put("mediaId","000000000000000006617");*/
		object.put("contenttype", "VIDEO_CHANNEL");
		object.put("contentid", "523");
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
		System.out.println(" Response"+ response2.toString());
		
		/*JSONObject obj = new JSONObject(response2.toString());
		if(obj.getInt("counttotal") > 0){
			JSONArray array = obj.getJSONArray("playbilllist");
			
			for(int i=0;i<array.length();i++){	
				
				JSONObject json= (JSONObject) array.get(i);		
				//PROGRAM TABLE DATA
				String channelId = json.getString("channelid");
				String programId = json.getString("id");
				String programName = json.getString("name");
				String description = json.getString("introduce");
				
				//PROGRAM SEQUENCE TABLE DATA
				String startTime = json.getString("starttime");
				String endTime  = json.getString("endtime");
				 int duration = getTimeDifference(startTime,endTime); // 
				 
				 String programDate = changeDateTimeFormat(startTime,"yyyy-mm-dd","mmddyyyy");
				 String programTime = changeDateTimeFormat(startTime,"yyyy-mm-dd hh:mm:ss","hh:mm");
				 
				//System.out.println("programId ="+ programId +"  programName :" +programName +"  description :"+description);
				
			}
		}else{
			
			System.out.println("There is no Live TV Programs.");
		}*/
		
	}

	
	public static int getTimeDifference(String time1, String time2) {
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		try {
			Date t1 = parser.parse(time1);
			Date t2 = parser.parse(time2);
			return (t2.getHours() * 60 + t2.getMinutes())
					- (t1.getHours() * 60 + t1.getMinutes());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return -99999999;
	}
	
	public static String changeDateTimeFormat(String dateTime,
			String currentFormat, String desiredFormat) {
		try {
			return new SimpleDateFormat(desiredFormat)
					.format(new SimpleDateFormat(currentFormat).parse(dateTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
