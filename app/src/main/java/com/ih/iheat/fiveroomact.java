package com.ih.iheat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.graphics.Matrix;
import android.widget.ImageView.ScaleType;

public class fiveroomact extends Activity {
	ImageView iv[]=new ImageView[5];
	ImageView setup;
	String email;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fiveroomlayout);

		iv[0]=(ImageView)findViewById(R.id.imageView1);
		iv[1]=(ImageView)findViewById(R.id.imageView2);
		iv[2]=(ImageView)findViewById(R.id.imageView3);
		iv[3]=(ImageView)findViewById(R.id.imageView4);
		iv[4]=(ImageView)findViewById(R.id.imageView5);

        email=getIntent().getStringExtra("email");

		setup=(ImageView)findViewById(R.id.imageViewSetup);
		setup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(fiveroomact.this,settingact.class);
				startActivity(i);
			}
		});
		Thread th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String u="?email="+email;
				final String  result[]=Connection.GET("http://www.thermostat.ie/test/androheatstat.jsp"+u).split("%");
				fiveroomact.this.runOnUiThread(new Runnable() {
		public void run()
		{
			int i=0;
			while(i<result.length)
			{
				String r=result[i];
				if(i>= iv.length)
					break;
					if(r.equals("0"))
						iv[i].setImageResource(R.drawable.hot_small);
					else
						iv[i].setImageResource(R.drawable.hot_smaller);
			i++;
			}
		}
				});
	}
});
		th.start();
	}


}
