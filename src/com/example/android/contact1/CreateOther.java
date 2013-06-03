package com.example.android.contact1;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.android.contact1.database.ContactServiceBean;
import com.example.test1.R;
import com.example.test1.R.id;

public class CreateOther extends Activity {

	final int SINGLE_DIALOG_MODEL = 0x113;
	private EditText name;
	private String gender;
	private EditText mobileNum;
	private EditText position;
	private EditText email;
	private EditText address;
	private EditText postCode;
	private Button pattenID;
	private Button scan;
	private Button create;
	private RadioGroup radioGroup;
	
	private int modelNum;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_other);
		
		name = (EditText)findViewById(R.id.name);
		mobileNum = (EditText)findViewById(R.id.mobileNum);
		position = (EditText)findViewById(R.id.position);
		email = (EditText)findViewById(R.id.email);
		address = (EditText)findViewById(R.id.address);
		postCode = (EditText)findViewById(R.id.postCode);
		pattenID = (Button)findViewById(R.id.pattenID);
		radioGroup = (RadioGroup)findViewById(R.id.radioGroupSex);
		
		Intent intent = getIntent();
		Bundle data = intent.getExtras(); // ��ȡ�ɶ�ά��ɨ������ݣ����е�ʱ���Զ����
		String scanResult = null;
		if(data != null)
		{
			scanResult = data.getString("SCAN_RESULT");
		}
		if(scanResult != null)
		{
			ContactDetails cd = new ContactDetails();
			try {
				cd = TrimToCDorJSON.trimToCD(new JSONObject(scanResult));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			name.setText(cd.getName());
			mobileNum.setText(cd.getMobileNum());
			position.setText(cd.getPosition());
			email.setText(cd.getEmail());
			address.setText(cd.getAddress());
			postCode.setText(cd.getPostcode());
			gender = cd.getGender();
			if(gender.equals("��"))
			{
				radioGroup.check(R.id.man);
			}
			else
			{
				radioGroup.check(R.id.woman);
			}
		}
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.man)
				{
					gender = "��";
				}
				else
				{
					gender = "Ů";
				}
			}	
		});
		
	}
	
	public void onClickListener(View v)//ȫ����ť�¼�
	{
		
		switch(v.getId())
		{
		case id.model:
			//startActivityForResult(intent);
			Intent in = new Intent(CreateOther.this, ManageCard.class);
			startActivity(in);
			showDialog(SINGLE_DIALOG_MODEL);
			break;
		case id.scan:
			ContactDetails cd = new ContactDetails(name.getText().toString(), mobileNum.getText().toString(), 
					""+modelNum, gender, position.getText().toString(), email.getText().toString(), address.getText().toString(),
					postCode.getText().toString());
			DrawCard dc = new DrawCard(cd);
			if(checkMust())
			{
				Toast.makeText(CreateOther.this, "�����ϱ���(*)��", Toast.LENGTH_SHORT).show();
				break;
			}
			View root = this.getLayoutInflater().inflate(R.layout.scan_view, null);
			final PopupWindow popup = new PopupWindow(root, 280, 360);
			
			((ImageView)(root.findViewById(R.id.scanImageView))).setImageBitmap(dc.draw());
			root.findViewById(R.id.showScanOK).setOnClickListener(new OnClickListener(){//ȡ��Ԥ��

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popup.dismiss();
				}
			});
			
			root.findViewById(R.id.createBtn).setOnClickListener(new OnClickListener(){//������Ƭ

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					create();
					popup.dismiss();
				}
			});
			
			//startActivity(in);
			popup.showAtLocation(findViewById(R.id.scan), Gravity.CENTER, 20, 20);
			break;
		case id.create:
			create();
			break;
		}
	}
	
	public boolean checkMust()
	{
		if(name.getText().toString().equals("") || mobileNum.getText().toString().equals(""))
		{
			Log.e("", "kong");
			return true;
		}
		return false;
	}
	
	public void create()
	{
		ContactServiceBean csb = new ContactServiceBean(this);
		ContactDetails cd = new ContactDetails(name.getText().toString(), mobileNum.getText().toString(), 
				""+modelNum, gender, position.getText().toString(), email.getText().toString(), address.getText().toString(),
				postCode.getText().toString());
		csb.save(cd);
		Toast.makeText(this, ""+csb.getCount(), Toast.LENGTH_LONG).show();
		csb.closeDB();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_self, menu);
		return true;
	}

	/**
	 * ����ģ��ѡ��Ի���
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		Builder b;
		switch(id)
		{
		case SINGLE_DIALOG_MODEL:
			b = new AlertDialog.Builder(this);
			b.setTitle("ѡ����Ƭģ��");
			b.setSingleChoiceItems(new String[]{"��","d"}, 0, new android.content.DialogInterface.OnClickListener(){

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					modelNum = which;
				}});
			b.setPositiveButton("ȷ��", null);
			return b.create();
			
		}
		return null;
	}
	
	

}
