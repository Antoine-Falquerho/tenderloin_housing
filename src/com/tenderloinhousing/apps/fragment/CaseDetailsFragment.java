package com.tenderloinhousing.apps.fragment;

import java.util.ArrayList;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.adapter.CasePictureAdatper;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.constant.UserType;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.fragment.StaffListDialogFragment.ChooseStaffDialogListener;
import com.tenderloinhousing.apps.helper.CommonUtil;
import com.tenderloinhousing.apps.model.Case;
import com.tenderloinhousing.apps.model.User;

public class CaseDetailsFragment extends Fragment implements IConstants, ChooseStaffDialogListener // listen to staff chooser dialog and assign the case to the staff
{
    private Case myCase;
    private User user;
    private TextView tvFullName;
    private TextView tvLanguageSpoken;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvDesc;
    private TextView tvViolationType;
    private TextView tvMulti;
    private TextView tvUnit;
    private ParseImageView ivBuildingImage;
    private TextView tvBuildingName;
    private TextView tvAddress;
    private TextView tvCaseNumber;
    private TextView tvCaseStatus;
    private TextView tvCaseManager;
    private ImageView ivPhoto;
    private LinearLayout llSuccess;
    private LinearLayout photoContainer;
    private Button btnShare;
    private Button btnEdit;
    private Button btnAssign;
    private CasePictureAdatper casePictureAdapter;
    private TextView tvSubmitDate;
    private boolean isNewCase;
   
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	myCase = (Case) getArguments().getSerializable(CASE_KEY);
	Log.d("DEBUG", getArguments() + "-------");
	if ((Boolean) getArguments().containsKey(IS_NEW_CASE_KEY))
	{
	    isNewCase = (Boolean) getArguments().getSerializable(IS_NEW_CASE_KEY);
	}
	else
	{
	    isNewCase = false;
	}

