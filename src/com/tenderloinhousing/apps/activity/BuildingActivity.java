package com.tenderloinhousing.apps.activity;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.fragment.BuildingCaseListFragment;
import com.tenderloinhousing.apps.helper.CommonUtil;
import com.tenderloinhousing.apps.model.Building;
import com.tenderloinhousing.apps.model.User;

public class BuildingActivity extends BaseFragmentActivity
{
    BuildingCaseListFragment caseListFragment;
    private ImageView ivBuildingImage;
    private TextView tvBuildingName;
    private TextView tvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	setContentView(R.layout.activity_building);

	Building building = (Building) getIntent().getSerializableExtra(BUILDING_OBJ_KEY);
	populateBuildingHeader(building);

	displayCaseListFragment();
    }

    private void displayCaseListFragment()
    {
	FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

	//caseListFragment = BuildingCaseListFragment.newInstance(screenName);
	transaction.replace(R.id.flCase, caseListFragment);

	transaction.commit();
    }

    private void loadProfileInfo(String screenName)
    {
	showProgressBar();

//	ParseDAO.getUser(screenName, new JsonHttpResponseHandler()
//	{
//	    @Override
//	    public void onSuccess(JSONObject json)
//	    {
//		User user = User.fromJson(json);
//		populateProfileHeader(user);
//	    }
//
//	    @Override
//	    public void onFailure(java.lang.Throwable e, org.json.JSONObject errorResponse)
//	    {
//		BuildingActivity.this.hideProgressBar();
//
//		String msg = CommonUtil.getJsonErrorMsg(errorResponse);
//		Toast.makeText(BuildingActivity.this, "Remote server call failed. " + msg, Toast.LENGTH_SHORT).show();
//
//		Log.d("ERROR", "loadProfileInfo REST call failure : " + e.getMessage() + "JSON error message: " + msg);
//	    }
//	});
    }

    public void populateBuildingHeader(Building building)
    {
	ivBuildingImage = (ImageView) findViewById(R.id.ivBuildingImage);
	// ImageLoader.getInstance().displayImage(building.getProfileImageUrl(), ivBuildingImage);

	tvBuildingName = (TextView) findViewById(R.id.tvBuildingName);
	tvBuildingName.setText(building.getName());

	tvAddress = (TextView) findViewById(R.id.tvAddress);
	tvAddress.setText(building.getAddress());

    }

}
