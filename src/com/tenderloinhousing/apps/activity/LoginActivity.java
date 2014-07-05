package com.tenderloinhousing.apps.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.R.layout;

public class LoginActivity extends Activity
{
    public static final String REST_CONSUMER_KEY = "xStctYyrNHtcxyqftFBmQifAh";
    public static final String REST_CONSUMER_SECRET = "bfmZu2aR6i2Wr0aE5FCsY88IlQQzGz5F9HvWZZcgjDmjhiYPkE";
  
    /** Called when the activity is first created. */
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_login);

	ParseAnalytics.trackAppOpened(getIntent());

	//createUserAccount();	

	signinWithTwitter();
    }

    private void createUserAccount()
    {
	// Create the ParseUser
	ParseUser user = new ParseUser();
	// Set core properties
	user.setUsername("cindyltq");
	user.setPassword("secret123");
	user.setEmail("cindyltq@hotmail.com");
	// Set custom properties
	user.put("phone", "650-253-0000");
	// Invoke signUpInBackground
	user.signUpInBackground(new SignUpCallback()
	{
	    public void done(ParseException e)
	    {
		if (e == null)
		{
		    // Hooray! Let them use the app now.
		    Toast.makeText(LoginActivity.this, "User sign up done!", Toast.LENGTH_SHORT).show();
		}
		else
		{
		    // Sign up didn't succeed. Look at the ParseException to figure out what went wrong
		    Toast.makeText(LoginActivity.this, "User sign up failed!", Toast.LENGTH_SHORT).show();
		}
	    }
	});
    }

    private void signinWithTwitter()
    {
	ParseTwitterUtils.initialize(REST_CONSUMER_KEY, REST_CONSUMER_SECRET);
	ParseTwitterUtils.logIn(this, new LogInCallback()
	{
	    @Override
	    public void done(ParseUser user, ParseException err)
	    {
		if (user == null)
		{
		    Log.d("MyApp", "Uh oh. The user cancelled the Twitter login.");
		}
		else if (user.isNew())
		{
		    Log.d("MyApp", "User signed up and logged in through Twitter!");
		}
		else
		{
		    Log.d("MyApp", "User logged in through Twitter!");
		}
	    }
	});
	
    }

    
}
