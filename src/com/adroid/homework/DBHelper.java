package com.adroid.homework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "card.db";
	private static final int DATABASE_VERSION = 1;
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXIST c_card" + 
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, userName VARCHAR, sex INTEGER, phone VARCHAR, job VARCHAR, email VARCHAR, " +
				"address VARCHAR, postNum INTEGER, model INTEGER)");
		db.execSQL("CREATE TABLE IF NOT EXIST s_card" + 
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT, userName VARCHAR, sex INTEGER, phone VARCHAR, job VARCHAR, email VARCHAR, " +
				"address VARCHAR, postNum INTEGER, model INTEGER, cardType INTEGER)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
