package com.ih.iheat;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class roomdetailact extends Activity implements OnClickListener {
	String status,email,devid;
	TextView cent,temp,roomtemp,onoff,no,tv5,tv01;
	ImageView home,ppl,degree,slider,degree1;
	View v2;
	SeekBar mybar;
	boolean state=false;
	RelativeLayout screen;
	String imagepath=null;
	 String rno;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		status=getIntent().getStringExtra("status");
		rno=getIntent().getStringExtra("roomno");
		email=getIntent().getStringExtra("email");
		devid=getIntent().getStringExtra("devid");
		setContentView(R.layout.roomdetaillayout);
		tv5=(TextView)findViewById(R.id.textView5);
		tv01=(TextView)findViewById(R.id.TextView01);
		degree1=(ImageView)findViewById(R.id.ImageView01);
		home=(ImageView)findViewById(R.id.imageViewHome);
		
		ppl=(ImageView)findViewById(R.id.imageView2);
		getImage();
		
		degree=(ImageView)findViewById(R.id.imageViewDegree1);
		slider=(ImageView)findViewById(R.id.imageViewHeatState);
		ppl.setOnClickListener(this);
		v2=(View)findViewById(R.id.view2);
		 screen=(RelativeLayout)findViewById(R.id.rel1);
		//onbutton=(ImageView)findViewById(R.id.imageViewOnOff);
		
		slider.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(state)
				{
					//onbutton.setVisibility(View.VISIBLE);
					/*ppl.setVisibility(View.INVISIBLE);
					degree.setVisibility(View.INVISIBLE);
					v2.setVisibility(View.INVISIBLE);
					cent.setVisibility(View.INVISIBLE);
					temp.setVisibility(View.INVISIBLE);
					roomtemp.setVisibility(View.INVISIBLE); */
					screen.setBackgroundResource(R.drawable.room_on_back);
					//mybar.setVisibility(View.INVISIBLE);
					//no.setVisibility(View.INVISIBLE);
					//onoff.setText("Heating On");
					no.setVisibility(View.VISIBLE);
					//onbutton.setImageResource(R.drawable.button_on);
					degree1.setVisibility(View.VISIBLE);
					tv5.setVisibility(View.VISIBLE);
					tv01.setVisibility(View.VISIBLE);
					slider.setVisibility(View.VISIBLE);
					slider.setImageResource(R.drawable.app_on);
					mybar.setVisibility(View.VISIBLE);
					state=false;
					changeHeatStat("1");
					getTemp();
				}
				else
				{
					screen.setBackgroundResource(R.drawable.room_off_back);
					/*gree.setVisibility(View.VISIBLE);
					v2.setVisibility(View.VISIBLE);
					cent.setVisibility(View.VISIBLE);
					temp.setVisibility(View.VISIBLE);
					roomtemp.setVisibility(View.VISIBLE);*/
					//onbutton.setImageResource(R.drawable.button_off);
					mybar.setVisibility(View.INVISIBLE);
					no.setVisibility(View.INVISIBLE);
					degree1.setVisibility(View.INVISIBLE);
					tv5.setVisibility(View.INVISIBLE);
					tv01.setVisibility(View.INVISIBLE);
					//onoff.setText("Heating Off");
					mybar.setVisibility(View.INVISIBLE);
					slider.setImageResource(R.drawable.app_off);
					state=true;
					changeHeatStat("0");
				}
			}

			
		});
		no=(TextView)findViewById(R.id.textViewNo);
		 cent=(TextView)findViewById(R.id.textViewCent1);
		 temp=(TextView)findViewById(R.id.textViewTemp1);
		 roomtemp=(TextView)findViewById(R.id.textViewRoomTemp1);
		 //onoff=(TextView)findViewById(R.id.textViewOnOff1);
		 mybar = (SeekBar) findViewById(R.id.seekBar1);

	        mybar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

	      @Override
	      public void onStopTrackingTouch(SeekBar seekBar) {

	        //add here your implementation
	      }

	      @Override
	      public void onStartTrackingTouch(SeekBar seekBar) {

	        //add here your implementation
	      }

	      @Override
	      public void onProgressChanged(SeekBar seekBar, int progress,
	          boolean fromUser) {
	            int value = seekBar.getProgress(); //this is the value of the progress bar (1-100)
	            int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
	            //value = progress; //this should also work
	            String valueString = val + ""; //this is the string that will be put above the slider
	            no.setText((value+7)+"");
	             //no.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
	             sendtoserver(value+7);
	            //seekBar.setThumb(writeOnDrawable(R.drawable.thumbler_small, valueString));        
	      }
	    });
	    
		home.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(roomdetailact.this,homeact.class);
				startActivity(i);
				
			}
		});
		
		if(!status.equals("0"))
		{
			screen.setBackgroundResource(R.drawable.room_on_back);
			/*ppl.setVisibility(View.INVISIBLE);
			degree.setVisibility(View.INVISIBLE);
			v2.setVisibility(View.INVISIBLE);
			cent.setVisibility(View.INVISIBLE);
			temp.setVisibility(View.INVISIBLE);
			roomtemp.setVisibility(View.INVISIBLE); */
			//mybar.setVisibility(View.INVISIBLE);
			//no.setVisibility(View.INVISIBLE);
			//onoff.setText("Heating On");
			//mybar.setVisibility(View.INVISIBLE);
			//slider.setVisibility(View.INVISIBLE);
			//onbutton.setImageResource(R.drawable.button_on);
			//slider.setVisibility(View.VISIBLE);
			slider.setImageResource(R.drawable.app_on);
			state=false;
			getTemp();
		}
		else
		{
			state=true;
			no.setVisibility(View.INVISIBLE);
			degree1.setVisibility(View.INVISIBLE);
			tv5.setVisibility(View.INVISIBLE);
			tv01.setVisibility(View.INVISIBLE);
			//onoff.setText("Heating On");
			mybar.setVisibility(View.INVISIBLE);
			
		}
			
			//setBackgroundResource(R.drawable.room_on_back);
	}
	 public BitmapDrawable writeOnDrawable(int drawableId, String text){

		    Bitmap bm = BitmapFactory.decodeResource(getResources(), drawableId).copy(Bitmap.Config.ARGB_8888, true);
            
		    Paint paint = new Paint(); 
		    paint.setStyle(Style.FILL);  
		    paint.setColor(Color.BLACK); //Change this if you want other color of text
		    paint.setTextSize(50); //Change this if you want bigger/smaller font
		    Bitmap bmpScaled = Bitmap.createScaledBitmap(bm,70 ,70, true);
		    Canvas canvas = new Canvas(bmpScaled);
		    canvas.drawText(text, 0, bmpScaled.getHeight(), paint); //Change the position of the text here
            
		    return new BitmapDrawable(bm);
		}
