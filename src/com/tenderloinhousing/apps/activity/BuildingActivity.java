package com.tenderloinhousing.apps.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.parse.ParseImageView;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.fragment.BuildingCaseListFragment;
import com.tenderloinhousing.apps.helper.BuildingList;
import com.tenderloinhousing.apps.model.Building;

public class BuildingActivity extends BaseFragmentActivity implements BuildingCaseListFragment.OnCaseLoadedListener
{
    BuildingCaseListFragment caseListFragment;
    private ParseImageView ivBuildingImage;
    private TextView tvBuildingName;
    private TextView tvAddress;
    private TextView tvCount;
    Building building;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	setContentView(R.layout.activity_building);

	String buildingId = (String) getIntent().getSerializableExtra(BUILDING_ID_KEY);
	building =  BuildingList.getBuildingById(buildingId);
	populateBuildingHeader(building);

	displayCaseListFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
	getMenuInflater().inflate(R.menu.menu_report, menu);
	getMenuInflater().inflate(R.menu.menu_explore, menu);
	return true;
    }
    
    // Respond to ActionBar icon click
    public boolean onOptionsItemSelected(MenuItem item)
    {
	switch (item.getItemId())
	{
	case R.id.miReport:
	    doReport();
	    return true;
	case R.id.miExplore:
	    doExplore();
	    return true;
	default:
	    return super.onOptionsItemSelected(item);
	}
    }
    
    private void doReport()
    {
	//Use DispatchActivity to guard the gate to CaseActivity and prompt for sign in
	Intent intent = new Intent(this, CreateCaseDispatchActivity.class);
	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK	| Intent.FLAG_ACTIVITY_NEW_TASK);	
	startActivity(intent);
    }

    private void doExplore()
    {
	Intent intent = new Intent(this, MapActivity.class);	
	startActivity(intent);
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
	ivBuildingImage.loadInBackground();	   

	tvBuildingName = (TextView) findViewById(R.id.tvBuildingName);
	tvBuildingName.setText(building.getName());

	tvAddress = (TextView) findViewById(R.id.tvAddress);
	tvAddress.setText(building.getAddress());
	
	tvCount = (TextView) findViewById(R.id.tvCount);
	
    }

    @Override
    public void onCaseLoaded(int caseCount)
    {
	tvCount.setText(caseCount +" Violoations");
	
    }

}
