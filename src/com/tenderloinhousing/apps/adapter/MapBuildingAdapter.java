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
import com.tenderloinhousing.apps.model.Building;
import com.tenderloinhousing.apps.model.Case;

public class MapBuildingAdapter extends ArrayAdapter<Building> {
	
	public MapBuildingAdapter(Context context, List<Building> buildings) {
		super(context, 0, buildings);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Building building = getItem(position);

		View v;
		if (convertView != null) {
			v = convertView;
		} else {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.building_list_item, parent, false);
		}

		TextView tvBuildingName = (TextView) v.findViewById(R.id.tvBuildingName);
		TextView tvBuildingAddress = (TextView) v.findViewById(R.id.tvBuildingAddress);
		
		tvBuildingName.setText(building.getName());
		tvBuildingAddress.setText(building.getAddress());

//		ArrayList<ParseFile> pictureList = inputCase.getPictures();
//		if (pictureList!=null){
//		
//		ParseFile picture = pictureList.get(0);
//			ParseImageView caseImage = (ParseImageView) v.findViewById(R.id.ivCaseImg);
//		    if (picture != null) {
//		    	caseImage.setParseFile(picture);
//		    	caseImage.loadInBackground(new GetDataCallback() {
//		            @Override
//		            public void done(byte[] data, ParseException e) {
//		            }
//		        });
//		    }
//		}
		
		ParseImageView buildingImage = (ParseImageView) v.findViewById(R.id.ivBuildingImg);
		ParseFile picture = building.getImage(); 
		if (picture != null)
		{
			buildingImage.setParseFile(picture);
			buildingImage.loadInBackground();
		}
		v.setTag(building.getBuildingId());
		return v;
	}

}
