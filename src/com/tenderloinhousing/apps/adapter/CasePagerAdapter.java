package com.tenderloinhousing.apps.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.parse.ParseUser;
import com.tenderloinhousing.apps.fragment.AllCaseListFragment;
import com.tenderloinhousing.apps.fragment.MyCaseListFragment;
import com.tenderloinhousing.apps.fragment.SearchCaseFragment;
import com.tenderloinhousing.apps.model.User;

public class CasePagerAdapter extends FragmentPagerAdapter
{
    private static final String[] CONTENT = new String[] { "All Cases", "My Cases", "Search by #" };

    public CasePagerAdapter(FragmentManager fragmentManager)
    {
	super(fragmentManager);
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position)
    {
	switch (position)
	{
	case 0: // Fragment # 0
	    return new AllCaseListFragment();
	case 1: // Fragment # 1
	    return MyCaseListFragment.newInstance((User) ParseUser.getCurrentUser());
	case 2: // Fragment # 2
	    return new SearchCaseFragment();
	default:
	    return null;
	}
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
	return CONTENT[position % CONTENT.length];
    }

    @Override
    public int getCount()
    {
	return CONTENT.length;
    }
}
