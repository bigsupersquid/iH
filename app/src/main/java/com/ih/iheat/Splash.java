package com.ih.iheat;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;
import android.widget.VideoView;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String videoName = "iheatupdaed";

        int id = getResources().getIdentifier(videoName, "raw",getPackageName());
        System.out.println("Package......"+getPackageName());
        final String path = "android.resource://" + getPackageName() + "/" + id;
        Uri video = Uri.parse(path);
        VideoView vdo=(VideoView)findViewById(R.id.splashvdo);
        vdo.setVideoURI(video);
        vdo.start();
        vdo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
               // Toast.makeText(Splash.this,"Vdo finished",Toast.LENGTH_LONG).show();
            Intent i=new Intent(Splash.this,homeact.class);
            Splash.this.startActivity(i);
            Splash.this.finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }
    
}
