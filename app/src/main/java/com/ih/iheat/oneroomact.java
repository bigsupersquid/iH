package com.ih.iheat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class oneroomact extends Activity implements OnClickListener {
	View iv[]=new View[1];
	ImageView degree[]=new ImageView[1];
	TextView onoff[]=new TextView[1];
	TextView t;
	TextView cent[]=new TextView[1];
	TextView roomtemp[]=new TextView[1];
	String devarray[];
	String status[]=new String[1];
	ImageView setup;
	String email;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.singleroomlayout);
		email=getIntent().getStringExtra("email");
		iv[0]=(View)findViewById(R.id.view1);
		iv[0].setOnClickListener(this);
		status[0]="0";
		degree[0]=(ImageView)findViewById(R.id.imageViewDegree1);
		onoff[0]=(TextView)findViewById(R.id.textViewOnOff);
		t=(TextView)findViewById(R.id.textViewTemp1);
		   // t.setText("an");                             
		cent[0]=(TextView)findViewById(R.id.textViewCent1);
		roomtemp[0]=(TextView)findViewById(R.id.textViewRoomTemp1);
		setup=(ImageView)findViewById(R.id.imageViewSetup);
		
		setup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(oneroomact.this,settingact.class);
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
				oneroomact.this.runOnUiThread(new Runnable() {
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
						
						//iv[i].setBackgroundResource(R.drawable.room_off_back);
					}
					else
						{
						iv[i].setBackgroundResource(R.drawable.hot_main);
						roomtemp[i].setVisibility(View.INVISIBLE);
						t.setVisibility(View.INVISIBLE);
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
	Intent i=new Intent(this,roomdetailact.class);
	i.putExtra("status",status[0]);
	i.putExtra("roomno","1");
	i.putExtra("email",email);
	i.putExtra("devid",devarray[0]);
	startActivity(i);
	
}
}
