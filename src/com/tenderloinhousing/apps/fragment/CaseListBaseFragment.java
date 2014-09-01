package com.tenderloinhousing.apps.fragment;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.activity.CaseActivity;
import com.tenderloinhousing.apps.adapter.CaseArrayAdapter;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.helper.EndlessScrollListener;
import com.tenderloinhousing.apps.model.Case;

public abstract class CaseListBaseFragment extends Fragment implements IConstants
{
    protected ListView lvCaseList;
    protected ArrayList<Case> caseList = new ArrayList<Case>();
    protected ArrayAdapter<Case> caseListAdapter;
    protected OnItemSelectedListener listener;
    protected FindCallback<Case> callback;
    protected EditText etCaseId;
    protected String caseId;
    protected ImageView ivRemove;

    @Override
    public void onAttach(Activity activity)
    {
	super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	caseListAdapter = new CaseArrayAdapter(getActivity(), caseList);
	loadCases(getFindCallBack());
	showProgressBar();
    }

    private OnItemClickListener getOnItemClickListener()
    {
	return new OnItemClickListener()
	{
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    {
		String caseId = ((TextView) view.findViewById(R.id.tvCaseId)).getText().toString();
		caseId =  StringUtils.substringAfter(caseId, "#");
		openCaseDetailIntent(caseId);
	    }
	};
    }

    private FindCallback<Case> getFindCallBack()
    {
	if (callback == null)
	{
	    callback = new FindCallback<Case>()
	    {
		@Override
		public void done(List<Case> caseList, com.parse.ParseException e)
		{
		    hideProgressBar();
		    if (e == null)
		    {
			caseListAdapter.clear();
			caseListAdapter.addAll(caseList);
		    }
		    else
		    {
			Log.d(ERROR, "Error loading cases" + e.getMessage());
			Toast.makeText(getActivity(), "Error loading cases.", Toast.LENGTH_SHORT).show();
		    }
		}
	    };
	}

	return callback;
    }

    //Use by search
    private void filterCasebyId(String caseId)
    {
	//caseId =  StringUtils.substringAfter(caseId, "#");
	
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
			
			List<Case> caseList = new ArrayList<Case>();			
			caseList.add(foundCase);
			caseListAdapter.clear();
			caseListAdapter.addAll(caseList);
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
	View view = inflater.inflate(R.layout.fragment_case_list, parent, false);
	lvCaseList = (ListView) view.findViewById(R.id.lvCaseList);
	lvCaseList.setAdapter(caseListAdapter);
	lvCaseList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
	lvCaseList.setOnItemClickListener(getOnItemClickListener());
	lvCaseList.setOnScrollListener(getOnScrollListener());
	
	etCaseId = (EditText) view.findViewById(R.id.etCaseId);
	etCaseId.setOnEditorActionListener(getOnEditorActionListener());
	etCaseId.addTextChangedListener(getTextChangedListener());
	
	ivRemove = (ImageView) view.findViewById(R.id.ivRemove);
	ivRemove.setOnClickListener(getOnClickListener());
	ivRemove.setVisibility(View.GONE);
	return view;
    }

    private OnClickListener getOnClickListener()
    {
	return new OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		etCaseId.setText(""); //clean up search text
		ivRemove.setVisibility(View.GONE);
		loadCases(getFindCallBack());
	    }
	};
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
        		    filterCasebyId(caseId);
        		    ivRemove.setVisibility(View.GONE);
        		    break;
        		default:
        		    caseId = etCaseId.getText().toString();
        		    filterCasebyId(caseId);
        		    ivRemove.setVisibility(View.GONE);
        		    break;
		}
		return false;
	    }
	};
    }

    private TextWatcher getTextChangedListener()
    {
	return new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// Fires right as the text is being changed (even supplies the range of text)
		    if(s.length()>0)
			ivRemove.setVisibility(View.VISIBLE);
		    else
			 ivRemove.setVisibility(View.GONE);
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// Fires right before text is changing
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			// Fires right after the text has changed
		    if(s.length()>0)
			ivRemove.setVisibility(View.VISIBLE);
		    else
			 ivRemove.setVisibility(View.GONE);
			
		}
	};
    }
    
    
//    public void searchAndDisplay(String caseId)
//    {
//	ParseDAO.getCaseById(caseId, new GetCallback<Case>()
//	{
//	    @Override
//	    public void done(Case foundCase, ParseException e)
//	    {
//		hideProgressBar();
//		
//		if (e == null)
//		{
//		    if (foundCase != null)
//		    {
//			Log.d(DEBUG, " foundCase " + foundCase.getBuilding().getAddress());
//			loadCaseDetailFragment(foundCase);
//		    }
//		}
//		else
//		{
//		    Toast.makeText(getActivity(), "No case with that id", Toast.LENGTH_LONG).show();
//		    Log.d(ERROR, "Error: " + e.getMessage());
//		}
//
//	    }
//	});
//    }

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
    
    //
    private void openCaseDetailIntent(String caseId)
    {
	Intent intent = new Intent(getActivity(), CaseActivity.class);
	intent.putExtra(CASE_ID_KEY, caseId);
	intent.putExtra(METHOD_KEY, METHOD_CODE_DETAIL);

	startActivity(intent);
    }

    // Respond to image grid scrolling
    protected EndlessScrollListener getOnScrollListener()
    {
	return new EndlessScrollListener()
	{
	    @Override
	    public void onLoadMore(int page, int totalItemsCount)
	    {
		loadCases(getFindCallBack());
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
    
    public abstract void loadCases(FindCallback<Case> callback);

}
