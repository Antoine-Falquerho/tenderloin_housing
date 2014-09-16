package com.tenderloinhousing.apps.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.model.User;

public class StaffArrayAdapter extends ArrayAdapter<User> implements IConstants
{
    Context context;
    User staff;
    ListView lvStaffList; 
    TextView tvStaffName;

    public StaffArrayAdapter(Context context, List<User> staff)
    {
	super(context, 0, staff);
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
	    view = inflator.inflate(R.layout.staff_list_item, parent, false);
	}

	staff = getItem(position);
	tvStaffName = (TextView) view.findViewById(R.id.tvStaffName);
	tvStaffName.setText(staff.getName());   
	view.setTag(staff);
	return view;
    }  
 
}
