package com.ih.iheat;

import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class homeact extends Activity implements OnClickListener {
	ImageView searchview;
	ProgressBar pb;
	TextView tv;
	private WifiManager _mainWifi;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.test);
	SQLiteDatabase db=openOrCreateDatabase("iheatdb",MODE_PRIVATE,null);
	db.execSQL("create table if not exists userinfo(email varchar(100),pass varchar(100),tabid varchar(100))");
	Cursor c=db.rawQuery("select * from userinfo", null);
	if(c.getCount()>0)
	{
		c.moveToFirst();
		String email=c.getString(c.getColumnIndex("email"));
		Cursor c1=db.rawQuery("select rooms from userroom" ,null);
		if(c1.getCount() >0)
		{
			c1.moveToFirst();
			int rno=c1.getInt(0);
			Intent i;
			switch(rno)
			{
			case 1:
				i=new Intent(this,oneroomact.class);
				i.putExtra("email",email);
				startActivity(i);
				break;
			case 2:
				i=new Intent(this,tworoomact.class);
				i.putExtra("email",email);
				startActivity(i);
				break;
			case 3:
				i=new Intent(this,threeroomact.class);
				i.putExtra("email",email);
				startActivity(i);
				break;
			case 4:
				i=new Intent(this,fourroomact.class);
				i.putExtra("email",email);
				startActivity(i);
				break;
			case 5:
				i=new Intent(this,fiveroomact.class);
				i.putExtra("email",email);
				startActivity(i);
				break;
			case 6:
				i=new Intent(this,sixroomact.class);
				i.putExtra("email",email);
				startActivity(i);
				break;
			}
		}
	}
	db.close();
	searchview=(ImageView)findViewById(R.id.imageViewSearch);
	searchview.setOnClickListener(this);
	pb=(ProgressBar)findViewById(R.id.progressBarLogin);
	tv=(TextView)findViewById(R.id.textSearch);
	
}
@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	pb.setVisibility(View.VISIBLE);
	tv.setVisibility(View.VISIBLE);
	startWifiScan();
		
	}
private void checkWifi()
{
  this._mainWifi = ((WifiManager)getSystemService("wifi"));
  if (!this._mainWifi.isWifiEnabled())
    new AlertDialog.Builder(this).setTitle("Wifi  disabled..").setMessage("Enable Wifi !!").setPositiveButton(17039379, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        homeact.this._mainWifi.setWifiEnabled(true);
        homeact.this.startWifiScan();
      }
    }).setNegativeButton("OK", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.cancel();
      }
    }).show();
  
}
private void startWifiScan()
{
  
  new Thread(new Runnable()
  {
    public void run()
    {
      try
      {
        Thread.sleep(4000);
        //homeact.this._receiverWifi = new homeact.WifiReceiver(homeact.this);
       // homeact.this.registerReceiver(homeact.this._receiverWifi, new IntentFilter(homeact.this._mIntentFilter));
        checkWifi();
       if( homeact.this._mainWifi.startScan())
       {
        homeact.this.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//homeact.this._progress.setMessage(" Wi-Fi.... found");
				homeact.this.pb.setVisibility(View.INVISIBLE);
				homeact.this.tv.setVisibility(View.INVISIBLE);
				List info=homeact.this._mainWifi.getScanResults();
				Intent intent=new Intent(homeact.this,resultact.class);
				String dinfo=info.get(0).toString();
				String ssid=dinfo.substring(dinfo.indexOf(":"),dinfo.indexOf(","));
				//intent.putExtra("ssid",ssid);
			
				ArrayList<String> arrlist=new ArrayList<String>();
				for(int i=0;i<info.size();i++)
					{
					Log.d("device "+i,info.get(i).toString());
					String dinfo1=info.get(i).toString();
					String ssid1=dinfo1.substring(dinfo1.indexOf(":")+1,dinfo1.indexOf(","));
					arrlist.add(ssid1);
					}
				String arr[]=new String[arrlist.size()];
				for(int i=0;i<arr.length;i++)
					arr[i]=arrlist.get(i);
				intent.putExtra("ssid", arr);
				startActivity(intent);
			}
		});
       }
       else
       {
      	 homeact.this.runOnUiThread(new Runnable() {
   			
   			@Override
   			public void run() {
   				// TODO Auto-generated method stub
   				homeact.this.pb.setVisibility(View.INVISIBLE);
				homeact.this.tv.setVisibility(View.INVISIBLE);
   				Toast.makeText(homeact.this,"No Wi-Fi device found.",Toast.LENGTH_LONG).show();
   			}
   		}); 
       }
        //homeact.this.startWifiScan();
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        
          localInterruptedException.printStackTrace();
      }
    }
  }).start();
}



}
