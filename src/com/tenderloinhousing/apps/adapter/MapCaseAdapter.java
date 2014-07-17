package com.tenderloinhousing.apps.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.model.Case;

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

		TextView tvCaseId = (TextView) v.findViewById(R.id.tvCaseId);
		TextView tvBuildingName = (TextView) v.findViewById(R.id.tvBuildingName);
		TextView tvIssueType = (TextView) v.findViewById(R.id.tvIssueType);
		
		tvCaseId.setText(inputCase.getCaseId());
		tvBuildingName.setText(inputCase.getBuilding().getName());
		tvIssueType.setText(inputCase.getIssueType());

		ArrayList<ParseFile> pictureList = inputCase.getPictures();
		if (pictureList!=null){
		
		ParseFile picture = pictureList.get(0);
			ParseImageView caseImage = (ParseImageView) v.findViewById(R.id.ivCaseImg);
		    if (picture != null) {
		    	caseImage.setParseFile(picture);
		    	caseImage.loadInBackground(new GetDataCallback() {
		            @Override
		            public void done(byte[] data, ParseException e) {
		            }
		        });
		    }
		}

		return v;
	}

}
