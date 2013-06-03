package com.example.test;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.R;

public class TestKeystore01 extends Activity {
	HttpClient hc;
	Button btn;
	TextView tv;
	String da;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_keystore);
		hc = new DefaultHttpClient(); 
		btn = (Button)findViewById(R.id.mode);
		tv = (TextView)findViewById(R.id.UserName);
		
		btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
				initKey(); 
				String url = "https://192.168.32.100:8443"; 
				//String url = "http://183.63.86.154:9999/ISC/services/ISC?wsdl";
				Log.e("url", "before get data");
				MD5 md5=new MD5();
			    String mMD5Password=md5.StrToMD5("password");
			    Log.i("asdasdasdasdsad", "sdsadsadasdasdasd");
			    JSONObject para=PageURL.getPara("75", PageURL.METHOD_NAME_LOGIN, "0", mMD5Password);
			    Log.i("asdasdasdasdsad", "sdsadsadasdasdasd");
			    //JSONObject result = PageURL1.connect(getBaseContext().getResources().openRawResource(R.raw.client), para, PageURL.METHOD_NAME_LOGIN);
			    //da = result.toString();
				da = getData(url);
				Log.e("url", "after get data");
				tv.setText(da);
				Toast.makeText(TestKeystore01.this, da, Toast.LENGTH_LONG).show(); 
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});

	}
	
	private SSLSocketFactory getSSLSocketFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException, NotFoundException, IOException, UnrecoverableKeyException
	{
		KeyStore trustStore = KeyStore.getInstance("BKS"); 
		KeyStore clientStore = KeyStore.getInstance("BKS");
		trustStore.load(getBaseContext().getResources().openRawResource(R.raw.server_trust), "123456".toCharArray()); 
		clientStore.load(getBaseContext().getResources().openRawResource(R.raw.client), "123456".toCharArray());
		SSLSocketFactory socketFactory = new SSLSocketFactory(clientStore, "123456", trustStore);
		return socketFactory;
	}
	
	private void initKey() throws Exception { 
/*		KeyStore trustStore = KeyStore.getInstance("BKS"); 
		KeyStore clientStore = KeyStore.getInstance("BKS");
		trustStore.load(getBaseContext().getResources().openRawResource(R.raw.server_trust), "123456".toCharArray()); 
		clientStore.load(getBaseContext().getResources().openRawResource(R.raw.client), "123456".toCharArray());
		SSLSocketFactory socketFactory = new SSLSocketFactory(clientStore, "123456", trustStore); 
		//SSLSocketFactory socketFactory = new SSLSocketFactory(trustStore); 
		Scheme sch = new Scheme("https", socketFactory, 8443); 
		hc.getConnectionManager().getSchemeRegistry().register(sch); */
		
	      
		  
		  InputStream clientTruststoreIs = getResources().openRawResource(R.raw.server_trust);
	      KeyStore trustStore = null;
	      trustStore = KeyStore.getInstance("BKS");
	      trustStore.load(clientTruststoreIs, "123456".toCharArray());
	      System.out.println("Loaded server certificates: " + trustStore.size());

	      // initialize trust manager factory with the read truststore
	      TrustManagerFactory trustManagerFactory = null;
	      trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	      trustManagerFactory.init(trustStore);

	      // setup client certificate

	      // load client certificate
	      InputStream keyStoreStream = getResources().openRawResource(R.raw.client);
	      KeyStore keyStore = null;
	      keyStore = KeyStore.getInstance("BKS");
	      keyStore.load(keyStoreStream, "123456".toCharArray());

	      System.out.println("Loaded client certificates: " + keyStore.size());

	      // initialize key manager factory with the read client certificate
	      KeyManagerFactory keyManagerFactory = null;
	      keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
	      keyManagerFactory.init(keyStore, "MyPassword".toCharArray());


	      // initialize SSLSocketFactory to use the certificates
	      SSLSocketFactory socketFactory = null;
	      socketFactory = new SSLSocketFactory(SSLSocketFactory.TLS, keyStore, "123456",trustStore, null, null);
	      
			Scheme sch = new Scheme("https", socketFactory, 8443); 
			hc.getConnectionManager().getSchemeRegistry().register(sch);
		
	}
	
	private String getData(String url) throws Exception { 
		HttpUriRequest hr = new HttpGet(url); 
		HttpResponse hres = hc.execute(hr); 
		HttpEntity he = hres.getEntity(); 
		InputStream is = he.getContent(); 
		StringBuffer sb = new StringBuffer(); 
		byte[] bytes = new byte[1024]; 
		for (int len = 0; (len = is.read(bytes)) != -1;) { 
		sb.append(new String(bytes, 0, len, "gb2312")); 
		} 
		Log.e("String builder", sb.toString());
		return sb.toString(); 
		} 
	

}
