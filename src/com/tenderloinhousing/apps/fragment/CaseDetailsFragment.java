package com.tenderloinhousing.apps.fragment;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.adapter.CasePictureAdatper;
import com.tenderloinhousing.apps.model.Case;
import com.tenderloinhousing.apps.model.User;

public class CaseDetailsFragment extends Fragment {
	private static Case myCase;
	private ParseUser user;
	private TextView tvFullName;
	private TextView tvLanguageSpoken;
	private TextView tvEmail;
	private TextView tvPhone;
	private TextView tvDesc;
	private TextView tvUnit;
//	private GridView gdView;
	private Gallery glyView;
	
	private Button btnShare;
	
	private CasePictureAdatper casePictureAdapter;

	
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
//		gdView = (GridView)view.findViewById(R.id.gridview);
		glyView = (Gallery) view.findViewById(R.id.gallery);
		
		
		ArrayList<ParseFile> pictures = myCase.getPictures();
		
		if (pictures!=null) {
			Log.d("parse file", "## size:" + pictures.size());			
			if (pictures.size() != 0) {
				Log.d("parse file", "## pictures:" + pictures.get(0).toString());			
			}
			
		} else {
			Log.d("parse file", "## pictures null");
		}
		
		
		casePictureAdapter = new CasePictureAdatper(getActivity(), pictures);
//		gdView.setAdapter(casePictureAdapter);
		
//		casePicturePageAdapter = new CasePicturePageAdatper(getActivity(), pictures);
		glyView.setAdapter(casePictureAdapter);
		
		btnShare = (Button)view.findViewById(R.id.btnShare);
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
				startActivity(Intent.createChooser(sharingIntent,"Share using"));
				
			}
		});
		
		return view;
	}

	public static CaseDetailsFragment newInstance(Bundle args, Case myCaseArg) {		
		CaseDetailsFragment fragment = new CaseDetailsFragment();   	
	   	fragment.setArguments(args);
	   	myCase = myCaseArg;	   	

	   	return fragment;
	}
	
	

}