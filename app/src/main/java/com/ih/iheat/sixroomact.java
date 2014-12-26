package com.ih.iheat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class sixroomact extends Activity implements OnClickListener {
	View iv[]=new View[6];
    RelativeLayout rl[]=new RelativeLayout[6];
	ImageView setup;
	String email;
    String devarray[];
    String status[]=new String[6];

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide statusbar of Android
        // could also be done later
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.sixroomlayout);
		email=getIntent().getStringExtra("email");
		iv[0]=(View)findViewById(R.id.view1);
        rl[0]=(RelativeLayout)findViewById(R.id.window1);
        final TextView onoff1=(TextView)rl[0].findViewById(R.id.OnOff);
        final TextView temp1=(TextView)rl[0].findViewById(R.id.Temp);

        iv[1]=(View)findViewById(R.id.view2);
        rl[1]=(RelativeLayout)findViewById(R.id.window2);
        final TextView onoff2=(TextView)rl[1].findViewById(R.id.OnOff);
        final TextView temp2=(TextView)rl[1].findViewById(R.id.Temp);

		iv[2]=(View)findViewById(R.id.view3);
        rl[2]=(RelativeLayout)findViewById(R.id.window3);
        final TextView onoff3=(TextView)rl[2].findViewById(R.id.OnOff);
        final TextView temp3=(TextView)rl[2].findViewById(R.id.Temp);

		iv[3]=(View)findViewById(R.id.view4);
        rl[3]=(RelativeLayout)findViewById(R.id.window4);
        final TextView onoff4=(TextView)rl[3].findViewById(R.id.OnOff);
        final TextView temp4=(TextView)rl[3].findViewById(R.id.Temp);

		iv[4]=(View)findViewById(R.id.view5);
        rl[4]=(RelativeLayout)findViewById(R.id.window5);
        final TextView onoff5=(TextView)rl[4].findViewById(R.id.OnOff);
        final TextView temp5=(TextView)rl[4].findViewById(R.id.Temp);

		iv[5]=(View)findViewById(R.id.view6);
        rl[5]=(RelativeLayout)findViewById(R.id.window6);
        final TextView onoff6=(TextView)rl[5].findViewById(R.id.OnOff);
        final TextView temp6=(TextView)rl[5].findViewById(R.id.Temp);

        for(int i=0;(i<status.length)&&(i<5);i++)
        {
            status[i]="0";
            try {
                iv[i].setOnClickListener(this);
            } catch (Exception e) {
                Log.d("sixroomact", e.toString());
            }
        }
		setup=(ImageView)findViewById(R.id.imageViewSetup);
		setup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(sixroomact.this,settingact.class);
				startActivity(i);
			}
		});
		Thread th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String u="?email="+email;
				final String  result[]=Connection.GET("http://www.thermostat.ie/test/androheatstat.jsp"+u).split("%");
				sixroomact.this.runOnUiThread(new Runnable() {
		public void run()
		{
			int i=0;
			while(i<result.length)
			{
				String r=result[i];
                if(i>= iv.length)
                    break;
                if(r.equals("0")) {}
                //    iv[i].setImageResource(R.drawable.cold_smaller);
                else {
                    iv[i].setBackgroundResource(R.drawable.hot_smaller);
                    switch (i) {
                        case 0:
                            onoff1.setText("Heating On");
                            break;
                        case 1:
                            onoff2.setText("Heating On");
                            break;
                        case 2:
                            onoff3.setText("Heating On");
                            break;
                        case 3:
                            onoff4.setText("Heating On");
                            break;
                        case 4:
                            onoff5.setText("Heating On");
                            break;
                        case 5:
                            onoff6.setText("Heating On");
                            break;
                    }
                }


                i++;
			}
		}
				});
	}
});
		th.start();
	}

    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        for (int t = 0; t < 6; t++) {
            if (arg0 == iv[t]) {
                Intent i = new Intent(this, roomdetailact.class);
                i.putExtra("status", status[t]);
                i.putExtra("roomno", "" + t);
                i.putExtra("email", email);
                i.putExtra("devid", devarray[t]);
                startActivity(i);
            }
        }
    }

}
