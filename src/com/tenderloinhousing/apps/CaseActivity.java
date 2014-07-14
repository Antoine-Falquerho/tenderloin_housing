package com.tenderloinhousing.apps;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tenderloinhousing.apps.activity.BaseFragmentActivity;
import com.tenderloinhousing.apps.fragment.CaseDetailsFragment;
import com.tenderloinhousing.apps.fragment.CaseFragment;
import com.tenderloinhousing.apps.model.Case;

public class CaseActivity extends BaseFragmentActivity
{
    LatLng laglng;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_case);
	
		String method = getIntent().getStringExtra(METHOD_KEY);			
		laglng = getIntent().getParcelableExtra(LATLNG_KEY);
		
		switch (method)
		{
	        	case METHOD_CODE_DETAIL:
	        		showCaseDetailFragment();	        	    
	        	case METHOD_CODE_CREATE:
	        		showCreateCaseFragment();	        	    
	        	default:
	        		showCreateCaseFragment();        	    
		}


    }

    private void showCreateCaseFragment()
    {
	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

	Bundle bundle = new Bundle();
	bundle.putParcelable(LATLNG_KEY, laglng);	
	CaseFragment caseFragment = CaseFragment.newInstance(bundle);	
	transaction.replace(R.id.flCase, caseFragment);
	
	transaction.commit();
    }
    
    private void showCaseDetailFragment()
    {    	
    	String case_id = getIntent().getStringExtra(CASE_ID_KEY);
    	
//    	String case_id = "MbHt8dslg3";
    	
    	// Define the class we would like to query
    	ParseQuery<Case> query = ParseQuery.getQuery(Case.class);
    	// Define our query conditions
    	query.whereEqualTo("objectId", case_id);
    	// Execute the find asynchronously
    	query.findInBackground(new FindCallback<Case>() {
    	    public void done(List<Case> itemList, ParseException e) {
    	        if (e == null) {
    	            // Access the array of results here
    	            Case myCase = itemList.get(0);
    	            
    	            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

    	    		Bundle bundle = new Bundle();
    	    		//TODO populate case details into Bundle here
    	    		// bundle.putString(SCREEN_NAME_KEY, screenName);
    	    		
    	    		CaseDetailsFragment detailsFragment = CaseDetailsFragment.newInstance(bundle, myCase);
    	    		
    	    		transaction.replace(R.id.flCase, detailsFragment);
    	    	
    	    		transaction.commit();
    	    		
    	            Toast.makeText(CaseActivity.this, myCase.getDescription(), Toast.LENGTH_SHORT).show();
    	        } else {
    	            Log.d("item", "Error: " + e.getMessage());
    	        }
    	    }

    	});

    	
    }
}
