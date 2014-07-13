package com.tenderloinhousing.apps;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.tenderloinhousing.apps.helper.BuildingList;
import com.tenderloinhousing.apps.model.Building;
import com.tenderloinhousing.apps.model.Case;
import com.tenderloinhousing.apps.model.User;

public class THCApplication extends Application
{
    @Override
    public void onCreate()
    {
	super.onCreate();

	ParseObject.registerSubclass(Building.class);
	ParseObject.registerSubclass(User.class);
	ParseObject.registerSubclass(Case.class);

	Parse.initialize(this, getString(R.string.parse_app_id), getString(R.string.parse_client_key));
	ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));
	ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key), getString(R.string.twitter_consumer_secret));

	ParseUser.enableAutomaticUser();
	ParseACL defaultACL = new ParseACL();
	// If you would like all objects to be private by default, remove this line.
	defaultACL.setPublicReadAccess(true);
	ParseACL.setDefaultACL(defaultACL, true);

	Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

	//    String id = buildingList.getBuildingIdByName("Vincent Hotel");
	//    Log.d("DEBUG", id);
	
    }
}
