package com.example.test;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.ksoap2.SoapEnvelope;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpsTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class PageURL1 {
	public static final String NAMESPACE = "http://webservice.chinagci.com";

	// WebServiceµÿ÷∑
	public static String URL_OLD = "http://10.12.18.231:8888/ISC/services/ISC?wsdl";
	public static String URL_OLD2 ="http://10.12.22.25:8080/ISC/services/ISC";
	public static String URL = "http://183.63.86.154:9999/ISC/services/ISC?wsdl";
	public static final String METHOD_NAME_LOGIN = "login";
	public static final String METHOD_NAME_GETCODEMSG = "getCodeMsg";
	public static final String METHOD_NAME_GETMEMBERINFO = "getMemberInfo";
	public static final String METHOD_NAME_APPLYTRANSFER = "applyTransfer";

	//public static String SOAP_ACTION_LOGIN = "http://webservice.chinagci.com/login";
	//public static String SOAP_ACTION_GETCODEMSG = "http://webservice.chinagci.com/getCodeMsg";
	
//	public static String para="{datetime:\""+"2013-03-06 21:13:21"+"\",user:\""+"75"+"\",app:\"andriod++app\",operate:\"login\",sign:\""+"5cc222e839acc9ec4630baa0a71d0d70425abe80a0e1d418019ae4bbea2b726b"+"\"}";
	public static int TIMEOUT = 5000;
	public static String APP="andriod++app";
	
	private static KeyStore clientStore;
	
	public static JSONObject getPara(String mUser,String operate,String mode,String mMD5Password){
		
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		String datetime = sDateFormat.format(new java.util.Date());
		
		String sign=new Hmac().signature(mMD5Password, ""+datetime+mUser+PageURL1.APP+operate+mode);
		
		JSONObject para=new JSONObject();
		try {
			para.put("datetime", datetime);
			para.put("user", mUser);
			para.put("app", PageURL1.APP);
			para.put("operate", operate);
			para.put("mode", mode);
			para.put("sign", sign);
			
			return para;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject getPara(String mUser,String operate,String mode,String mMD5Password,String params){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		String datetime = sDateFormat.format(new java.util.Date());
		
		String sign=new Hmac().signature(mMD5Password, ""+datetime+mUser+PageURL1.APP+operate+mode+params);
		
		JSONObject para=new JSONObject();
		try {
			para.put("datetime", datetime);
			para.put("user", mUser);
			para.put("app", PageURL1.APP);
			para.put("operate", operate);
			para.put("mode", mode);
			para.put("params", params);
			para.put("sign", sign);
			return para;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject connect(InputStream client, JSONObject para, String METHOD_NAME){

		try {
			
			System.out.println("rpc------");
			SoapObject rpc = new SoapObject(PageURL1.NAMESPACE, METHOD_NAME);
			rpc.addProperty("parameters", para.toString());
			System.out.println("rpc" +  rpc.toString());
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.bodyOut = rpc;
			envelope.dotNet = false;
			HttpsTransportSE ht = new HttpsTransportSE(PageURL1.URL, PageURL1.TIMEOUT, METHOD_NAME, TIMEOUT);
			//((HttpsServiceConnectionSE)ht.getServiceConnection()).setSSLSocketFactory(getSSLSocketFactory(client));
			ht.debug = true;
			ht.call(null, envelope);
			SoapPrimitive sp=(SoapPrimitive)envelope.getResponse();
			System.out.println(""+sp.toString());
			JSONTokener jsonParser = new JSONTokener(sp.toString());
			JSONObject result = (JSONObject) jsonParser.nextValue();
			System.out.println(""+result.toString());
			return result;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
			return null;
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	
	private static SSLSocketFactory getSSLSocketFactory(InputStream client) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException, IOException {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        clientStore = KeyStore.getInstance("BKS");
        clientStore.load(client, "123456".toCharArray());
        tmf.init(clientStore);
        SSLContext context = SSLContext.getInstance("SSL");
        context.init(null, tmf.getTrustManagers(), null);
        return context.getSocketFactory();
	}
	
	public static JSONObject action(Map<String ,String> para){ // getPara + connect
		return null;
	}
}
