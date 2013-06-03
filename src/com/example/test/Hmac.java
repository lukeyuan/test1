package com.example.test;


import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class Hmac {

	public String signature(String macKey, String macData) {

		// String macKey = "The HMAC key";
		// String macData ="the data string";
		System.out.println("MACDATA:" + macData);
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			// get the bytes of the hmac key and data string
			byte[] secretByte = macKey.getBytes("UTF-8");
			byte[] dataBytes = macData.getBytes("UTF-8");
			SecretKey secret = new SecretKeySpec(secretByte, "HMACSHA256");

			mac.init(secret);
			byte[] doFinal = mac.doFinal(dataBytes);
			byte[] hexB = new Hex().encode(doFinal);
			String checksum = new String(hexB);

			return checksum;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s=new Hmac().signature("5f4dcc3b5aa765d61d8327deb882cf99", "2013-02-28 21:13:2175andriod++applogin");
		System.out.println(s);
	}

}
