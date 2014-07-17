package com.tenderloinhousing.apps.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.adapter.CaseArrayAdapter;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.helper.EndlessScrollListener;
import com.tenderloinhousing.apps.model.Case;

public abstract class CaseListBaseFragment extends Fragment implements IConstants
{
    protected long maxId = 0;
    protected long sinceId = 0;
    protected ListView lvCaseList;
    protected ArrayList<Case> tweetList = new ArrayList<Case>();
    protected ArrayAdapter<Case> itemAdapter;
    protected OnItemSelectedListener listener;
    protected String endpoint;

    public interface OnItemSelectedListener
    {
	public void onItemSelected(String link);
    }

    @Override
    public void onAttach(Activity activity)
    {
	super.onAttach(activity);
	
	if(activity instanceof OnItemSelectedListener)
	{
	    listener = (OnItemSelectedListener) activity;
	}
	else
	{
	    throw new ClassCastException(activity.toString() + " must implement CaseListBaseFragment.OnItemSelectedListener.");
	}
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	itemAdapter = new CaseArrayAdapter(getActivity(), tweetList);

	//boolean isNetworkAvaialble = getArguments().getBoolean(NETWORK_ON_FLAG);
	populateTimeline();
    }
    
    public void populateTimeline()
    {
//	if (CommonUtil.isNetworkConnected(getActivity()))
//	    REST_CLIENT.getTimeLine(maxId, getEndpoint(), getResponseHandler());
//	else
//	    itemAdapter.addAll(getSavedItems());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
	View view = inflater.inflate(R.layout.fragment_case_list, parent, false);
	lvCaseList = (ListView) view.findViewById(R.id.lvCaseList);
	lvCaseList.setAdapter(itemAdapter);
	lvCaseList.setOnScrollListener(getOnScrollListener());
	return view;
    }


//    protected JsonHttpResponseHandler getResponseHandler()
//    {
//	return new JsonHttpResponseHandler()
//	{
//	    @Override
//	    public void onSuccess(JSONArray jsonArray)
//	    {
//		((BaseFragmentActivity)getActivity()).hideProgressBar();
//		
//		if (jsonArray.length() <= 0)
//		    return;
//
//		List<Case> tweets = Case.fromJsonArray(jsonArray);
//		itemAdapter.addAll(tweets);
//		
//		Log.d("DEBUG", "adapter size = " + itemAdapter.getCount());
//
//		setCursors(jsonArray);
//
//		// Persist all tweets in DB
//		TweetDAO.saveAllItems(tweets);
//	    }
//
//	    @Override
//	    public void onFailure(java.lang.Throwable e, org.json.JSONObject errorResponse)
//	    {
//		((BaseFragmentActivity)getActivity()).hideProgressBar();
//		
//		String msg = CommonUtil.getJsonErrorMsg(errorResponse);
//		Toast.makeText(getActivity(), "Remote server call failed. " + msg, Toast.LENGTH_SHORT).show();		
//		
//		Log.d("ERROR", "getTimeline REST call failure : " + e.getMessage() + "JSON error message: " + msg);
//	    }
//	};
//    }
//
//    private void setCursors(JSONArray jsonArray)
//    {
//	try
//	{
//	    JSONObject oldestObject = (JSONObject) jsonArray.get(jsonArray.length() - 1);
//	    JSONObject newestObject = (JSONObject) jsonArray.get(0);
//
//	    Case oldestTweet = Case.fromJson(oldestObject);
//	    Case newestTweet = Case.fromJson(newestObject);
//
//	    setMaxId( oldestTweet.getTweetId() - 1);
//	    // sinceId = newestTweet.getTweetId();
//
//	    Log.d("DEBUG", "this.maxId = " + getMaxId() + ", parent.maxId = " + maxId);
//	}
//	catch (JSONException e)
//	{
//	    Log.d("ERROR", "Error in JsonHttpResponseHandler : " + e.getMessage());
//	    e.printStackTrace();
//	}
//    }

    // Respond to image grid scrolling
    protected EndlessScrollListener getOnScrollListener()
    {
	return new EndlessScrollListener()
	{
	    @Override
	    public void onLoadMore(int page, int totalItemsCount)
	    {
		// Triggered only when new data needs to be appended to the list
		//REST_CLIENT.getTimeLine(getMaxId(), getEndpoint(), getResponseHandler());
	    }
	};
    }

    public void displayNewTweet(Case newTweet, boolean isFromServer)
    {
	if (isFromServer)
	    tweetList.set(0, newTweet);
	else
	    tweetList.add(0, newTweet);

	lvCaseList.setSelection(0);
	itemAdapter.notifyDataSetChanged();
    }

    protected static Bundle getBundle(boolean isNetworkAvailable)
    {
	Bundle args = new Bundle();
	args.putBoolean(NETWORK_ON_FLAG, isNetworkAvailable);
	
	return args;
    }

 

    public String getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    protected  abstract List<Case> getSavedItems();
    
}
