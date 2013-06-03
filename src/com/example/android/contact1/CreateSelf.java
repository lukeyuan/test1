package com.example.android.contact1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.android.contact1.database.ContactServiceBean;
import com.example.test1.R;
import com.example.test1.R.id;

public class CreateSelf extends Activity {

	final int SINGLE_DIALOG_MODEL = 0x113;
	final int SINGLE_DIALOG_CARDTYPE = 0x114;
	private EditText user;
	private String sex;
	private EditText moblieNum;
	private EditText position;
	private EditText email;
	private EditText address;
	private EditText postCode;
	private Button model;
	private Button createType;
	private Button scan;
	private Button create;
	private RadioGroup radioGroup;
	
	private int modelNum;
	private String cardType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_self);
		
		user = (EditText)findViewById(R.id.userName);
		moblieNum = (EditText)findViewById(R.id.phone);
		position = (EditText)findViewById(R.id.job);
		email = (EditText)findViewById(R.id.email);
		address = (EditText)findViewById(R.id.address);
		postCode = (EditText)findViewById(R.id.postNum);
		model = (Button)findViewById(R.id.mode);
		createType = (Button)findViewById(R.id.type);
		radioGroup = (RadioGroup)findViewById(R.id.radioGroupSex);
		modelNum = 0;
		cardType = "同事";
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.man)
				{
					sex = "男";
				}
				else
				{
					sex = "女";
				}
			}	
		});
		
	}
	
	public void onClickListener(View v)//全部按钮事件
	{
		switch(v.getId())
		{
		case id.model:
			//startActivityForResult(intent);
			break;
		case id.scan:
			//Intent in = new Intent(CreateSelf.this, ManageCard.class);
			if(checkMust())
			{
				Toast.makeText(CreateSelf.this, "请填上必填(*)项", Toast.LENGTH_SHORT).show();
				break;
			}
			View root = this.getLayoutInflater().inflate(R.layout.scan_view, null);
			final PopupWindow popup = new PopupWindow(root, 280, 360);
			
			root.findViewById(R.id.showScanOK).setOnClickListener(new OnClickListener(){//取消预览

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					popup.dismiss();
				}
			});
			
			root.findViewById(R.id.createBtn).setOnClickListener(new OnClickListener(){//创建名片

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
			break;
		case id.type:
			
			break;
		}
	}
	
	public boolean checkMust()
	{
		if(user.getText().toString().equals("") || moblieNum.getText().toString().equals(""))
		{
			Log.e("", "kong");
			return true;
		}
		return false;
	}
	
	public boolean create()
	{
		ContactServiceBean csb = new ContactServiceBean(this);
		MyContactDetails cd = new MyContactDetails(user.getText().toString(), moblieNum.getText().toString(), 
				""+modelNum, sex, position.getText().toString(), email.getText().toString(), address.getText().toString(),
				postCode.getText().toString(), cardType);
		csb.saveSelf(cd);
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_self, menu);
		return true;
	}

	/**
	 * 生成模板选择对话框
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		switch(id)
		{
		case SINGLE_DIALOG_MODEL:
			Builder b = new AlertDialog.Builder(this);
			b.setTitle("选择名片模板");
			b.setSingleChoiceItems(new String[]{"无","d"}, 0, new android.content.DialogInterface.OnClickListener(){

				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					modelNum = which;
				}});
			b.setPositiveButton("确定", null);
			return b.create();
			
		case SINGLE_DIALOG_CARDTYPE:
			final String [] cardTypeSet = new String[]{"同事", "亲友"};
			b = new AlertDialog.Builder(this);
			b.setTitle("选择名片类型");
			b.setSingleChoiceItems(cardTypeSet, 0, new android.content.DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					cardType = cardTypeSet[arg1];
				}
				
			});
			break;
		}
		return null;
	}
	
	

}
