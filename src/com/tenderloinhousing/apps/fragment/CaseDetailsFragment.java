package com.tenderloinhousing.apps.fragment;

import com.tenderloinhousing.apps.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CaseDetailsFragment extends Fragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("DEBUG", "onCreateView View !!!!!");
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_case_details, container, false);
		TextView tvFullName = (TextView)container.findViewById(R.id.tvFullName);
		TextView tvLanguageSpoken = (TextView)container.findViewById(R.id.tvLanguageSpoken);
		TextView tvEmail = (TextView)container.findViewById(R.id.tvEmail_);
		TextView tvPhone = (TextView)container.findViewById(R.id.tvPhone);
		TextView tvDesc = (TextView)container.findViewById(R.id.tvViolDesc);
		 Log.d("DEBUG", "CaseDetailsFragment#onCreateView View");
		return view;
	}

	public static CaseDetailsFragment newInstance(Bundle args) {
		CaseDetailsFragment fragment = new CaseDetailsFragment();   	
	   	fragment.setArguments(args);

	   	return fragment;
	}

}
