package com.tenderloinhousing.apps.fragment;

import android.os.Bundle;

import com.parse.FindCallback;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.model.Case;

public class NewCaseListFragment extends CaseListBaseFragment
{    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
    }

    @Override
    public void loadCases(FindCallback<Case> callback)
    {
	ParseDAO.getNewCases(callback);
    }
    
    
}
