package com.ih.iheat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class devpasswordact extends Activity implements OnClickListener {
	String devid,id;
	ImageView imgview;
	String email;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	Intent i=getIntent();
	devid=i.getStringExtra("devid");
	email=i.getStringExtra("email");
	id=devid.substring(devid.indexOf(":")+1, devid.indexOf(")")).trim();
	if(devid.contains("$$$"))
		devid=devid.replace("$$$","");
	if(devid.contains("$"))
		devid=devid.replace("$","");
	if(devid.contains(";"))
		devid=devid.replace(";","");
	System.out.println("idfinwjifnwinwifnwfi   "+id);
	//Toast.makeText(this,"id  "+id ,Toast.LENGTH_LONG).show();
	setContentView(R.layout.devpasswordlayout);
	TextView tv=(TextView)findViewById(R.id.textView1);
	tv.setText(devid);
	imgview=(ImageView)findViewById(R.id.imageViewConnect);
	imgview.setOnClickListener(this);
}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	EditText ed=(EditText)findViewById(R.id.editTextPass);
	final String pass=ed.getText().toString();
	Thread th=new Thread(new Runnable() {
		
		@Override
		public void run() {
			//System.out.println("email=="+viewdevicesact.this.email);
			// TODO Auto-generated method stub
			System.out.println("devid="+id+"  pass="+pass);
			String result=GET("http://www.thermostat.ie/test/andropassvalidate.jsp?devid="+id+"&pass="+pass);
			System.out.println("Result....."+result);
		    if(result.trim().equals("ok"))
		    {
		    	//Toast.makeText(devpasswordact.this,"Currect Password",Toast.LENGTH_LONG).show();
		    	Intent i=new Intent(devpasswordact.this,viewdevicesact.class);
		    	i.putExtra("devid",id);
		    	i.putExtra("email",email);
		    	devpasswordact.this.startActivity(i);
		    }
		    else
		    {
		    	devpasswordact.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(devpasswordact.this,"Incurrect Password",Toast.LENGTH_LONG).show();
					}
				});
		    }
		}
		});
	th.start();
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
