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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class loginact extends Activity implements OnClickListener {
  EditText name,email,pass,tabid;
  ProgressBar pb;
  TextView tv;
  ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginlayout);
		name=(EditText)findViewById(R.id.editTextName);
		email=(EditText)findViewById(R.id.editTextEmail);
		pass=(EditText)findViewById(R.id.editTextPassword);
		tabid=(EditText)findViewById(R.id.editText1);
		pb=(ProgressBar)findViewById(R.id.progressBarLogin);
		tv=(TextView)findViewById(R.id.textViewLogin);
		iv=(ImageView)findViewById(R.id.imageConnect);
		iv.setOnClickListener(this);
	}
	@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		  pb.setVisibility(View.VISIBLE);
		  tv.setVisibility(View.VISIBLE);
			Thread th=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String u="?name="+name.getText().toString()+"&email="+email.getText().toString()+"&pass="+pass.getText().toString()+"&tabid="+tabid.getText().toString();
					String result=GET("http://www.thermostat.ie/test/andrologin.jsp"+u);
					loginact.this.runOnUiThread(new Runnable() {
			   			
			   			@Override
			   			public void run() {
			   				// TODO Auto-generated method stub
			   				loginact.this.pb.setVisibility(View.INVISIBLE);
							loginact.this.tv.setVisibility(View.INVISIBLE);
			   				
			   			}
			   		});
					if(result.trim().equals("success"))
					{
						Intent i=new Intent(loginact.this,loginsuccessact.class);
						i.putExtra("name",name.getText().toString());
						 i.putExtra("email",email.getText().toString());
						 i.putExtra("pass",pass.getText().toString());
						 i.putExtra("tabid",tabid.getText().toString());
						startActivity(i);
						
					}
					
					else
					{
						loginact.this.runOnUiThread(new Runnable() {
				   			
				   			@Override
				   			public void run() {
				   				// TODO Auto-generated method stub
				   				loginact.this.pb.setVisibility(View.INVISIBLE);
								loginact.this.tv.setVisibility(View.INVISIBLE);
								Toast.makeText(loginact.this,"Invalid Login info",Toast.LENGTH_LONG).show();
				   				
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
