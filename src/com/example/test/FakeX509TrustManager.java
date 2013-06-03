package com.example.test;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import com.example.test1.R;

public class FakeX509TrustManager implements X509TrustManager { 

    private static TrustManager[] trustManagers; 
    private static KeyManager[] keyManagers;
    private static final X509Certificate[] _AcceptedIssuers = new 
X509Certificate[] {}; 

    @Override 
    public void checkClientTrusted(X509Certificate[] chain, String 
authType) throws CertificateException { 
    } 

    @Override 
    public void checkServerTrusted(X509Certificate[] chain, String 
authType) throws CertificateException { 
    } 

    public boolean isClientTrusted(X509Certificate[] chain) { 
            return true; 
    } 

    public boolean isServerTrusted(X509Certificate[] chain) { 
            return true; 
    } 

    @Override 
    public X509Certificate[] getAcceptedIssuers() { 
            return _AcceptedIssuers; 
    } 

    public static void allowAllSSL(InputStream is_server, InputStream is_client) { 
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() 
{ 
                    @Override 
                    public boolean verify(String hostname, SSLSession session) { 
                            return true; 
                    } 

            }); 

            SSLContext context = null; 
            if (trustManagers == null) { 
                    trustManagers = new TrustManager[] {null}; 
            } 
            if(keyManagers == null)
            {
            	keyManagers = new KeyManager[]{null};
            }
            
            try { 
                    context = SSLContext.getInstance("TLS"); 
                    KeyStore ks_server = KeyStore.getInstance("BKS");
                    KeyStore ks_client = KeyStore.getInstance("BKS");
                    
                    ks_server.load(is_server, "123456".toCharArray());
                    ks_client.load(is_client, "123456".toCharArray());
                    
                    String algo = TrustManagerFactory.getDefaultAlgorithm(); 
                    TrustManagerFactory tmf =TrustManagerFactory.getInstance(algo);
                    KeyManagerFactory kmf = KeyManagerFactory.getInstance(algo);
                    
                    tmf.init(ks_server); 
                    kmf.init(ks_client, "123456".toCharArray());
                    
                    trustManagers = tmf.getTrustManagers();
                    keyManagers = kmf.getKeyManagers();
                    
                    context.init(keyManagers , trustManagers, new SecureRandom()); 
            } catch (NoSuchAlgorithmException e) { 
                    e.printStackTrace(); 
            } catch (KeyManagementException e) { 
                    e.printStackTrace(); 
            } catch (KeyStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CertificateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnrecoverableKeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory()); 
    } 

} 