package com.example.test;

import java.security.MessageDigest;

public class MD5 {

	public String StrToMD5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			String str = buf.toString();
			System.out.println("result: " + buf.toString());// 32位的加密
			System.out.println("result: " + buf.toString().substring(8, 24));// 16位的加密
			return str;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MD5 md5=new MD5();
		md5.StrToMD5("password");
	}

}
