package com.tenderloinhousing.apps.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.model.Case;

public class CaseArrayAdapter extends ArrayAdapter<Case> implements IConstants
{
    Context context;
    Case inputCase;
    ListView lvCaseList;
    ParseImageView caseImage;
    TextView tvBuildingName;
    TextView tvIssueType;
    TextView tvCaseId;

    public CaseArrayAdapter(Context context, List<Case> cases)
    {
	super(context, 0, cases);
	this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
	View view;
	if (convertView != null)
	{
	    view = convertView;
	}
	else
	{
	    LayoutInflater inflator = LayoutInflater.from(getContext());
	    view = inflator.inflate(R.layout.case_list_item, parent, false);
	}

	inputCase = getItem(position);

	tvCaseId = (TextView) view.findViewById(R.id.tvCaseId);
	tvBuildingName = (TextView) view.findViewById(R.id.tvBuildingName);
	tvIssueType = (TextView) view.findViewById(R.id.tvIssueType);
	caseImage = (ParseImageView) view.findViewById(R.id.ivCaseImg);

	tvCaseId.setText(inputCase.getCaseId());
	tvBuildingName.setText(inputCase.getBuilding().getName());
	tvIssueType.setText(inputCase.getIssueType());

	loadBuildingImage();

	return view;
    }

    private void loadBuildingImage()
    {
	ParseFile picture = null;
	ArrayList<ParseFile> pictureList = inputCase.getPictures();

	if (pictureList != null && !pictureList.isEmpty())
	    picture = pictureList.get(0);
	else
	    picture = inputCase.getBuilding().getImage(); // If the case doesn't have any pictures, display building picture

	if (picture != null)
	{
	    caseImage.setParseFile(picture);
	    caseImage.loadInBackground(new GetDataCallback()
                                	    {
                                		@Override
                                		public void done(byte[] data, ParseException e){}
                                	    });
	}
    }  
}
