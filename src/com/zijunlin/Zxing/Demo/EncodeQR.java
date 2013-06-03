package com.zijunlin.Zxing.Demo;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;



public class EncodeQR {
	public static Bitmap Create2DCode(String str) throws WriterException
	{
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 300, 300);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		
		int pixels [] = new int[width * height];
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				if(matrix.get(x, y))
				{
					pixels[y * width + x] = 0xff000000;
				}
			}
		}
		
		Bitmap mbitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		//create bitmap by pixels
		mbitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return mbitmap;
	}
}
