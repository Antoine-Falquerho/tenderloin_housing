package com.tenderloinhousing.apps.activity;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.adapter.CasePagerAdapter;

public class ManageCaseActivity extends BaseFragmentActivity  implements ActionBar.TabListener
{
    ViewPager vpPager;
    CasePagerAdapter adapter;
    OnPageChangeListener mPageChangeListener;
    
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "My Cases", "New Cases", "All Cases"};
 

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_ACTION_BAR);
	requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);	
	setContentView(R.layout.activity_manage_case);

	mPageChangeListener = getPageChangeListener();
	showProgressBar();
	vpPager = (ViewPager) findViewById(R.id.vpPager);
	adapter = new CasePagerAdapter(getSupportFragmentManager());
	vpPager.setAdapter(adapter);
	vpPager.setOnPageChangeListener(mPageChangeListener);

	// Bind the title indicator to the adapter
	//TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
	//indicator.setViewPager(vpPager);
	//indicator.setOnPageChangeListener(mPageChangeListener);
	
        actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        getActionBar().setHomeButtonEnabled(true);  
 
    }

    private OnPageChangeListener getPageChangeListener()
    {
	return new OnPageChangeListener()
	{
	    @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }
 
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
 
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
	};
    }
    
    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
 
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected show respected fragment view
        vpPager.setCurrentItem(tab.getPosition());
    }
 
    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }
 
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	getMenuInflater().inflate(R.menu.menu_report, menu);
	getMenuInflater().inflate(R.menu.menu_explore, menu);
	return true;
    }
    
    // Respond to ActionBar icon click
    public boolean onOptionsItemSelected(MenuItem item)
    {
	switch (item.getItemId())
	{
	case R.id.miReport:
	    doReport();
	    return true;
	case android.R.id.home:
		startActivity(new Intent(getApplicationContext(), MapActivity.class));
	case R.id.miExplore:
	    doExplore();
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }
    
    private void doReport()
    {
	//Use DispatchActivity to guard the gate to CaseActivity and prompt for sign in
	Intent intent = new Intent(this, CreateCaseDispatchActivity.class);
	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK	| Intent.FLAG_ACTIVITY_NEW_TASK);	
	startActivity(intent);
    }

    private void doExplore()
    {
	Intent intent = new Intent(this, MapActivity.class);	
	startActivity(intent);
    }

}
