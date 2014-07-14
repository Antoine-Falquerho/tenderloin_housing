package com.tenderloinhousing.apps.adapter;

import java.util.List;

import com.tenderloinhousing.apps.model.Case;
import com.tenderloinhousing.apps.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MapCaseAdapter extends ArrayAdapter<Case> {
	
	public MapCaseAdapter(Context context, List<Case> cases) {
		super(context, 0, cases);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Case inputCase = getItem(position);

		View v;
		if (convertView != null) {
			v = convertView;
		} else {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.case_list_item, parent, false);
		}

		ImageView ivProfileImg = (ImageView) v.findViewById(R.id.ivCaseImg);
		TextView tvCaseId = (TextView) v.findViewById(R.id.tvCaseId);
		TextView tvCaseStatus = (TextView) v.findViewById(R.id.tvCaseStatus);
//		TextView tvIssueType = (TextView) v.findViewById(R.id.tvIssueType);
		

//		ivProfileImg.setImageResource(android.R.color.transparent);
		
		tvCaseId.setText(inputCase.getCaseId());
		tvCaseStatus.setText(inputCase.getCaseId());
//		tvIssueType.setText(inputCase.getIssueType());
//		tvDescription.setText(inputCase.getDescription());

		return v;
	}

}
