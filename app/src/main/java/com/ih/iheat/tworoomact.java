package com.ih.iheat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class tworoomact extends Activity implements OnClickListener{
	View iv[]=new View[2];
	ImageView degree[]=new ImageView[2];
	TextView onoff[]=new TextView[2];
	TextView temp[]=new TextView[2];
	TextView cent[]=new TextView[2];
	TextView roomtemp[]=new TextView[2];
	ImageView setup;
	String email;
	String devarray[];
	String status[]=new String[2];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tworoomlayout);
		email=getIntent().getStringExtra("email");
		
		iv[0]=(View)findViewById(R.id.view1);
		degree[0]=(ImageView)findViewById(R.id.imageViewDegree1);
		onoff[0]=(TextView)findViewById(R.id.textViewOnOff1);
		temp[0]=(TextView)findViewById(R.id.textViewTemp1);
		cent[0]=(TextView)findViewById(R.id.textViewCent1);
		roomtemp[0]=(TextView)findViewById(R.id.textViewRoomTemp1);
		
		iv[1]=(View)findViewById(R.id.view2);
		degree[1]=(ImageView)findViewById(R.id.imageViewDegree2);
		onoff[1]=(TextView)findViewById(R.id.textViewOnOff2);
		temp[1]=(TextView)findViewById(R.id.textViewTemp2);
		cent[1]=(TextView)findViewById(R.id.textViewCent2);
		roomtemp[1]=(TextView)findViewById(R.id.textViewRoomTemp2);
		for(int i=0;i<status.length;i++)
			{
			status[i]="0";
			iv[i].setOnClickListener(this);
			}
		setup=(ImageView)findViewById(R.id.imageViewSetup);
		setup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(tworoomact.this,settingact.class);
				startActivity(i);
			}
		});
		final ProgressDialog pd = new ProgressDialog(this);
		pd.setMessage("loading");
		pd.show();
		Thread th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String u="?email="+email;
				String total[]=Connection.GET("http://www.thermostat.ie/test/androheatstat.jsp"+u).split("~~~");
				final String  result[]=total[0].split("%");
				devarray=total[1].split("%");
				status=result;
				tworoomact.this.runOnUiThread(new Runnable() {
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
						//iv[i].setImageResource(R.drawable.offsmall);
					}
					else
					{
						iv[i].setBackgroundResource(R.drawable.hot_big);
					roomtemp[i].setVisibility(View.INVISIBLE);
					temp[i].setVisibility(View.INVISIBLE);
					onoff[i].setText("Heating On");
					degree[i].setVisibility(View.INVISIBLE);
					cent[i].setVisibility(View.INVISIBLE);
					}
			i++;
			}
			pd.hide();
		}
				});
	}
});
		th.start();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0==iv[0])
		{
		Intent i=new Intent(this,roomdetailact.class);
		i.putExtra("status",status[0]);
		i.putExtra("roomno","1");
		i.putExtra("email",email);
		i.putExtra("devid",devarray[0]);
		startActivity(i);
		}
		else
		{
			Intent i=new Intent(this,roomdetailact.class);
			i.putExtra("roomno","2");
			i.putExtra("status",status[1]);
			i.putExtra("email",email);
			i.putExtra("devid",devarray[1]);
			startActivity(i);	
		}
		
	}
}
