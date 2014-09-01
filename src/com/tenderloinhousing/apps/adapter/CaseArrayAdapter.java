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

import com.makeramen.RoundedImageView;
import com.parse.ParseFile;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.constant.CaseStatus;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.helper.CommonUtil;
import com.tenderloinhousing.apps.model.Case;
import com.tenderloinhousing.apps.model.User;

public class CaseArrayAdapter extends ArrayAdapter<Case> implements IConstants
{
    Context context;
    Case inputCase;
    ListView lvCaseList;
    RoundedImageView caseImage;
    TextView tvBuildingName;
    TextView tvIssueType;
    TextView tvCaseId;
    TextView tvCaseStatus;
    TextView tvCaseManager;

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
	caseImage = (RoundedImageView) view.findViewById(R.id.ivCaseImg);
	tvCaseStatus= (TextView) view.findViewById(R.id.tvCaseStatus);
	tvCaseManager = (TextView) view.findViewById(R.id.tvCaseManager);

	tvCaseId.setText("Case #"+inputCase.getCaseId());
	tvBuildingName.setText(inputCase.getBuilding().getName());
	tvIssueType.setText(inputCase.getIssueType());
	tvCaseManager.setText(inputCase.getStaff()==null ? ": <NO ONE>" : ": " + ((User)inputCase.getStaff()).getName());
	
	if(CaseStatus.CREATED.toString().equals(inputCase.getCaseStatus()))
	{
        	String timeAgo = CommonUtil.getRelativeTimeAgo(inputCase.getCreatedAt());
        	tvCaseStatus.setText(inputCase.getCaseStatus() + "  " +  timeAgo);	
	}
	else
	    tvCaseStatus.setText(inputCase.getCaseStatus());

	styleCaseStatus();
	
	loadBuildingImage();

	return view;
    } 

    private void styleCaseStatus()
    {
	if(CaseStatus.CREATED.toString().equals(inputCase.getCaseStatus()))
	    tvCaseStatus.setBackground(context.getResources().getDrawable(R.drawable.case_status_created_shape));
	else if(CaseStatus.IN_REVIEW.toString().equals(inputCase.getCaseStatus()))
	    tvCaseStatus.setBackground(context.getResources().getDrawable(R.drawable.case_status_inreview_shape));
	else if(CaseStatus.CLOSED.toString().equals(inputCase.getCaseStatus()))
	    tvCaseStatus.setBackground(context.getResources().getDrawable(R.drawable.case_status_closed_shape));
    }

    private void loadBuildingImage()
    {
	ParseFile pictureFile = null;
	ArrayList<ParseFile> pictureList = inputCase.getPictures();

	if (pictureList != null && !pictureList.isEmpty())
	    pictureFile = pictureList.get(0);
	else
	    pictureFile = inputCase.getBuilding().getImage(); // If the case doesn't have any pictures, display building picture

	if (pictureFile != null)
	{
//	    caseImage.setParseFile(pictureFile);
//	    caseImage.loadInBackground();	    

	    caseImage.setImageBitmap(CommonUtil.convertParseImageFile(pictureFile));
	}
    }  
}
