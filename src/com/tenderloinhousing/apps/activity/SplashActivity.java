package com.tenderloinhousing.apps.activity;

import java.util.Timer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.helper.BuildingList;

public class SplashActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
	}	

	@Override
	protected void onResume() {
		//Create a thread
		new Thread(new Runnable() {
	        public void run() {
//	        	Do heavy work in background
//	        	try {
//	        	    Thread.sleep(5000);
//	        	} catch (InterruptedException e) {
//	        	    e.printStackTrace();
//	        	}

	        	BuildingList buildingList = BuildingList.getInstance();
	            startActivity(new Intent(SplashActivity.this, MapActivity.class));
	            finish(); //End this activity
	        }
	    }).start();
		super.onResume();
}
}