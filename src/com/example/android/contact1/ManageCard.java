package com.example.android.contact1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.contact1.database.ContactServiceBean;
import com.example.test1.R;

public class ManageCard extends Activity {

	ListView myListView;
	private List<Map<String, Object>> mData;
	private Button deleteBtn;
	private Cursor c;
	private Set<Integer> deleteList;
	MyAdapter adapter ;
	ContactServiceBean csb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_management);
		myListView = (ListView)findViewById(R.id.listView1);
		deleteBtn = (Button)findViewById(R.id.deleteBtn);
		mData = getData();
		adapter = new MyAdapter(this);
		deleteList = new TreeSet<Integer>();
		
		myListView.setAdapter(adapter);
		csb = new ContactServiceBean(this);
		deleteBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				List<ContactDetails> list = new ArrayList<ContactDetails>();
				ContactDetails cd;
				for(int deletePosition : deleteList)
				{
					cd = new ContactDetails();
					Log.i("deltete position", ""+(deletePosition));
					c.moveToPosition(deletePosition);
					//c.move(deletePosition);
					cd.setName(c.getString(c.getColumnIndex("name")));
					cd.setMobileNum(c.getString(c.getColumnIndex("mobileNum")));
					list.add(cd);
				}
				csb.deleteOtherData(list);
				deleteList.clear();  //清空删除列表
				mData = getData();   //重新获得数据
				adapter.notifyDataSetChanged(); //通知列表更新
			}});
	}

	private List<Map<String, Object>> getData()
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;
		ContactServiceBean csb = new ContactServiceBean(this);
		c = csb.getAllOthersData();
		while(c.moveToNext())
		{
		map = new HashMap<String, Object>();
		map.put("userName", c.getString(c.getColumnIndex("name"))+ " " + c.getString(c.getColumnIndex("mobileNum")));
		list.add(map);
		}
		return list;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manage_card, menu);
		return true;
	}
	
	public final class ViewHolder
	{
		public TextView userName;
		public CheckBox checkbox;
	}
	
	/*
	 * ListView Adapter
	 */
	public class MyAdapter extends BaseAdapter{

		private LayoutInflater mInflater;
		
		public MyAdapter(Context context)
		{
			this.mInflater = LayoutInflater.from(context);
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;

			if(convertView == null)
			{
				holder = new ViewHolder();
				convertView = mInflater.inflate(R.layout.card_content, null);
				holder.userName = (TextView)convertView.findViewById(R.id.userName);
				holder.checkbox = (CheckBox)convertView.findViewById(R.id.cardContentCheckBox);
				Log.i("test", "" + (holder.checkbox == null));
				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder)convertView.getTag();
			}
			Log.i("test holder.checkbox true or not", "" + (holder.checkbox == null));
			holder.userName.setText((String)mData.get(position).get("userName"));
			
			holder.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener(){

				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					Log.e(""+position,""+arg1);
					if(arg1)
					{
						deleteList.add(position);
					}
					else
					{
						deleteList.remove(position);
					}
				}
				
			});
			return convertView;
		}

	}

}
