package com.tenderloinhousing.apps.adapter;

import java.util.List;
import java.util.Random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.makeramen.RoundedImageView;
import com.parse.ParseFile;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.helper.CommonUtil;
import com.tenderloinhousing.apps.model.Building;

public class MapBuildingAdapter extends ArrayAdapter<Building>
{

    public MapBuildingAdapter(Context context, List<Building> buildings)
    {
	super(context, 0, buildings);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
	Building building = getItem(position);

	View v;
	if (convertView != null)
	{
	    v = convertView;
	}
	else
	{
	    LayoutInflater inflator = LayoutInflater.from(getContext());
	    v = inflator.inflate(R.layout.building_list_item, parent, false);
	}

	TextView tvBuildingName = (TextView) v.findViewById(R.id.tvBuildingName);
	TextView tvBuildingAddress = (TextView) v.findViewById(R.id.tvBuildingAddress);
	TextView tvCount = (TextView) v.findViewById(R.id.tvCount);

	tvBuildingName.setText(building.getName());
	tvBuildingAddress.setText(building.getAddress());

	Random r = new Random();
	int caseCount = r.nextInt(50 - 30) + 30;
	tvCount.setText(String.valueOf(caseCount) + " Violations");

	RoundedImageView buildingImage = (RoundedImageView) v.findViewById(R.id.ivBuildingImg);
	ParseFile pictureFile = building.getImage();
	if (pictureFile != null)
	{
//	    buildingImage.setParseFile(picture);
//	    buildingImage.loadInBackground();
	    buildingImage.setImageBitmap(CommonUtil.convertParseImageFile(pictureFile));
	}
	v.setTag(building.getBuildingId());
	return v;
    }

}