	ActionBar actionBar = getActivity().getActionBar();
	actionBar.setHomeButtonEnabled(true);
	actionBar.setDisplayHomeAsUpEnabled(true);
	actionBar.setTitle(R.string.title_activity_case_management);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState)
    {
	View view = inflater.inflate(R.layout.fragment_case_details, container,
		false);
	user = (User) myCase.getTenant();

	Log.d("DEBUG", myCase.getCaseId() + "------");
	tvFullName = (TextView) view.findViewById(R.id.tvFullName);
	tvFullName.setText(user.getName());

	tvLanguageSpoken = (TextView) view.findViewById(R.id.tvLanguageSpoken);
	tvLanguageSpoken.setText(((User) user).getLanguage());

	tvEmail = (TextView) view.findViewById(R.id.tvEmail);
	tvEmail.setText(user.getEmail());

	tvPhone = (TextView) view.findViewById(R.id.tvPhone);
	tvPhone.setText(((User) user).getPhone());

	tvDesc = (TextView) view.findViewById(R.id.tvViolDesc);
	tvDesc.setText(myCase.getDescription());

	tvUnit = (TextView) view.findViewById(R.id.tvUnit);
	tvUnit.setText(myCase.getUnit());

	tvViolationType = (TextView) view.findViewById(R.id.tvViolationType);
	tvViolationType.setText(myCase.getIssueType());

	tvMulti = (TextView) view.findViewById(R.id.tvMulti);
	tvMulti.setText(myCase.getIsMultiUnitPetition() ? "Yes" : "No");

	tvBuildingName = (TextView) view.findViewById(R.id.tvBuildingName);
	tvBuildingName.setText(myCase.getBuilding().getName());

	tvAddress = (TextView) view.findViewById(R.id.tvAddress);
	tvAddress.setText(myCase.getBuilding().getAddress());

	tvCaseNumber = (TextView) view.findViewById(R.id.tvCaseNumber);
	tvCaseNumber.setText(myCase.getCaseId());

	tvCaseStatus = (TextView) view.findViewById(R.id.tvCaseStatus);
	tvCaseStatus.setText(myCase.getCaseStatus());

	tvCaseManager = (TextView) view.findViewById(R.id.tvCaseManager);
	tvCaseManager.setText(myCase.getStaff()==null?"":((User)myCase.getStaff()).getName());
	
	tvSubmitDate = (TextView) view.findViewById(R.id.tvSubmitDate);
	tvSubmitDate.setText(CommonUtil.getStringFromDate(myCase.getCreatedAt()));

	ivBuildingImage = (ParseImageView) view.findViewById(R.id.ivBuildingImage);
	ivBuildingImage.setParseFile(myCase.getBuilding().getImage());
	ivBuildingImage.loadInBackground();

	photoContainer = (LinearLayout) view.findViewById(R.id.photoContainer);

	llSuccess = (LinearLayout) view.findViewById(R.id.llSuccess);
	if (isNewCase)
	{
	    llSuccess.setVisibility(1);
	}
	setPictures();

	// Action Buttons
	btnShare = (Button) view.findViewById(R.id.btnShare);
	btnEdit = (Button) view.findViewById(R.id.btnEdit);
	btnAssign = (Button) view.findViewById(R.id.btnAssign);
	
	//If current user is not Staff, remove Assign button
	 User user = (User) ParseUser.getCurrentUser();
	if(!UserType.STAFF.toString().equals( user.getUserType()))
	    //((ViewManager)view).removeView(btnAssign);
	    btnAssign.setVisibility(View.INVISIBLE);
	 
	setButtons();
	return view;
    }

    private void setButtons()
    {
	// Share case by email
	btnShare.setOnClickListener(new OnClickListener()
	{

	    @Override
	    public void onClick(View v)
	    {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/html");
		sharingIntent.putExtra(
			android.content.Intent.EXTRA_TEXT,
			Html.fromHtml("<h1> Case #" + myCase.getCaseId() + "</h1>"
				+ "<p>Hotel Name: " + myCase.getBuilding().getName() + "</p>"
				+ "<p>Unit: " + myCase.getUnit() + "</p>"
				+ "<p>Issue type: " + myCase.getIssueType() + "</p>"
				+ "<p>" + myCase.getDescription() + "</p>"));
		startActivity(Intent
			.createChooser(sharingIntent, "Share using"));
	    }
	});

	// Edit case
	btnEdit.setOnClickListener(new OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		FragmentTransaction transaction = getFragmentManager()
			.beginTransaction();

		Bundle bundle = new Bundle();
		bundle.putSerializable(CASE_KEY, myCase);
		CaseFragment caseFragment = CaseFragment.newInstance(bundle);

		transaction.setCustomAnimations(R.anim.slide_down_in,
			R.anim.slide_down_out);
		transaction.replace(R.id.flCase, caseFragment);
		transaction.commit();
	    }
	});

	// Assign case to staff
	btnAssign.setOnClickListener(new OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		// show Staff list dialog window
		StaffListDialogFragment staffListDialogFragment = new StaffListDialogFragment().newInstance(CaseDetailsFragment.this);
		staffListDialogFragment.show(getFragmentManager(), "fragment_staff_list");
	    }
	});
    }

    private void setPictures()
    {
	try
	{
	    // Convert pictures to Bitmap
	    ArrayList<ParseFile> pictureList = myCase.getPictures();
	    if (pictureList != null && !pictureList.isEmpty())
	    {
		photoContainer.removeAllViews();
		for (ParseFile picture : pictureList)
		{
		    byte[] byteArray = picture.getData();
		    Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

		    // Add image to scrolling view
		    ImageView imageView = new ImageView(getActivity().getApplicationContext());
		    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(220, 220);
		    layoutParams.setMargins(10, 5, 10, 5);
		    imageView.setLayoutParams(layoutParams);
		    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		    imageView.setImageBitmap(bmp);

		    photoContainer.addView(imageView);
		}
	    }
	}
	catch (ParseException e)
	{
	    e.printStackTrace();
	    Log.d(ERROR, "load image failure : " + e.getMessage());
	}
    }

    public static CaseDetailsFragment newInstance(Bundle args)
    {
	CaseDetailsFragment fragment = new CaseDetailsFragment();
	fragment.setArguments(args);

	return fragment;
    }

    //Listening ChooseStaffDialog. Once staff is chosen and the dialog window closes, assign the case to this staff
    @Override
    public void onChooseStaff(final User staff)
    {
	myCase.setStaff(staff);
	ParseDAO.saveCase(myCase, new SaveCallback()
	{
	    @Override
	    public void done(ParseException e)
	    {
		if (e == null)
		{
		    Toast.makeText(getActivity(), "Case is assigned to " + staff.getName() + " successfully. ", Toast.LENGTH_LONG).show();
		    tvCaseManager.setText(staff.getName());
		    // getActivity().finish();

		}
		else
		{
		    Toast.makeText(getActivity(), "Remote server call failed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
		    Log.d(ERROR, "assign case failure : " + e.getMessage());
		}
	    }
	});

    }

}
