package com.example.test;



import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsServiceConnectionSE;
import org.ksoap2.transport.HttpsTransportSE;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test1.R;

public class TestKeystore extends Activity {
	HttpClient hc;
	Button btn;
	TextView tv;
	String da;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_keystore);
		hc = new DefaultHttpClient(); 
		btn = (Button)findViewById(R.id.mode);
		tv = (TextView)findViewById(R.id.UserName);
		context = getBaseContext();
		FakeX509TrustManager.allowAllSSL(getBaseContext().getResources().openRawResource(R.raw.server_trust),
        		getBaseContext().getResources().openRawResource(R.raw.client)) ; // solution: javax.net.ssl.SSLException: Not trusted server certificate 
		btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				new Thread(new getMsg()).start();
			}
		});

	}
	
	private SSLSocketFactory getSSLSocketFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException, CertificateException, NotFoundException, IOException {
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore trustStore = KeyStore.getInstance("BKS");
        trustStore.load(getBaseContext().getResources().openRawResource(R.raw.server_trust_test), "123456".toCharArray());
        tmf.init(trustStore);
        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);
        return context.getSocketFactory();
	}
	
	protected org.apache.http.conn.ssl.SSLSocketFactory createAdditionalCertsSSLSocketFactory() {
	    try {
	        final KeyStore ks = KeyStore.getInstance("BKS");

	        // the bks file we generated above
	        final InputStream in = context.getResources().openRawResource(R.raw.server_trust_test);  
	        try {
	            // don't forget to put the password used above in strings.xml/mystore_password
	            ks.load(in, "123456".toCharArray());
	        } finally {
	            in.close();
	        }

	        return new AdditionalKeyStoresSSLSocketFactory(ks);

	    } catch( Exception e ) {
	        throw new RuntimeException(e);
	    }
	}

	final Handler handler =new Handler(){
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			}
	};
	
	class getMsg implements Runnable
	{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try
			{

			String url= "https://192.168.32.100:8443/jaxwsServer/HelloJaxwsPort?wsdl";
			String url2="http://192.168.32.100:8080/jaxwsServer/HelloJaxwsPort?wsdl";
			System.out.println("rpc------");
			SoapObject rpc = new SoapObject("http://jaxws.hmw.com/", "sayHello");
			rpc.addProperty("userName", 1);
			System.out.println("rpc" +  rpc.toString());
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.bodyOut = rpc;
			envelope.dotNet = false;
			
			
			//HttpsTransportSE ht = new HttpsTransportSE("192.168.32.100", 8443, "/jaxwsServer/HelloJaxwsPort", 10000);
			/*
			//TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
          // 				TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            //KeyStore trustStore = KeyStore.getInstance("BKS"); 
            //trustStore.load(getBaseContext().getResources().openRawResource(R.raw.server_trust_test), "123456".toCharArray()); 
            
            //tmf.init(trustStore);
            //SSLContext context = SSLContext.getInstance("TLS");
            //context.init(null, tmf.getTrustManagers(), null);

//			(ht.getServiceConnection())
            
            //final SchemeRegistry schemeRegistry = new SchemeRegistry();
            //schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            //schemeRegistry.register(new Scheme("https", createAdditionalCertsSSLSocketFactory(), 8443));
            //final HttpParams params = new BasicHttpParams();
            //final ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(params,schemeRegistry);
            
            ((HttpsServiceConnectionSE)ht.getServiceConnection()).setSSLSocketFactory(getSSLSocketFactory());
			ht.debug = true;
			FakeX509TrustManager.allowAllSSL(); 
			ht.call("", envelope);
			Log.e("hello", "sdasdaddad");
			*/
			
//			HttpTransportSE androidHttpTransport = new HttpTransportSE(url);
			HttpTransportSE androidHttpTransport = new HttpTransportSE(url,5000);
	         try
	            {                

	                
//	        	 ht.call("sayHello", envelope);
	            //			                androidHttpTransport.
//	                SoapObject response = (SoapObject)envelope.getResponse();
//	                System.out.println(""+response.toString());
	                androidHttpTransport.call(null, envelope);
	            }
	         catch(Exception e)
	         {
	             e.printStackTrace();
	             
	         }
			Log.e("helo", "shdandasdsahdsahda");
			SoapPrimitive sp=(SoapPrimitive)envelope.getResponse();
			System.out.println(""+sp.toString());

//			ht.getServiceConnection().disconnect();
			//Toast.makeText(TestKeystore.this, da, Toast.LENGTH_LONG).show(); 
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	

}
