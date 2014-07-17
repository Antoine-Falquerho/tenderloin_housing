package com.tenderloinhousing.apps.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.adapter.CasePagerAdapter;
import com.viewpagerindicator.TabPageIndicator;
public class ManageCaseActivity extends FragmentActivity
{
    ViewPager vpPager;
    CasePagerAdapter adapter;
    OnPageChangeListener mPageChangeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_manage_case);

	// if (savedInstanceState == null)
	// {
	// getFragmentManager().beginTransaction()
	// .add(R.id.container, new PlaceholderFragment())
	// .commit();
	// }

	mPageChangeListener = getPageChangeListener();

	vpPager = (ViewPager) findViewById(R.id.vpPager);
	adapter = new CasePagerAdapter(getSupportFragmentManager());
	vpPager.setAdapter(adapter);
	vpPager.setOnPageChangeListener(mPageChangeListener);

	// Bind the title indicator to the adapter
	 TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
	        indicator.setViewPager(vpPager);

	indicator.setOnPageChangeListener(mPageChangeListener);

    }

    private OnPageChangeListener getPageChangeListener()
    {
	return new OnPageChangeListener()
	{

	    // This method will be invoked when a new page becomes selected.
	    @Override
	    public void onPageSelected(int position)
	    {
		Toast.makeText(ManageCaseActivity.this,"Selected page position: " + position, Toast.LENGTH_SHORT).show();
	    }

	    // This method will be invoked when the current page is scrolled
	    @Override
	    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	    {
		// Code goes here
	    }

	    // Called when the scroll state changes:
	    // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
	    @Override
	    public void onPageScrollStateChanged(int state)
	    {
		// Code goes here
	    }
	};
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

	// Inflate the menu; this adds items to the action bar if it is present.
	getMenuInflater().inflate(R.menu.case_list, menu);
	return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
	// Handle action bar item clicks here. The action bar will
	// automatically handle clicks on the Home/Up button, so long
	// as you specify a parent activity in AndroidManifest.xml.
	int id = item.getItemId();
	if (id == R.id.action_settings)
	{
	    return true;
	}
	return super.onOptionsItemSelected(item);
    }

}
