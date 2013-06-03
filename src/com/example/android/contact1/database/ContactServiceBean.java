package com.example.android.contact1.database;

import java.util.ArrayList;
import java.util.List;

import com.example.android.contact1.ContactDetails;
import com.example.android.contact1.MyContactDetails;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.util.Log;

public class ContactServiceBean {
	private MySqlHelper mySqlHelper;
	SQLiteDatabase sqlite;	
	
	public ContactServiceBean(Context context) {
		super();
		this.mySqlHelper = new MySqlHelper(context, "", null, 3);	
	//	sqlite = context.openOrCreateDatabase("deng.db", Context. MODE_PRIVATE, null);
	//	sqlite=SQLiteDatabase.openOrCreateDatabase("/data/data/com.example.databaseforcontact/databases/deng.db",null); 
	}
	/**
	 * ���浽���ݿ�
	 * @param contactDetails
	 */
	public  void save(ContactDetails contactDetails){
		sqlite = mySqlHelper.getWritableDatabase();
		String sql = "replace into "+MySqlHelper.tableName+" (pattenid,name,gender,mobileNum,position,email,address,postcode) values (?,?,?,?,?,?,?,?) ";
		sqlite.execSQL(sql, new String[]{contactDetails.getPattenid(),contactDetails.getName(),contactDetails.getGender(),contactDetails.getMobileNum(),contactDetails.getPosition(),contactDetails.getEmail(),contactDetails.getPostcode()});
		//sqlite.close();
	}
	/**
	 * ���ݷ�ҳ��ʾ��
	 * @param offset����ʼҳ
	 * @param maxResult ����ҳ
	 * @return
	 */
	public List<ContactDetails> getScrollDate (int offset,int maxResult){
		List<ContactDetails> persons = new ArrayList<ContactDetails>();
		SQLiteDatabase sqlite = mySqlHelper.getReadableDatabase();
		Cursor cursor = sqlite.rawQuery("select * from contactdetail order by id asc limit ?,?",
				new String[]{String.valueOf(offset),String.valueOf(maxResult)});
		while(cursor.moveToNext()){
		//	String personid = cursor.getString(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("mobileNum"));
			//persons.add(new ContactDetails(name,phone));
		}
		cursor.close();
		return persons;
	}
	/**
	 * �õ���������
	 * @return
	 */
	public long getCount(){
		SQLiteDatabase sqlite = mySqlHelper.getReadableDatabase();
		Cursor cursor = sqlite.rawQuery("select count(*) from contactdetail", null);
		cursor.moveToFirst();
		long result = cursor.getLong(0);
		cursor.close();
		return result;
	}
	/**
	 * ����cursor�ķ�ҳ��ʾ
	 * @param offset
	 * @param maxResult
	 * @return cursor
	 */
	public Cursor getCursorScrollDate(int offset, int maxResult) {
		SQLiteDatabase sqlite = mySqlHelper.getReadableDatabase();
		Cursor cursor = sqlite.rawQuery("select name as _id from contactdetail order by _id asc limit ?,?",
				new String[]{String.valueOf(offset),String.valueOf(maxResult)});
		return cursor;
	}
	/**
	 * �õ�ȫ�����ݣ�����cursor
	 * @return cursor
	 */
	public Cursor getAllCursorScrollDate() {
		SQLiteDatabase sqlite = mySqlHelper.getReadableDatabase();
		Cursor cursor = sqlite.rawQuery("select name as _id, mobileNum from contactdetail order by _id asc ",
				null);
		return cursor;
	}
	
	/**
	 * �����Լ�����Ϣ
	 * @param contactDetails
	 */
	public  void saveSelf(MyContactDetails contactDetails){
		sqlite = mySqlHelper.getWritableDatabase();
		String sql = "replace into "+MySqlHelper.myTableName+" (pattenid,name,gender,mobileNum,position,email,address,postcode,cardtype) values (?,?,?,?,?,?,?,?,?) ";
		sqlite.execSQL(sql, new String[]{contactDetails.getPattenid(),contactDetails.getName(),
				contactDetails.getGender(),contactDetails.getMobileNum(),contactDetails.getPosition(),
				contactDetails.getEmail(),contactDetails.getPostcode(),contactDetails.getCardType()});
		//sqlite.close();
	}
	
	/**
	 * ��ѯ������ϵ������
	 * @return cursor
	 */
	public Cursor getAllOthersData()
	{
		SQLiteDatabase sqlite = mySqlHelper.getReadableDatabase();
		Cursor cursor = sqlite.rawQuery("select * from " + MySqlHelper.tableName + " order by " + MySqlHelper.name, null);
		return cursor;
	}
	
	/**
	 * ��ѯ�����Լ�����Ƭ
	 * @return cursor
	 */
	public Cursor getAllSelfData()
	{
		SQLiteDatabase sqlite = mySqlHelper.getReadableDatabase();
		Cursor cursor = sqlite.rawQuery("select * from " + MySqlHelper.myTableName + " order by " + MySqlHelper.name, null);
		return cursor;
	}
	
	/**
	 * ɾ����ϵ������
	 * @param list Ҫɾ������ϵ���б�
	 * @return ɾ���ɹ�true ʧ��false
	 */
	public boolean deleteOtherData(List<ContactDetails> list)
	{
		SQLiteDatabase sqlite = mySqlHelper.getWritableDatabase();
		String sql = "";
		for(ContactDetails cd : list)
		{
			sqlite.delete(MySqlHelper.tableName, MySqlHelper.name + " = ? and " + MySqlHelper.mobileNum +" = ?",
					new String[]{cd.getName(), cd.getMobileNum()});
			Log.i("delete", cd.getName());
		}
		sqlite.close();
		return true;
	}
	
	public void closeDB()
	{
		sqlite.close();
	}
}
