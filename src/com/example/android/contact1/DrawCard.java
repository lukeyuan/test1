package com.example.android.contact1;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DrawCard {
	ContactDetails cd;
	Paint paint;
	
	public DrawCard(ContactDetails cd)
	{
		this.cd = cd;
		paint = new Paint();
	}
	
	public Bitmap draw()
	{
		Bitmap bitmap = Bitmap.createBitmap(280, 180, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setColor(Color.BLACK);
		paint.setTextSize(24);
		canvas.drawText("ÐÕÃû"+cd.getName(), 0, 60, paint);
		canvas.drawText("µç»°"+cd.getMobileNum(), 0, 120, paint);
		return bitmap;
	}
}
