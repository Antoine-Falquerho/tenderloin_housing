package com.tenderloinhousing.apps.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    protected long maxId = 0;
    protected long sinceId = 0;
    protected ListView lvCaseList;
    protected ArrayList<Case> caseList = new ArrayList<Case>();
    protected ArrayAdapter<Case> caseListAdapter;
    protected OnItemSelectedListener listener;
    protected FindCallback<Case> callback;

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
    }

    private OnItemClickListener getOnItemClickListener()
    {
	return new OnItemClickListener()
	{
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	    {
		String caseId = ((TextView) view.findViewById(R.id.tvCaseId)).getText().toString();
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
	ParseDAO.getCaseById(caseId, new GetCallback<Case>()
	{
	    @Override
	    public void done(Case foundCase, ParseException e)
	    {
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

	return view;
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

    public abstract void loadCases(FindCallback<Case> callback);

}
