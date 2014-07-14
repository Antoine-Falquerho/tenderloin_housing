package com.tenderloinhousing.apps;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tenderloinhousing.apps.activity.BaseFragmentActivity;
import com.tenderloinhousing.apps.fragment.CaseDetailsFragment;
import com.tenderloinhousing.apps.fragment.CaseFragment;
import com.tenderloinhousing.apps.model.Case;

public class CaseActivity extends BaseFragmentActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_case);
	
		String method = getIntent().getStringExtra(METHOD_KEY);			
		
		switch (method)
		{
	        	case METHOD_CODE_DETAIL:
	        		showCaseDetailFragment();	
	        		break;
	        	case METHOD_CODE_CREATE:
	        		showCreateCaseFragment();	        	    
	        		break;
	        	default:
	        		showCreateCaseFragment();        	    
	        		break;
		}


    }

    private void showCreateCaseFragment()
    {
    	
   Log.d("showCreateCaseFragment", "##");
    	
    	
	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

	Bundle bundle = new Bundle();
	//TODO populate case details into Bundle here
	// bundle.putString(SCREEN_NAME_KEY, screenName);
	
	CaseFragment caseFragment = CaseFragment.newInstance(bundle);
	
	transaction.replace(R.id.flCase, caseFragment);

	transaction.commit();
    }
    
    private void showCaseDetailFragment()
    {    	
    	String case_id = getIntent().getStringExtra("case_id");
    	
//    	String case_id = "PSG5oTjcvS";
    	
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
