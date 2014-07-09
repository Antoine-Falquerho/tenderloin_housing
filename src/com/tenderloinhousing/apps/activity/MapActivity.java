package com.tenderloinhousing.apps.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.model.Case;

public class MapActivity extends Activity {
    
    ParseUser user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_map);
		
		ParseAnalytics.trackAppOpened(getIntent());
		
//		Parse Initialization
	    ParseObject.registerSubclass(Case.class);
	    Parse.initialize(this, "TVqCaEm8N44ScY0fDLx4eCuRhONTALAPbC7P0289", "CNgx7Wf1CNNle26yTVAi7bNGTCxVAIJd5McCxhRe");


//	    Add a User and a Case
	    ParseUser user = ParseUser.getCurrentUser();

	    Case case1 = new Case("5", "My new Case", "address", "unit", "phoneNumber", "email", "languageSpoken", "description", user);      
		case1.saveInBackground();
	    	    
	}	
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu)
	    {
		getMenuInflater().inflate(R.menu.menu_login, menu);
		return true;
	    }

	    // Respond to ActionBar icon click
	    public boolean onOptionsItemSelected(MenuItem item)
	    {
		switch (item.getItemId())
		{
		case R.id.miLogin:
		    handleSignIn();
		    return true;
		default:
		    return super.onOptionsItemSelected(item);
		}
	    }

	    private void handleSignIn()
	    {
		Intent intent = new Intent(this, LoginActivity.class);	
		startActivity(intent);
		
	    }
//	    
//	    @Override
//	    protected void onActivityResult(int requestCode, int resultCode, Intent data)
//	    {		
//		if (resultCode == RESULT_OK)
//		{
//		    user = ParseUser.getCurrentUser();
//		    Log.d("INFO", "User signing in is " + user.getUsername() );
//		}
//		else if (resultCode == RESULT_CANCELED)
//		{
//		    
//		}
//	    }
	    
	    
//	    ParseUser user = new ParseUser();
//		 // Set core properties
//		 user.setUsername("joestevens");
//		 user.setPassword("secret123");
//		 user.setEmail("email@example.com");
//		 // Set custom properties
//		 user.put("phone", "650-253-0000");
//		 // Invoke signUpInBackground
//		 user.signUpInBackground(new SignUpCallback() {
//			  public void done(ParseException e) {
//				    if (e == null) {
//				      // Hooray! Let them use the app now.
//				    } else {
//				      // Sign up didn't succeed. Look at the ParseException
//				      // to figure out what went wrong
//				    }
//				  }
//				});
	    
//	    ParseQuery<ParseUser> users = ParseUser.getQuery();	    
//	    users.findInBackground(new FindCallback<ParseUser>() {
//	      public void done(List<ParseUser> objects, ParseException e) {
//	        if (e == null) {
//	        	Log.d("debug", objects.get(0).toString());
//	        	Log.d("debug", "-------");
//	        	Case case1 = new Case("5", "Name", "address", "unit", "phoneNumber", "email", "languageSpoken", "description", objects.get(0));
//	    	    case1.saveInBackground();
//	        } else {
//	            // Something went wrong.
//	        }
//	      }
//	    });

}
