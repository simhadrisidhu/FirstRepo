package com.itaas.ott;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import com.itaas.ott.catchUp.QueryHotProgram;
import com.itaas.ott.catchUp.QueryOrder;
import com.itaas.ott.tstv.PlayRecord;
import com.itaas.ott.tstv.SubscribeAndPlay;

public class Authenticator {

	private static final String Algorithm = "DESede/ECB/PKCS5Padding";// DESede/ECB/PKCS5Padding;DESede
	private static final String baseUrl = "http://221.226.2.232:17200/EPG/JSON/";
	private static final String DESede = "DESede";
	
	public static void main(String[] args)throws IOException,NoSuchAlgorithmException {
		
		
		String userName ="ott19";
		String password ="123123";
		
	String GET_URL = "http://221.226.2.232:17200/EPG/JSON/Login?UserId="+userName;

	CloseableHttpClient httpClient = HttpClients.createDefault();
	   HttpGet httpGet = new HttpGet(GET_URL);
	   String cookieValue="";
	   
	   CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
	   
	  /* System.out.println("GET Response Status:: "
               + httpResponse.getStatusLine().getStatusCode());*/

       BufferedReader reader = new BufferedReader(new InputStreamReader(
               httpResponse.getEntity().getContent()));
       
       Header[] headers = httpResponse.getHeaders("Set-Cookie");
       for (Header h : headers) {
       	cookieValue = h.getValue().toString();
              String cook[] = cookieValue.split(";");
              for(int i=0;i<cook.length;i++){
           	 //  System.out.println(cook[i]);
           	   if(cook[i].contains("JSESSIONID")){
           		   cookieValue = cook[i];
           		   break;
           	   }
              }
       }
     //  System.out.println(" cookieValue Login::"+cookieValue);
       String inputLine;
       StringBuffer response = new StringBuffer();

       while ((inputLine = reader.readLine()) != null) {
           response.append(inputLine);
       }
       reader.close();
       JSONObject jObject  = new JSONObject(response.toString());
       String enctytoken = jObject.getString("enctytoken");
       System.out.println("enctytoken From Login Service :"+enctytoken);
	   
	String authenticator = calculateAuthenticator(userName,password,enctytoken);
	
	System.out.println("authenticator :"+authenticator);
	
	String authUrl = baseUrl + "Authenticate";
	
	JSONObject object = new JSONObject();

	object.put("terminalid", "00:00:00:00:00:00");
	object.put("mac", "00:00:00:00:00:00");
	object.put("terminaltype", "WEBTV");
	object.put("utcEnable", 1);
	object.put("timezone", "GMT+05:30");
	object.put("userType", 1);
	object.put("authenticator", authenticator);
	object.put("userid", userName);

	String content = object.toString();
		
	DefaultHttpClient httpClient1 = new DefaultHttpClient();
	HttpPost httpPost = new HttpPost(authUrl);
	HttpResponse httpResponse1 = null;
	
	httpPost.setEntity(new StringEntity(content, "UTF-8"));
	httpPost.setHeader("Content-type", "application/json");
	httpPost.setHeader("Cookie", cookieValue);
	httpResponse1 = httpClient1.execute(httpPost);
	 Header[] headers1 = httpResponse1.getHeaders("Set-Cookie");
     for (Header h : headers) {
     	cookieValue = h.getValue().toString();
            String cook[] = cookieValue.split(";");
            for(int i=0;i<cook.length;i++){
         	  // System.out.println(cook[i]);
         	   if(cook[i].contains("JSESSIONID")){
         		   cookieValue = cook[i];
         		   break;
         	   }
            }
     }

	BufferedReader reader1 = new BufferedReader(new InputStreamReader(httpResponse1.getEntity().getContent()));
	String inputLine1;
	StringBuffer response1 = new StringBuffer();

	while ((inputLine1 = reader1.readLine()) != null) {
		response1.append(inputLine1);
	}
	reader1.close();
	 JSONObject jObject1  = new JSONObject(response1.toString());
      cookieValue = jObject1.getString("sessionid");
	//System.out.println("Authenticator Response"+ response1.toString());	
	System.out.println(" Authenticate Token::"+cookieValue);
	String arg[] = {cookieValue};
	//AllChannels.main(arg);
	//PlayBillList.main(arg, "20160410191246", "20160412191256");
	 ContentLocked.main(arg, "82");
	//CheckPassword.main(arg);
	//Authorization.main(arg, "71");
	//Play.main(arg);
	//QueryHotProgram.main(arg);
	//PlayRecord.main(arg);
	//SubscribeAndPlay.main(arg);
	//QueryProfile.main(arg);
	//QueryOrder.main(arg);
	//PlayBillList.main(arg);
	}
	
	public static String calculateAuthenticator(String user,String pwd,String encryToken)throws NoSuchAlgorithmException{
		String password = pwd;
		//var data = [_.random(99999999), o.encryToken, o.userId, o.mac, o.ip, o.mac, 'Reserved', 'OTT'].join('$'),
	      //      key = o.password;
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		
		String md5Pwd = md5Pwd(password);
		final byte[] rawKey = md5Pwd.getBytes();
		
		final byte[] keyBytes = new byte[24];
		
		for (int i = 0; i <rawKey.length; i++)
		{
		keyBytes[i] = rawKey[i];
		}
		
		for (int i = rawKey.length; i <keyBytes.length; i++)
		{
		keyBytes[i] = (byte)0;
		}
		//String mac="00:00:00:00:00:00";
		String mac="11:11:11:11:11:11";
		String ipAddr ="0.0.0.0";
		String random ="20926330";
		//String data = random+"$$"+user+"$"+mac+"$"+ipAddr+"$"+mac+"Reserved$OTT";
		String data ="20926330$"+encryToken+"$"+user+"$"+user+"$"+ipAddr+"$" +mac+"$Reserved$CTC";
		byte[] encoded = null;
		
		try
		{
		encoded = encrypt(keyBytes, data.getBytes());
		}
		catch (Exception e)
		{
		e.printStackTrace();
		}
		
		 String result = byte2hex(encoded);
		return result;
	}
	
private static String md5Pwd(String pwd)throws NoSuchAlgorithmException{
		
		String plainPwd =pwd;
		byte[] id = plainPwd.getBytes();

		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(id);
		md.update("99991231".getBytes());  			// "99991231" mentioned in XML-API DOC

		byte[] buffer = md.digest();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i <buffer.length; i++) {
		sb.append(Integer.toHexString((int) buffer[i] & 0xff));
		}
		String md5Pwd = sb.substring(0, 8); 		// only use first 8 characters
		
		return md5Pwd;
	}

	public static byte[] encrypt(byte[] keybyte, byte[] src)
			throws NoSuchAlgorithmException, NoSuchPaddingException, Exception {
		SecretKey deskey = new SecretKeySpec(keybyte, DESede);
		Cipher c1 = Cipher.getInstance(Algorithm);
		c1.init(Cipher.ENCRYPT_MODE, deskey);
		return c1.doFinal(src);
	
	}
	
	
	public static String byte2hex(byte[] b) {
		StringBuffer hs = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs.append("0").append(stmp);
			else
				hs.append(stmp);
		}
		return hs.toString().toUpperCase(Locale.getDefault());
	}
}

