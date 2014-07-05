package com.tenderloinhousing.apps;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class ParseApplication extends Application
{
   public static final String PARSE_APPLICATION_ID = "4XyvTyO4qonotVwUWPwhFBIfvVtcMvUVgnjJEdyF";
    public static final String PARSE_CLIENT_KEY = "kJsWEULuLy1hE6q2IQ8xfZ3O3QrCtaqKsxQ697dq";

    @Override
    public void onCreate()
    {
	super.onCreate();

	// Add your initialization code here
	Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);
	
	// Test creation of object
	testCreateObject();

	ParseUser.enableAutomaticUser();
	ParseACL defaultACL = new ParseACL();

	// If you would like all objects to be private by default, remove this line.
	defaultACL.setPublicReadAccess(true);

	ParseACL.setDefaultACL(defaultACL, true);

    }

    private void testCreateObject()
    {
	ParseObject testObject = new ParseObject("TestObject");
	testObject.put("foo", "bar");
	testObject.saveInBackground();
    }


}
