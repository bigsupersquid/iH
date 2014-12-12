package com.ih.iheat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class settingact extends Activity  implements OnClickListener,OnItemSelectedListener{
	TextView tv1;
	Spinner spinner;
	ImageView rooms,home;
	Button b;
	int item=1;
	SQLiteDatabase db;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.settingslayout);
	home=(ImageView)findViewById(R.id.imageViewHome);
	home.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			Intent i=new Intent(settingact.this,homeact.class);
			startActivity(i);
			
		}
	});
	db=openOrCreateDatabase("iheatdb",MODE_PRIVATE,null);
	db.execSQL("create table if not exists userroom(rooms number(2))");
	Cursor c1=db.rawQuery("select email from userinfo",null);
	Cursor c=db.rawQuery("select * from userroom",null);
	System.out.println("Count "+c.getCount());
	if(c.getCount()==0)
		db.execSQL("insert into userroom values(1)");
	else
	{
		System.out.println("in else");
		c.moveToFirst();
		item=c.getInt(0);
		System.out.println("item  "+item);
	}
	
	spinner=(Spinner)findViewById(R.id.spinner1);
	rooms=(ImageView)findViewById(R.id.imageViewRooms);
	b=(Button)findViewById(R.id.buttonSave);
	b.setOnClickListener(this);
	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
	        R.array.rooms_array, android.R.layout.simple_spinner_item);
	// Specify the layout to use when the list of choices appears
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	// Apply the adapter to the spinner
	spinner.setAdapter(adapter);
	spinner.setOnItemSelectedListener(this);
	tv1=(TextView)findViewById(R.id.textView2);//update display setting textview
	tv1.setOnClickListener(this);
	setImage(item);
}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	super.onResume();
	setImage(item);
}
private void setImage(int item2) {
	// TODO Auto-generated method stub
	switch(item2)
	{
	case 1:
		rooms.setImageResource(R.drawable.one);
		spinner.setSelection(0);
		break;
	case 2:
		rooms.setImageResource(R.drawable.two);
		spinner.setSelection(1);
		break;
	case 3:
		rooms.setImageResource(R.drawable.three);
		spinner.setSelection(2);
		break;
	case 4:
		rooms.setImageResource(R.drawable.four);
		spinner.setSelection(3);
		break;
	case 5:
		rooms.setImageResource(R.drawable.five);
		spinner.setSelection(4);
		break;
	case 6:
		rooms.setImageResource(R.drawable.six);
		spinner.setSelection(5);
	}
	
}
@Override
public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
		long arg3) {
	// TODO Auto-generated method stub
	String itemc=arg0.getItemAtPosition(arg2).toString();
	if(itemc.equals("One")){
		rooms.setImageResource(R.drawable.one);
		item=1;
	}
	else if(itemc.equals("Two"))
	{
		rooms.setImageResource(R.drawable.two);
		item=2;
	}
	else if(itemc.equals("Three"))
	{
		rooms.setImageResource(R.drawable.three);
	    item=3;
	}
		else if(itemc.equals("Four"))
		{
		rooms.setImageResource(R.drawable.four);
		item=4;
		}
	else if(itemc.equals("Five"))
	{
		
	rooms.setImageResource(R.drawable.five);
	item=5;
	}
	else
	{
		rooms.setImageResource(R.drawable.six);
	    item=6;
	}
	
		
	
}
@Override
public void onNothingSelected(AdapterView<?> arg0) {
	// TODO Auto-generated method stub
	
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	//Intent i=new Intent(this,roomsact.class);
	//startActivity(i);
	db.execSQL("update userroom set rooms="+item);
	Toast.makeText(this,"You added "+item+" rooms to display",Toast.LENGTH_LONG).show();
	
}
}
