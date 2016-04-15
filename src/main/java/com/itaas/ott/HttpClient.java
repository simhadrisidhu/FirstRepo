package com.itaas.ott;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class HttpClient {
	
	private static final String USER_AGENT = "Mozilla/5.0";
	
	private static final String GUEST_INFO = "http://221.226.2.232:17200/EPG/JSON/GetGuestInfo";
	 private static final String GET_URL = "http://221.226.2.232:17200/EPG/JSON/Login?UserId=ott19";
	 //private static final String GET_URL1 ="http://221.226.2.232:17200/EPG/JSON/Authenticate?loginNam=ott19&userid=ott19&authenticator=123123&userType=0&authType=0";
	 private static final String GET_URL1 ="http://221.226.2.232:17200/EPG/JSON/Authenticate?terminalid=00:00:00:00:00:00&userid=ott19&userType=1&utcEnable=1&terminaltype=WEBTV";
	 private static final String GET_URL2 ="http://221.226.2.232:17200/EPG/JSON/ExecuteBatch";
	 
	 private static final String LOGOUT_URL="http://221.226.2.232:17200/EPG/JSON/Logout?type=0";
	 
	public static void main(String[] args) {
		
		try {
			sendGET();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(NoSuchAlgorithmException ne)
		{
			ne.printStackTrace();
		}
	}
	
	
	 private static void sendGET() throws IOException,NoSuchAlgorithmException {
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpGet httpGet = new HttpGet(GET_URL);
	        String cookieValue="";
	        //httpGet.addHeader("User-Agent", USER_AGENT);
	        httpGet.addHeader("Connection","Keep-Alive");
	        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
	 
	        System.out.println("GET Response Status:: "
	                + httpResponse.getStatusLine().getStatusCode());
	 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                httpResponse.getEntity().getContent()));
	        
	        Header[] headers = httpResponse.getHeaders("Set-Cookie");
	        for (Header h : headers) {
	        	cookieValue = h.getValue().toString();
	               String cook[] = cookieValue.split(";");
	               for(int i=0;i<cook.length;i++){
	            	   System.out.println(cook[i]);
	            	   if(cook[i].contains("JSESSIONID")){
	            		   cookieValue = cook[i];
	            		   break;
	            	   }
	               }
	        }
	 
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	 
	        while ((inputLine = reader.readLine()) != null) {
	            response.append(inputLine);
	        }
	        reader.close();
	 
	        // print result
	        System.out.println(response.toString());
	        
	        JSONObject jObject  = new JSONObject(response.toString()); // json
	        //JSONObject data = jObject.getJSONObject("data"); // get data object
	        String enctytoken = jObject.getString("enctytoken");
	        System.out.println("enctytoken :"+enctytoken);
	       HttpGet httpPost = new HttpGet(GET_URL1);
	      
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
	        httpPost.addHeader("Referer", "http://10.137.15.32:33200/EPG/JSON/Authenticate");
	        httpPost.addHeader("Accept-Language", "zh-cn");
	        httpPost.addHeader("Accept-Encoding", "gzip, deflate");
	        httpPost.addHeader("Connection","Keep-Alive");
	        httpPost.addHeader("Cache-Control", "no-cache");
	        //httpPost.addHeader("Set-Cookie", cookieValue);
	        httpPost.addHeader("Cookie", cookieValue);
	        httpPost.setHeader("Cookie", cookieValue);
	      //  httpPost.setHeader("Set-Cookie", "JSESSIONID="+ cookieValue);
	      //  httpPost.setHeader("Cookie", "JSESSIONID="+ "6C488039C7831ADAE908FDC2B381DABB");
	        httpPost.addHeader("User-Agent", USER_AGENT);
	        String authenticator ="";// DESUtil.main(enctytoken);
	     //   String content = "{"+"terminalid:"+"00:00:00:00:00:00"+","+"terminaltype:"+"WEBTV"+","+"userType:"+"1"+","+"authenticator:"+authenticator+"}";
	        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
	        params.add(new BasicNameValuePair("terminalid", "00:00:00:00:00:00"));
	        params.add(new BasicNameValuePair("terminaltype", "WEBTV"));
	        params.add(new BasicNameValuePair("utcEnable", "1"));
	        params.add(new BasicNameValuePair("userType", "1"));
	        params.add(new BasicNameValuePair("authenticator", authenticator));
	        params.add(new BasicNameValuePair("userid", "ott19"));
	        //httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
	      
	       
	        CloseableHttpResponse httpResponse1 = httpClient.execute(httpPost);
	        
	        System.out.println("POST Response Status:: "
	                + httpResponse1.getStatusLine().getStatusCode());
	        
	        BufferedReader reader1 = new BufferedReader(new InputStreamReader(
	                httpResponse1.getEntity().getContent()));
	 
	        String inputLine1;
	        StringBuffer response1 = new StringBuffer();
	 
	        while ((inputLine1 = reader1.readLine()) != null) {
	            response1.append(inputLine1);
	        }
	        reader1.close();
	 
	        // print result
	        System.out.println(response1.toString());
	        
	        httpClient.close();
	     //   System.out.println("cookieValue :"+cookieValue);
	       // sendPOST(cookieValue);
	    }

	 
	 private static void sendPOST(String cookieValue) throws IOException {
		 
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        HttpGet httpPost = new HttpGet(GET_URL2);
	       // HttpPost httpPost = new HttpPost(GET_URL1);
	        httpPost.addHeader("Accept", "*/*");
	        httpPost.addHeader("Content-Type","application/x-www-form-urlencoded");
	        httpPost.addHeader("Referer", "http://10.137.15.32:33200/EPG/JSON/Authenticate");
	        httpPost.addHeader("Accept-Language", "zh-cn");
	        httpPost.addHeader("Accept-Encoding", "gzip, deflate");
	      //  httpPost.addHeader("Content-Length","824");
	        httpPost.addHeader("Connection","Keep-Alive");
	        httpPost.addHeader("Cache-Control", "no-cache");
	        httpPost.addHeader("Cookie", cookieValue);
	        httpPost.addHeader("User-Agent", USER_AGENT);
	 
	        /*List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
	        urlParameters.add(new BasicNameValuePair("userName", "ott19"));
	 
	        HttpEntity postParams = new UrlEncodedFormEntity(urlParameters);
	        httpPost.setEntity(postParams);*/
	 
	        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
	 
	        System.out.println("POST Response Status:: "
	                + httpResponse.getStatusLine().getStatusCode());
	 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(
	                httpResponse.getEntity().getContent()));
	 
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	 
	        while ((inputLine = reader.readLine()) != null) {
	            response.append(inputLine);
	        }
	        reader.close();
	 
	        // print result
	        System.out.println(response.toString());
	        httpClient.close();
	 
	    }
}
