package com.tenderloinhousing.apps.fragment;

import java.util.List;

import com.tenderloinhousing.apps.model.Case;

import android.os.Bundle;

public class BuildingCaseListFragment extends CaseListBaseFragment
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
   
   
//   public static BuildingCaseListFragment newInstance(boolean isNetworkAvailable)
//   {
//	BuildingCaseListFragment fragment = new BuildingCaseListFragment();	
//	fragment.setArguments(getBundle(isNetworkAvailable));
//
//	return fragment;
//   }
  
 
}
