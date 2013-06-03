package com.example.android.contact1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

public class TrimToCDorJSON {

	/**
	 * 把ContactDetails转换为JSON
	 * @param cd
	 * @return JSONObject
	 */
	public static JSONObject trimToJSON(ContactDetails cd)
	{
		JSONObject jsb = new JSONObject();
		try {
			jsb.put("Name", cd.getName());
			jsb.put("Gender", cd.getGender());
			jsb.put("MobileNum", cd.getMobileNum());
			jsb.put("Pattenid", cd.getPattenid());
			if(!cd.getPosition().equals("")){jsb.put("Position", cd.getPosition());}
			if(!cd.getEmail().equals("")){jsb.put("Email", cd.getEmail());};
			if(!cd.getAddress().equals("")){jsb.put("Address", cd.getAddress());}
			if(!cd.getPostcode().equals("")){jsb.put("PostCode", cd.getPostcode());}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsb;
	}
	
	/**
	 * 把JSON格式转换为ContactDetails
	 * @param js
	 * @return ContactDetails
	 */
	@SuppressWarnings("rawtypes")
	public static ContactDetails trimToCD(JSONObject js)
	{
		ContactDetails cd = new ContactDetails();
		try {
			
			Iterator iter = js.keys();
			Class c = Class.forName("ContactDetails");
			while(iter.hasNext())
			{
				
					Method m = c.getMethod("set"+iter.next().toString(), String.class);//动态调用set方法将所有有值的名片字段填上
					m.invoke(cd, new Object[]{js.getString(iter.toString())});
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cd;
	}
}
