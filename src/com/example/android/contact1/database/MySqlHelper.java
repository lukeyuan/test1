package com.example.android.contact1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqlHelper extends SQLiteOpenHelper {
	public static final String tableName = "contactdetail";
	public static final String myTableName = "mycontactdetail";
	public static final String databaseName = "deng.db";
	
    public static final String ID = "id";  
    public static final String pattenid = "pattenid"; 
    public static final String name = "name";  
    public static final String gender = "gender"; 
    public static final String mobileNum = "mobileNum";  
    public static final String position = "position";
    public static final String email = "email";  
    public static final String address = "address";
    public static final String postcode = "postcode";
    public static final String cardtype = "cardtype";
    
	public MySqlHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, databaseName, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS "+tableName+
				" ("
				+pattenid+" varchar(2), "
				+name+" VARCHAR, "
				+gender+" varchar(2), "
				+mobileNum+" PRIMARY KEY  varchar(11) , "
				+position+" varchar, "
				+email+" varchar(50), "
				+address+" varchar, "
				+postcode+" VARCHAR(6) )" );
		db.execSQL("CREATE TABLE IF NOT EXISTS "+myTableName+
				" ("
				+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
				+pattenid+" varchar(2), "
				+name+" VARCHAR,"
				+gender+" varchar(2), "
				+mobileNum+" varchar(11) , "
				+position+" varchar, "
				+email+" varchar(50), "
				+address+" varchar, "
				+postcode+" VARCHAR(6), " 
				+cardtype+" VARCHAR(2))" );
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 //db.execSQL("DROP TABLE IF EXISTS " + tableName);  
	     //onCreate(db);
	}

}
