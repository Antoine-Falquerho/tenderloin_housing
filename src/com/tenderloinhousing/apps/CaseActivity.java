package com.tenderloinhousing.apps;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.tenderloinhousing.apps.activity.BaseFragmentActivity;
import com.tenderloinhousing.apps.fragment.CaseDetailsFragment;
import com.tenderloinhousing.apps.fragment.CaseFragment;

public class CaseActivity extends BaseFragmentActivity
{

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_case);

	showCaseFragment();

    }

    private void showCaseFragment()
    {
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
	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

	Bundle bundle = new Bundle();
	//TODO populate case details into Bundle here
	// bundle.putString(SCREEN_NAME_KEY, screenName);
	
	CaseDetailsFragment detailsFragment = CaseDetailsFragment.newInstance(bundle);
	
	transaction.replace(R.id.flCase, detailsFragment);

	transaction.commit();
    }
}
