package com.ih.iheat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class resultact extends Activity implements OnClickListener{
	TextView tv;
	ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wifiresult);
		Intent i=getIntent();
		String ssid[]=i.getStringArrayExtra("ssid");
		tv=(TextView)findViewById(R.id.textResult);
		String str="Connected to:";
		String line="                        :";
		for(int j=0;j<ssid.length;j++)
			if(j==0)
				str=str+ssid[j];
			else
			str=str+"\n"+line+ssid[j];
		tv.setText(str);
		iv=(ImageView)findViewById(R.id.imageConnect);
		iv.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i=new Intent(this,loginact.class);
		startActivity(i);
	}

}
