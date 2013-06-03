package com.adroid.homework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.example.android.contact1.ManageCard;
import com.example.test1.R;
import com.example.test1.R.id;

public class CreateSelf extends Activity {

	private EditText user;
	private boolean sex;
	private EditText phone;
	private EditText job;
	private EditText email;
	private EditText address;
	private EditText postNum;
	private Button model;
	private Button scan;
	private Button create;
	private RadioGroup radioGroup;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_new_self);
		
		user = (EditText)findViewById(R.id.userName);
		phone = (EditText)findViewById(R.id.phone);
		job = (EditText)findViewById(R.id.job);
		email = (EditText)findViewById(R.id.email);
		address = (EditText)findViewById(R.id.address);
		postNum = (EditText)findViewById(R.id.postNum);
		model = (Button)findViewById(R.id.mode);
		radioGroup = (RadioGroup)findViewById(R.id.radioGroupSex);
		
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener(){

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == R.id.man)
				{
					sex = false;
				}
				else
				{
					sex = true;
				}
			}	
		});
	}
	
	public void onClickListener(View v)
	{
		switch(v.getId())
		{
		case id.model:
			//startActivityForResult(intent);
			break;
		case id.scan:
			Intent in = new Intent(CreateSelf.this, ManageCard.class);
			checkMust();
			startActivity(in);
			break;
		case id.create:
			break;
		}
	}
	
	public boolean checkMust()
	{
		if(user.getText().toString().equals("") || phone.getText().toString().equals(""))
		{
			Log.e("", "kong");
		}
		return false;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_self, menu);
		return true;
	}

}
