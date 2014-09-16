package com.tenderloinhousing.apps.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.adapter.StaffArrayAdapter;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.constant.UserType;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.model.User;

public class StaffListDialogFragment extends DialogFragment implements IConstants
{
    protected ListView lvStaffList;
    protected ArrayList<User> staffList = new ArrayList<User>();
    protected ArrayAdapter<User> staffListAdapter;
    public static final int STAFF_DIALOG_FRAGMENT=1; 

    // Declare this listener in order to pass Staff object to parent (CaseDetailsFragment) to assign case
    public interface ChooseStaffDialogListener
    {
	void onChooseStaff(User staff);
    }

    public StaffListDialogFragment()
    {
	// Empty constructor required for DialogFragment
    }

    public static StaffListDialogFragment newInstance(ChooseStaffDialogListener listener)
    {
	StaffListDialogFragment dialogFragment = new StaffListDialogFragment(); 
	dialogFragment.setTargetFragment((Fragment) listener, /* requestCode */STAFF_DIALOG_FRAGMENT);
	return dialogFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	staffListAdapter = new StaffArrayAdapter(getActivity(), staffList);
	// load all staff from DB to feed adapter
	ParseDAO.getAll(User.class, getFindCallBack());
	showProgressBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
	View view = inflater.inflate(R.layout.fragment_staff_list, container);
	lvStaffList = (ListView) view.findViewById(R.id.lvStaffList);
	lvStaffList.setAdapter(staffListAdapter);
	lvStaffList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	lvStaffList.setOnItemClickListener(getOnItemClickListener());
	getDialog().setTitle("THC Staff Members");

	return view;
    }

    // On clicking each list item of staff list, assign the case to this staff
    private OnItemClickListener getOnItemClickListener()
    {
	return new OnItemClickListener()
	{
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    {
		// Get Staff object attached to this line item
		User staff = (User) view.getTag();

		// notify parent (CaseDetailsFragment) through the listener. TargetFragment implements the listener
		ChooseStaffDialogListener listener = (ChooseStaffDialogListener) getTargetFragment();
		listener.onChooseStaff(staff);

		// dismiss the dialog window
		dismiss();
	    }
	};
    }

    private FindCallback<User> getFindCallBack()
    {
	return new FindCallback<User>()
	{
	    @Override
	    public void done(List<User> staffList, com.parse.ParseException e)
	    {
		hideProgressBar();
		if (e == null)
		{
		    staffListAdapter.clear();
		    for(User staff: staffList)
		    {
			if(UserType.STAFF.toString().equals( staff.getUserType()))
				staffListAdapter.add(staff);
		    }
		}
		else
		{
		    Log.d(ERROR, "Error loading all staff" + e.getMessage());
		    Toast.makeText(getActivity(), "Error loading all staff.", Toast.LENGTH_SHORT).show();
		}
	    }
	};
    }

    // Should be called manually when an async task has started
    public void showProgressBar()
    {
	getActivity().setProgressBarIndeterminateVisibility(true);
    }

    // Should be called when an async task has finished
    public void hideProgressBar()
    {
	getActivity().setProgressBarIndeterminateVisibility(false);
    }
}
