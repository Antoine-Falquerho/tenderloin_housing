package com.tenderloinhousing.apps.fragment;

import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.ParseUser;
import com.tenderloinhousing.apps.constant.UserType;
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
	User user = (User) ParseUser.getCurrentUser();

	if (UserType.STAFF.toString().equals(user.getUserType()))
	    ParseDAO.getCaseByStaff(user, callback);
	else
	    ParseDAO.getCaseByTenant(user, callback);
    }

    // private User getTenant()
    // {
    // return (User) getArguments().getSerializable(TENANT_OBJ_KEY);
    // }

    // public static MyCaseListFragment newInstance(User tenant)
    // {
    // MyCaseListFragment fragment = new MyCaseListFragment();
    // Bundle bundle = new Bundle();
    // bundle.putSerializable(TENANT_OBJ_KEY, tenant);
    // fragment.setArguments(bundle);
    //
    // return fragment;
    // }

}
