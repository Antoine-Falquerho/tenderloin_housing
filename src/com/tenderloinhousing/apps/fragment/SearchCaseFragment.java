package com.tenderloinhousing.apps.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.model.Case;

public class SearchCaseFragment extends Fragment implements IConstants
{
    EditText etCaseId;
    String caseId;

    @Override
    public void onAttach(Activity activity)
    {
	super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
	View view = inflater.inflate(R.layout.fragment_search_case, parent, false);
	etCaseId = (EditText) view.findViewById(R.id.etCaseId);
	etCaseId.setOnEditorActionListener(getOnEditorActionListener());
	return view;
    }

    private OnEditorActionListener getOnEditorActionListener()
    {
	return new OnEditorActionListener()
	{
	    @Override
	    public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
	    {
		int result = actionId & EditorInfo.IME_MASK_ACTION;
		switch (result)
    		{
        		case EditorInfo.IME_ACTION_DONE:
        		    caseId = etCaseId.getText().toString();
        		    searchAndDisplay(caseId);
        		    break;
        		default:
        		    caseId = etCaseId.getText().toString();
        		    searchAndDisplay(caseId);
        		    break;
		}
		return false;
	    }
	};
    }

    public void searchAndDisplay(String caseId)
    {
	ParseDAO.getCaseById(caseId, new GetCallback<Case>()
	{
	    @Override
	    public void done(Case foundCase, ParseException e)
	    {
		hideProgressBar();
		
		if (e == null)
		{
		    if (foundCase != null)
		    {
			Log.d(DEBUG, " foundCase " + foundCase.getBuilding().getAddress());
			loadCaseDetailFragment(foundCase);
		    }
		}
		else
		{
		    Toast.makeText(getActivity(), "No case with that id", Toast.LENGTH_LONG).show();
		    Log.d(ERROR, "Error: " + e.getMessage());
		}

	    }
	});
    }

    public void loadCaseDetailFragment(Case foundCase)
    {
	Bundle bundle = new Bundle();
	bundle.putSerializable(CASE_KEY, foundCase);
	CaseDetailsFragment detailsFragment = CaseDetailsFragment.newInstance(bundle);

	getActivity().getSupportFragmentManager()
		.beginTransaction()
		.add(R.id.flDetailContainer, detailsFragment)
		.commit();
	showProgressBar();
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
