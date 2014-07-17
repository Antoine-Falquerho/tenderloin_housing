package com.tenderloinhousing.apps.fragment;

import java.util.List;

import android.os.Bundle;

import com.parse.FindCallback;
import com.parse.ParseUser;
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
   public List<Case> getSavedItems()
   {
	//return ParseDAO.getCaseByTenant((User) ParseUser.getCurrentUser(), new FindCallback<Case>());
       return null;
   }
   
  
//   
//   public static AllCaseListFragment newInstance(boolean isNetworkAvailable)
//   {
//       AllCaseListFragment fragment = new AllCaseListFragment();	
//	fragment.setArguments(getBundle(isNetworkAvailable));
//
//	return fragment;
//   }
}
