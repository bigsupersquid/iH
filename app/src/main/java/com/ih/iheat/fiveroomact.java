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
import android.widget.TextView;

public class fiveroomact extends Activity {
	ImageView iv[]=new ImageView[5];
    ImageView degree[]=new ImageView[5];
    TextView onoff[]=new TextView[5];
    TextView temp[]=new TextView[5];
    TextView cent[]=new TextView[5];
    TextView roomtemp[]=new TextView[5];
	ImageView setup;
	String email;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fiveroomlayout);

		iv[0]=(ImageView)findViewById(R.id.imageView1);
        degree[0]=(ImageView)findViewById(R.id.imageViewDegree1);
        onoff[0]=(TextView)findViewById(R.id.textViewOnOff1);
        temp[0]=(TextView)findViewById(R.id.textViewTempRoom1);
        cent[0]=(TextView)findViewById(R.id.textViewCent1);
        roomtemp[0]=(TextView)findViewById(R.id.textViewRoomTemp1);

        iv[1]=(ImageView)findViewById(R.id.imageView2);
        degree[1]=(ImageView)findViewById(R.id.imageViewDegree2);
        onoff[1]=(TextView)findViewById(R.id.textViewOnOff2);
        temp[1]=(TextView)findViewById(R.id.textViewTemp2);
        cent[1]=(TextView)findViewById(R.id.textViewCent2);
        roomtemp[1]=(TextView)findViewById(R.id.textViewRoomTemp2);

		iv[2]=(ImageView)findViewById(R.id.imageView3);
        degree[2]=(ImageView)findViewById(R.id.imageViewDegree3);
        onoff[2]=(TextView)findViewById(R.id.textViewOnOff3);
        temp[2]=(TextView)findViewById(R.id.textViewTemp3);
        cent[2]=(TextView)findViewById(R.id.textViewCent3);
        roomtemp[2]=(TextView)findViewById(R.id.textViewRoomTemp3);

		iv[3]=(ImageView)findViewById(R.id.imageView4);
        degree[3]=(ImageView)findViewById(R.id.imageViewDegree4);
        onoff[3]=(TextView)findViewById(R.id.textViewOnOff4);
        temp[3]=(TextView)findViewById(R.id.textViewTemp4);
        cent[3]=(TextView)findViewById(R.id.textViewCent4);
        roomtemp[3]=(TextView)findViewById(R.id.textViewRoomTemp4);

		iv[4]=(ImageView)findViewById(R.id.imageView5);
        degree[4]=(ImageView)findViewById(R.id.imageViewDegree5);
        onoff[4]=(TextView)findViewById(R.id.textViewOnOff5);
        temp[4]=(TextView)findViewById(R.id.textViewTemp5);
        cent[4]=(TextView)findViewById(R.id.textViewCent5);
        roomtemp[4]=(TextView)findViewById(R.id.textViewRoomTemp5);

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
					{
						//iv[i].setBackgroundResource(R.drawable.hotroom4);
					}
					else
						{
							if(i==0)
							iv[i].setBackgroundResource(R.drawable.hot_small);
							else
							iv[i].setBackgroundResource(R.drawable.hot_smaller);
//						roomtemp[i].setVisibility(View.INVISIBLE);
//						temp[i].setVisibility(View.INVISIBLE);
						onoff[i].setText("Heating On");
//						degree[i].setVisibility(View.INVISIBLE);
//						cent[i].setVisibility(View.INVISIBLE);
			i++;
			}
		}
				});
	}
});
		th.start();
	}


}
