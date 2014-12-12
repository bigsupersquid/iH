package com.ih.iheat;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class loginsuccessact extends Activity implements OnClickListener{
	TextView tv,search;
	String email,pass,tabid;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginsuccesslayout);
		Intent i=getIntent();
		String name=i.getStringExtra("name");
		email=i.getStringExtra("email");
		pass=i.getStringExtra("pass");
		tabid=i.getStringExtra("tabid");
		tv=(TextView)findViewById(R.id.textWelcome);
		search=(TextView)findViewById(R.id.textViewSearch);
		search.setOnClickListener(this);
		tv.setText("Welcome "+name);
		db=openOrCreateDatabase("iheatdb",MODE_PRIVATE,null);
		db.execSQL("create table if not exists userinfo(email varchar(100),pass varchar(100),tabid varchar(100))");
		
			db.execSQL("insert into userinfo values('"+email+"','"+pass+"','"+tabid+"')");
		db.close();
		
		
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent i=new Intent(this,viewdevicesact.class);
		i.putExtra("email",email);
		startActivity(i);
		
	}

}
