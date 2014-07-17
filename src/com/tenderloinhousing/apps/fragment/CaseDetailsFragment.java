package com.tenderloinhousing.apps.fragment;

import java.util.ArrayList;

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
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.adapter.CasePictureAdatper;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.helper.CommonUtil;
import com.tenderloinhousing.apps.model.Case;
import com.tenderloinhousing.apps.model.User;

public class CaseDetailsFragment extends Fragment implements IConstants {
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
	private ImageView ivBuildingImage;
	private TextView tvBuildingName;
	private TextView tvAddress;
	private TextView tvCaseNumber;
	private TextView tvCaseStatus;
	private ImageView ivPhoto;
	private LinearLayout photoContainer;
	private Button btnShare;
	private Button btnEdit;	
	private CasePictureAdatper casePictureAdapter;
	private TextView tvSubmitDate;

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myCase = (Case) getArguments().getSerializable(CASE_KEY);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_case_details, container, false);
		user = (User) myCase.getTenant();		
		try {
			user.fetchIfNeeded();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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

	tvSubmitDate = (TextView) view.findViewById(R.id.tvSubmitDate);
	tvSubmitDate.setText(CommonUtil.getStringFromDate(myCase.getCreatedAt()));

	ivBuildingImage = (ImageView) view.findViewById(R.id.ivBuildingImage);
	// ivBuildingImage.setText(myCase.get); //TODO

	photoContainer = (LinearLayout) view.findViewById(R.id.photoContainer);
	setPictures();

	// ArrayList<ParseFile> pictures = myCase.getPictures();
	//
	// if (pictures!=null) {
	// Log.d("parse file", "## size:" + pictures.size());
	// if (pictures.size() != 0) {
	// Log.d("parse file", "## pictures:" + pictures.get(0).toString());
	// }
	//
	// } else {
	// Log.d("parse file", "## pictures null");
	// }
	// casePictureAdapter = new CasePictureAdatper(getActivity(), pictures);
	// glyView.setAdapter(casePictureAdapter);

    		
	// Action Buttons
	btnShare = (Button) view.findViewById(R.id.btnShare);
	btnEdit = (Button) view.findViewById(R.id.btnEdit);
	setButtons();
	return view;
    }

   private void setButtons()
   {
	btnShare.setOnClickListener(new OnClickListener() {

	    @Override
		public void onClick(View v) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/html");
		sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
			Html.fromHtml("<h1> Case id #" + myCase.getCaseId() + "</h1>" +
				"<p>Unit" + myCase.getUnit() + "</p>" +
				"<p>Issue type" + myCase.getIssueType() + "</p>" +
				"<p>" + myCase.getDescription() + "</p>"));
		startActivity(Intent.createChooser(sharingIntent, "Share using"));
	    }
	});

	btnEdit.setOnClickListener(new OnClickListener() {		
	    @Override
		public void onClick(View v) {			
		FragmentTransaction transaction = getFragmentManager().beginTransaction();		

		Bundle bundle = new Bundle();
		bundle.putSerializable(CASE_KEY, myCase);
		CaseFragment caseFragment = CaseFragment.newInstance(bundle);
		
		transaction.setCustomAnimations(R.anim.slide_down_in, R.anim.slide_down_out);
		transaction.replace(R.id.flCase, caseFragment);	
		transaction.commit();			
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
		for (ParseFile picture : pictureList)
		{
		    byte[] byteArray = picture.getData();
		    Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

		    // Add image to srolling view
		    ImageView imageView = new ImageView(getActivity().getApplicationContext());
		    imageView.setLayoutParams(new LayoutParams(220, 220));
		    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		    imageView.setImageBitmap(bmp);

		    photoContainer.addView(imageView);
		}
	}
	catch (ParseException e)
	{
	    e.printStackTrace();
	    Log.d(ERROR, "createCase failure : " + e.getMessage());
	}
    }

	public static CaseDetailsFragment newInstance(Bundle args) {		
		CaseDetailsFragment fragment = new CaseDetailsFragment();   	
	   	fragment.setArguments(args);
	   	return fragment;
	}
	
	

}
