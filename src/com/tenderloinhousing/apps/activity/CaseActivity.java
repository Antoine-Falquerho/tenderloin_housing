package com.tenderloinhousing.apps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.tenderloinhousing.apps.R;
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
	
		int method = getIntent().getIntExtra(METHOD_KEY, METHOD_CODE_CREATE);	//default to create		
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
    	String case_id = getIntent().getStringExtra(CASE_ID_KEY);
    	
    	ParseDAO.getCaseById(case_id, new GetCallback<Case>() {
	           @Override
				public void done(Case foundCase, ParseException e) {
					if (e == null) {
                                	                    if (foundCase!=null){
                                	                    	     
                                	        	            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                
                                	        	    		Bundle bundle = new Bundle();
                                	        	    		bundle.putSerializable(CASE_KEY, foundCase);	        	    		
                                	        	    		CaseDetailsFragment detailsFragment = CaseDetailsFragment.newInstance(bundle);
                                	        	    		
                                	        	    		transaction.replace(R.id.flCase, detailsFragment);
                                	        	    	
                                	        	    		transaction.commit();
                                	                    }
                                	                } else {
                                                         	Toast.makeText(getApplicationContext(), "No case with that id",Toast.LENGTH_LONG).show();
                                        	                    Log.d(ERROR, "Error in getCaseByID: " + e.getMessage());
                                        	                }
					
				}
	        });
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	getMenuInflater().inflate(R.menu.menu_explore, menu);
	return true;
    }
    
    // Respond to ActionBar icon click
    public boolean onOptionsItemSelected(MenuItem item)
    {
	switch (item.getItemId())
	{
	case R.id.miExplore:
	    doExplore();
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }
    

    private void doExplore()
    {
	Intent intent = new Intent(this, MapActivity.class);	
	startActivity(intent);
    }
}
