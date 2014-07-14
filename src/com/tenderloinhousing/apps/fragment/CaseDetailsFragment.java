package com.tenderloinhousing.apps.fragment;

import java.util.ArrayList;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.model.Case;
import com.tenderloinhousing.apps.model.User;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CaseDetailsFragment extends Fragment {
	private static Case myCase;
	private ParseUser user;
	private TextView tvFullName;
	private TextView tvLanguageSpoken;
	private TextView tvEmail;
	private TextView tvPhone;
	private TextView tvDesc;
	private TextView tvUnit;
//	private CasePictureAdatper casePictureAdapter;

	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_case_details, container, false);
		user = myCase.getTenant();		
		try {
			user.fetchIfNeeded();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.d("DEBUG", myCase.getCaseId() + "------");
		tvFullName = (TextView)view.findViewById(R.id.tvFullName);		
		tvFullName.setText(user.getUsername());
		tvLanguageSpoken = (TextView)view.findViewById(R.id.tvLanguageSpoken);
		tvLanguageSpoken.setText(((User) user).getLanguage());
		tvEmail = (TextView)view.findViewById(R.id.tvEmail_);
		tvEmail.setText(user.getEmail());
		tvPhone = (TextView)view.findViewById(R.id.tvPhone);
		tvPhone.setText(((User) user).getPhone());
		tvDesc = (TextView)view.findViewById(R.id.tvViolDesc);
		tvDesc.setText(myCase.getDescription());
		tvUnit = (TextView)view.findViewById(R.id.tvUnit);
		tvUnit.setText(myCase.getUnit());
		ArrayList<ParseFile> pictures = myCase.getPictures();
		
		return view;
	}

	public static CaseDetailsFragment newInstance(Bundle args, Case myCaseArg) {		
		CaseDetailsFragment fragment = new CaseDetailsFragment();   	
	   	fragment.setArguments(args);
	   	myCase = myCaseArg;	   	

	   	return fragment;
	}

}
