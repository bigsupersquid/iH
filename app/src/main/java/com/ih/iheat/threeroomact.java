package com.ih.iheat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class threeroomact extends Activity implements OnClickListener{
	View iv[]=new View[3];
	ImageView degree[]=new ImageView[3];
	TextView onoff[]=new TextView[3];
	TextView temp[]=new TextView[3];
	TextView cent[]=new TextView[3];
	TextView roomtemp[]=new TextView[3];
	ImageView setup;
	String email;
	String status[]=new String[3];
	String devarray[];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.threeroomlayout);
		email=getIntent().getStringExtra("email");
		iv[0]=(View)findViewById(R.id.view1);
		degree[0]=(ImageView)findViewById(R.id.imageViewDegree1);
		onoff[0]=(TextView)findViewById(R.id.textViewOnOff1);
		temp[0]=(TextView)findViewById(R.id.textViewDegree1);
		cent[0]=(TextView)findViewById(R.id.textViewCent1);
		roomtemp[0]=(TextView)findViewById(R.id.textViewRoomTemp1);
		iv[1]=(View)findViewById(R.id.view2);
		degree[1]=(ImageView)findViewById(R.id.ImageViewDegree2);
		onoff[1]=(TextView)findViewById(R.id.textViewOnOff);
		temp[1]=(TextView)findViewById(R.id.textViewDegree2);
		cent[1]=(TextView)findViewById(R.id.TextViewCent2);
		roomtemp[1]=(TextView)findViewById(R.id.TextViewRoomTemp2);
		iv[2]=(View)findViewById(R.id.view3);
		degree[2]=(ImageView)findViewById(R.id.ImageViewDegree3);
		onoff[2]=(TextView)findViewById(R.id.TextViewOnOff3);
		temp[2]=(TextView)findViewById(R.id.TextViewDegree3);
		cent[2]=(TextView)findViewById(R.id.TextViewCent3);
		roomtemp[2]=(TextView)findViewById(R.id.TextViewRoomTemp3);
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
				Intent i=new Intent(threeroomact.this,settingact.class);
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
				threeroomact.this.runOnUiThread(new Runnable() {
		public void run()
		{
			int i=0;
			while(i<result.length)
			{
				System.out.println("i is="+i+" and iv.lenght is "+ iv.length);
				String r=result[i];
				if(i>= iv.length)
					break;
					if(r.equals("0"))
						{
						//iv[i].setImageResource(R.drawable.offsmall);
						}
					else
					{
						if(i==0)
						iv[i].setBackgroundResource(R.drawable.hotthreevertical);
						else
						iv[i].setBackgroundResource(R.drawable.hotthreehori);
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
		else if(arg0==iv[1])
		{
			Intent i=new Intent(this,roomdetailact.class);
			i.putExtra("status",status[1]);
			i.putExtra("roomno","2");
			i.putExtra("email",email);
			i.putExtra("devid",devarray[1]);
			startActivity(i);	
		}
		else
		{
			Intent i=new Intent(this,roomdetailact.class);
			System.out.println("status "+status[2]);
			i.putExtra("status",status[2]);
			i.putExtra("roomno","3");
			i.putExtra("email",email);
			i.putExtra("devid",devarray[2]);
			startActivity(i);	
		}
		
	}
}
