package com.tenderloinhousing.apps.fragment;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import com.parse.FindCallback;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.model.Building;
import com.tenderloinhousing.apps.model.Case;

public class BuildingCaseListFragment extends CaseListBaseFragment
{
    protected OnCaseLoadedListener listener;
   
   @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);	
	
	ActionBar actionBar = getActivity().getActionBar();
	actionBar.setHomeButtonEnabled(true);
	actionBar.setDisplayHomeAsUpEnabled(true);
	actionBar.setTitle(R.string.title_activity_building);
	
    }  
   
   public interface OnCaseLoadedListener
   {
	public void onCaseLoaded(int caseCount);
   }
   
   @Override
   public void onAttach(Activity activity)
   {
	super.onAttach(activity);
	
//	if(activity instanceof OnCaseLoadedListener)
//	{
//	    listener = (OnCaseLoadedListener) activity;
//	}
//	else
//	{
//	    throw new ClassCastException(activity.toString() + " must implement BuildingCaseListFragment.OnCaseLoadedListener.");
//	}
   }

    @Override
    public void loadCases(FindCallback<Case> callback)
    {
	ParseDAO.getCaseByBuilding(getBuilding(), callback);
    }

    private Building getBuilding()
    {
	return (Building) getArguments().getSerializable(BUILDING_OBJ_KEY);
    }
    
    public static BuildingCaseListFragment newInstance(Building building)
    {
	BuildingCaseListFragment fragment = new BuildingCaseListFragment();
	Bundle bundle = new Bundle();
	bundle.putSerializable(BUILDING_OBJ_KEY, building);
	fragment.setArguments(bundle);

	return fragment;
    }
 
}
