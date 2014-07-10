package com.tenderloinhousing.apps;

import com.tenderloinhousing.apps.model.Case;
import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseFacebookUtils;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;

public class THCApplication extends Application
{
    @Override
    public void onCreate()
    {
	super.onCreate();
	// Required - Initialize the Parse SDK
	Parse.initialize(this, getString(R.string.parse_app_id),
		getString(R.string.parse_client_key));


	ParseUser.enableAutomaticUser();
	ParseACL defaultACL = new ParseACL();
	// If you would like all objects to be private by default, remove this line.
	defaultACL.setPublicReadAccess(true);
	ParseACL.setDefaultACL(defaultACL, true);

	

	Parse.setLogLevel(Parse.LOG_LEVEL_DEBUG);

	// Optional - If you don't want to allow Facebook login, you can
	// remove this line (and other related ParseFacebookUtils calls)
	ParseFacebookUtils.initialize(getString(R.string.facebook_app_id));

	// Optional - If you don't want to allow Twitter login, you can
	// remove this line (and other related ParseTwitterUtils calls)
	ParseTwitterUtils.initialize(getString(R.string.twitter_consumer_key),
		getString(R.string.twitter_consumer_secret));
	
	
//	Parse Initialization
    ParseObject.registerSubclass(Case.class);
    Parse.initialize(this, "TVqCaEm8N44ScY0fDLx4eCuRhONTALAPbC7P0289", "CNgx7Wf1CNNle26yTVAi7bNGTCxVAIJd5McCxhRe");


//    Add a User and a Case
    ParseUser user = ParseUser.getCurrentUser();

    Case case1 = new Case("5", "My new Case", "address", "unit", "phoneNumber", "email", "languageSpoken", "description", user);      
	case1.saveInBackground();
    	    
	
	
    }
}
