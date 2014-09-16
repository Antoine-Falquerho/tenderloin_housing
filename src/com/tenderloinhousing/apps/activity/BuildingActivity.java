package com.tenderloinhousing.apps.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseImageView;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.fragment.BuildingCaseListFragment;
import com.tenderloinhousing.apps.helper.BuildingList;
import com.tenderloinhousing.apps.model.Building;

public class BuildingActivity extends BaseFragmentActivity //implements BuildingCaseListFragment.OnCaseLoadedListener
{
    BuildingCaseListFragment caseListFragment;
    private ParseImageView ivBuildingImage;
    private TextView tvBuildingName;
    private TextView tvCount;
    private Button btnCreateReport;
    Building building;
    String caseCount;
    ImageView ivClose;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	setContentView(R.layout.activity_building);

	caseCount = (String) getIntent().getStringExtra(CASE_COUNT_KEY);
	String buildingId = (String) getIntent().getSerializableExtra(BUILDING_ID_KEY);
	building =  BuildingList.getBuildingById(buildingId);
	
	populateBuildingHeader(building,caseCount);

	displayCaseListFragment();
	getActionBar().setHomeButtonEnabled(true);  
	getActionBar().setDisplayHomeAsUpEnabled(true); 
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
	case android.R.id.home:
		startActivity(new Intent(getApplicationContext(), MapActivity.class));
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

    public void populateBuildingHeader(Building building, String caseCount)
    {
	ivBuildingImage = (ParseImageView) findViewById(R.id.ivBuildingImage);	
	ivBuildingImage.setParseFile(building.getImage());
	ivBuildingImage.loadInBackground();	   

	tvBuildingName = (TextView) findViewById(R.id.tvBuildingName);
	tvBuildingName.setText(building.getName());
	
	tvCount = (TextView) findViewById(R.id.tvCount);
	tvCount.setText("Reported Violations ("  + caseCount +")");
	
	btnCreateReport = (Button) findViewById(R.id.btnCreateReport);	
	btnCreateReport.setOnClickListener(getOnClickListener());
	
	ivClose = (ImageView) findViewById(R.id.ivClose);	
	ivClose.setOnClickListener(new OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		doExplore();		
	    }	    
	});
    }

    public OnClickListener getOnClickListener()
    {
	return new OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		doReport();
	    }
	};
    }
    
//    @Override
//    public void onCaseLoaded(int caseCount)
//    {
//	tvCount.setText(caseCount +" Violoations");
//	
//    }
    }
