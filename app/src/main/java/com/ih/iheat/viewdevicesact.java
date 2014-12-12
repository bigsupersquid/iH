package com.ih.iheat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class viewdevicesact extends Activity implements OnClickListener {
	ArrayList<String> web;
	ListView list;
	Integer img;
	String email;
	String devid;
	Button setting;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		email=getIntent().getStringExtra("email");
		devid=getIntent().getStringExtra("devid");
		setContentView(R.layout.devicelistlayout);
		setting=(Button)findViewById(R.id.buttonSetting);
		setting.setOnClickListener(this);
		web=new ArrayList<String>();
		Thread th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("email=="+viewdevicesact.this.email);
				// TODO Auto-generated method stub
				String result=GET("http://www.thermostat.ie/test/androdevid.jsp?email="+viewdevicesact.this.email);
				String devices[]=result.split("%");
				System.out.println(result);
				for(int i=0;i<devices.length;i++)
				{
					String dev=devices[i];
					//String d=dev.substring(dev.indexOf(":")+1, dev.indexOf(")")).trim();
					//String stat=dev.substring(dev.indexOf(")"),dev.indexOf("%")).trim();
					if(dev.contains("$$$"))
					web.add(devices[i]+";");
					else
					web.add(devices[i]);	
					System.out.print(devices[i]);
				}
				viewdevicesact.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						img=R.drawable.finddevice;
						
						CustomList adapter = new
						        CustomList(viewdevicesact.this, web, img);
						list=(ListView)findViewById(R.id.listView1);
						list.setAdapter(adapter);
				        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				                @Override
				                public void onItemClick(AdapterView<?> parent,View  view,
				                                        int position, long id) {
				                   // Toast.makeText(viewdevicesact.this, "You Clicked at " +web.get(position), Toast.LENGTH_SHORT).show();
				                	Intent i=new Intent(viewdevicesact.this,devpasswordact.class);
				                	i.putExtra("devid",web.get(position).toString());
				                	i.putExtra("email", viewdevicesact.this.email);
				                	startActivity(i);
				                }
				            });
					
						
					}
				});
			}
		});
		
		th.start();
		}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	Intent i=new Intent(this,settingact.class);
	startActivity(i);
	}
	public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";
 
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
    }
	// convert inputstream to String
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
 
    }
 
    // check network connection
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
            else
                return false;  
    }

}
