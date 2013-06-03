package com.adroid.homework;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManger {
	private DBHelper helper;
	private SQLiteDatabase db;
	private static final String TABLE_C = "c_card";
	private static final String TABLE_S = "s_card";
	
	public DBManger(Context context)
	{
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}
	
	/**
	 * add common card
	 * @param infos
	 */
	public void add(List<Information> infos)
	{
		db.beginTransaction();
		try{
			for(Information info : infos)
			{
				db.execSQL("INSERT INTO " + TABLE_C + " VALUES(null, ?, ? , ?, ?, ?, ?, ?, ?)", 
						new Object[]{info.userName, info.sex, info.phone, 
						info.job, info.email, info.address, info.postNum, info.model});
			}
		}
		finally
		{
			db.endTransaction();
		}
	}
	
	/**
	 * add self card
	 * @param infos
	 */
	public void add_self(List<InformationSelf> infos)
	{
		db.beginTransaction();
		try{
			for(InformationSelf info : infos)
			{
				db.execSQL("INSERT INTO " + TABLE_C + " VALUES(null, ?, ? , ?, ?, ?, ?, ?, ?, ?)", 
						new Object[]{info.userName, info.sex, info.phone, 
						info.job, info.email, info.address, info.postNum, info.model, info.cardType});
			}
		}
		finally
		{
			db.endTransaction();
		}
	}
	
	/**
	 * delete common card info 
	 * @param info
	 */
	public void deleteCard(Information info)
	{
		db.delete(TABLE_C, "userName = ? and phone = ?", new String[]{info.userName, info.phone});
	}
	
	/**
	 * delete self card info
	 * @param info
	 */
	public void deleteCard_Self(Information info)
	{
		db.delete(TABLE_S, "userName = ? and phone = ?", new String[]{info.userName, info.phone});
	}
	
	/**
	 * query all common cards
	 * @return Cursor
	 */
	public Cursor queryAllcard()
	{
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_C, null);
		return c;
	}
	
	/**
	 * query all self cards
	 * @return Cursor
	 */
	public Cursor queryAllcard_self()
	{
		Cursor c = db.rawQuery("SELECT * FROM " + TABLE_S, null);
		return c;
	}
	
	/**
	 * close database
	 */
	public void closeDB()
	{
		db.close();
	}
}
