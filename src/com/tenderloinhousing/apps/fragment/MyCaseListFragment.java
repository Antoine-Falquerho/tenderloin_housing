package com.tenderloinhousing.apps.fragment;

import android.os.Bundle;

import com.parse.FindCallback;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.model.Case;
import com.tenderloinhousing.apps.model.User;

public class MyCaseListFragment extends CaseListBaseFragment
{    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
    }

    @Override
    public void loadCases(FindCallback<Case> callback)
    {
	ParseDAO.getCaseByTenant(getTenant(), callback);
    }
    
    private User getTenant()
    {
	return (User) getArguments().getSerializable(TENANT_OBJ_KEY);
    }

    public static MyCaseListFragment newInstance(User tenant)
    {
	MyCaseListFragment fragment = new MyCaseListFragment();
	Bundle bundle = new Bundle();
	bundle.putSerializable(TENANT_OBJ_KEY, tenant);
	fragment.setArguments(bundle);

	return fragment;
    }

    
}