@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	Intent intent = new Intent();
    intent.setType("image/*");
    intent.setAction(Intent.ACTION_GET_CONTENT);
    startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);
	
}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
     
    if (requestCode == 1 && resultCode == RESULT_OK) {
        //Bitmap photo = (Bitmap) data.getData().getPath(); 
       
        Uri selectedImageUri = data.getData();
        imagepath = getPath(selectedImageUri);
        Bitmap bitmap=BitmapFactory.decodeFile(imagepath);
        ppl.setImageBitmap(bitmap);
        saveimage(bitmap);
        //phstat=true;
         
    }
}
     public String getPath(Uri uri) {
            String[] projection = { MediaStore.Images.Media.DATA };
            Cursor cursor = managedQuery(uri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
 public void saveimage(Bitmap bm)
 {
	 ByteArrayOutputStream buffer = new ByteArrayOutputStream(bm.getWidth() * bm.getHeight());
	 bm.compress(CompressFormat.PNG, 0 /*ignored for PNG*/, buffer);
	 //int bytes = bm.getByteCount();
	//or we can calculate bytes this way. Use a different value than 4 if you don't use 32bit images.
	//int bytes = b.getWidth()*b.getHeight()*4; 

	//ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
	//bm.copyPixelsToBuffer(buffer); //Move the byte data to the buffer

	//byte[] array = buffer.array();
	    System.out.println("Buffer size during change "+buffer.size());
	   
	 try{
		SQLiteDatabase db=openOrCreateDatabase("iheatdb",MODE_PRIVATE,null);
		db.execSQL("create table if not exists roomimg(roomno varchar(10),img BLOB)");
		Cursor c=db.rawQuery("select * from roomimg where roomno='"+rno+"'",null);
		if(c !=null && c.getCount()==0)
		{
			String sql="insert into roomimg values(?,?)";
			SQLiteStatement insertStmt      =   db.compileStatement(sql);
			insertStmt.clearBindings();
		    insertStmt.bindString(1,rno);
		    
		    insertStmt.bindBlob(2,buffer.toByteArray());
		    insertStmt.executeInsert();
			//db.execSQL(sql);
		    Toast.makeText(this,"Picture Set Succesfully",Toast.LENGTH_LONG).show();
		}
		else
		{
			String sql="update roomimg set img=? where roomno=?";
			SQLiteStatement insertStmt      =   db.compileStatement(sql);
			insertStmt.clearBindings();
			insertStmt.bindBlob(1,buffer.toByteArray());
		    insertStmt.bindString(2,rno);
		    
		    
		    insertStmt.executeUpdateDelete();
		    Toast.makeText(this,"Picture Changed Succesfully",Toast.LENGTH_LONG).show();
			//db.execSQL(sql);
			
		}
		db.close();
	 }
	 catch(Exception e)
	 {
		 System.out.println("Exceppppption in saving img"+e.toString());
		 
	 }
	 
 }
 public void getImage()
 {
	 SQLiteDatabase db=openOrCreateDatabase("iheatdb",MODE_PRIVATE,null);
		db.execSQL("create table if not exists roomimg(roomno varchar(10),img BLOB)");
		Cursor c=db.rawQuery("select * from roomimg where roomno='"+rno+"'",null);
		System.out.println("image s "+c.getCount());
		if(c !=null && c.getCount()==0)
			{
			db.close();
			System.out.println("image is null");
			return;
			}
			else
			{
				c.moveToFirst();
				byte[] bb = c.getBlob(c.getColumnIndex("img"));
				db.close();
				ppl.setImageBitmap(BitmapFactory.decodeByteArray(bb, 0, bb.length));
				System.out.println("image set ..."+bb.length);
				
			}
		
				
 }
 public void sendtoserver(final int val)
 {
	 		Thread th=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String u="?email="+email+"&devid="+devid+"&temp="+val;
					final String  result=Connection.GET("http://www.thermostat.ie/test/androangechheatstat.jsp"+u);
					System.out.println("Heat status changed for "+devid+" to "+val+"Result "+result);
					}
	 		});
	 		th.start();
 }
 public void getTemp()
 {
	 Thread th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String u="?email="+email+"&devid="+devid;
				final String  result=Connection.GET("http://www.thermostat.ie/test/androgetheatstat.jsp"+u);
				System.out.println("Heat status changed for "+devid+"Result "+result);
				roomdetailact.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
					roomdetailact.this.mybar.setProgress((int)Float.parseFloat(result)-7);	
					}
				});
				}
		});
	
	 th.start();
 }
 private void changeHeatStat(final String string) {
		// TODO Auto-generated method stub
	 Thread th=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				String u="?email="+email+"&devid="+devid+"&stat="+string;
				final String  result=Connection.GET("http://www.thermostat.ie/test/androchangeheatstat.jsp"+u);
				System.out.println(devid+" status "+string);
				}
		});
		th.start();
	}
}
