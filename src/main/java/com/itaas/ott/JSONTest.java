package com.itaas.ott;

import org.json.JSONArray;
import org.json.JSONObject;

public class JSONTest {
	
	public static void main(String[] args) {
		
		
		
		JSONObject object = new JSONObject();
		object.put("name", "HeartBit");
		
		JSONObject object1 = new JSONObject();
		object1.put("userid", "ott19");
		object.put("param",object1 );
		
		JSONArray array = new JSONArray();
		array.put(object);
		System.out.println(array.toString());
		
		JSONObject obj = new JSONObject();
		obj.put("requestList", array);
		System.out.println(obj.toString());
		
		
	}

}
