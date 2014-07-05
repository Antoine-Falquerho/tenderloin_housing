package com.tenderloinhousing.apps.activity;

import android.support.v4.app.FragmentActivity;

import com.tenderloinhousing.apps.helper.IConstants;

public class BaseFragmentActivity extends FragmentActivity implements IConstants
{
    // Should be called manually when an async task has started
    public void showProgressBar()
    {
	setProgressBarIndeterminateVisibility(true);
    }

    // Should be called when an async task has finished
    public void hideProgressBar()
    {
	setProgressBarIndeterminateVisibility(false);
    }

}
