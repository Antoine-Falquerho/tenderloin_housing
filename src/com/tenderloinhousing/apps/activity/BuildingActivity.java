package com.tenderloinhousing.apps.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.fragment.BuildingCaseListFragment;
import com.tenderloinhousing.apps.helper.BuildingList;
import com.tenderloinhousing.apps.model.Building;

public class BuildingActivity extends BaseFragmentActivity
{
    BuildingCaseListFragment caseListFragment;
    private ParseImageView ivBuildingImage;
    private TextView tvBuildingName;
    private TextView tvAddress;
    Building building;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	setContentView(R.layout.activity_building);

	String buildingId = (String) getIntent().getSerializableExtra(BUILDING_ID_KEY);
	buildingId ="eC53xf5qDw";
	building =  BuildingList.getBuildingById(buildingId);
	populateBuildingHeader(building);

	displayCaseListFragment();
    }

    private void displayCaseListFragment()
    {
	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	
	caseListFragment = BuildingCaseListFragment.newInstance(building);
	transaction.replace(R.id.listContainer, caseListFragment);
	
	transaction.commit();
    }

    public void populateBuildingHeader(Building building)
    {
	ivBuildingImage = (ParseImageView) findViewById(R.id.ivBuildingImage);
	
	ivBuildingImage.setParseFile(building.getImage());
	ivBuildingImage.loadInBackground(new GetDataCallback() {
	            @Override
	            public void done(byte[] data, ParseException e) {	        	
	            }
	        });	   

	tvBuildingName = (TextView) findViewById(R.id.tvBuildingName);
	tvBuildingName.setText(building.getName());

	tvAddress = (TextView) findViewById(R.id.tvAddress);
	tvAddress.setText(building.getAddress());
    }

}
