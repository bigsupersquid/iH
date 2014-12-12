package com.ih.iheat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class startbro extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
	              Intent App = new Intent(context, Splash.class);
	              App.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	              context.startActivity(App);
		}
		}

	}


