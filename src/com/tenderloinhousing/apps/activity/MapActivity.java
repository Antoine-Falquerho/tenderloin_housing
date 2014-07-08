package com.tenderloinhousing.apps.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.tenderloinhousing.apps.R;

public class MapActivity extends Activity {
    
    ParseUser user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_map);
		
		ParseAnalytics.trackAppOpened(getIntent());
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

}
