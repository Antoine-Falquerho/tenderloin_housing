package com.tenderloinhousing.apps;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tenderloinhousing.apps.activity.BaseFragmentActivity;
import com.tenderloinhousing.apps.dao.ParseDAO;
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
		CaseFragment caseFragment = CaseFragment.newInstance(bundle);	
		transaction.replace(R.id.flCase, caseFragment);
		
		transaction.commit();
    }
    
    private void showCaseDetailFragment()
    {    	
//    	String case_id = getIntent().getStringExtra(CASE_ID_KEY);
    	
    	String case_id = "LJ40s6IuTh";
    	
    	ParseDAO.getCaseById(case_id, new GetCallback<Case>() {
	           @Override
				public void done(Case foundCase, ParseException e) {
					if (e == null) {
	                    if (foundCase!=null){
	                    	Case myCase = foundCase;
	        	            
	        	            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

	        	    		Bundle bundle = new Bundle();
	        	    		//TODO populate case details into Bundle here
	        	    		// bundle.putString(SCREEN_NAME_KEY, screenName);
	        	    		
	        	    		CaseDetailsFragment detailsFragment = CaseDetailsFragment.newInstance(bundle, myCase);
	        	    		
	        	    		transaction.replace(R.id.flCase, detailsFragment);
	        	    	
	        	    		transaction.commit();
	                    }
	                } else {
                 	Toast.makeText(getApplicationContext(), "No case with that id",Toast.LENGTH_LONG).show();
	                    Log.d(ERROR, "Error: " + e.getMessage());
	                }
					
				}
	        });
    	
    }
}
