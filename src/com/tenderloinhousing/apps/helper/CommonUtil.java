package com.tenderloinhousing.apps.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.AdapterView.OnItemSelectedListener;

public class CommonUtil
{
   public static SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
    
    public static Date getDateFromString(String dateString)
    {	
	sf.setLenient(true);
	Date date = null;
	try
	{
	    date = sf.parse(dateString);
	}
	catch (ParseException e)
	{
	    e.printStackTrace();
	}

	return date;
    }
    
    public static String getStringFromDate(Date date)
    {
	return sf.format(date);
    }


    public static boolean isNetworkConnected(Context context)
    {
	ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	return (cm.getActiveNetworkInfo() != null) && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static String getJsonErrorMsg(JSONObject  errorResponse)
    {
	String msg ="";
	
	try
	{
	    JSONObject jsonObject = (JSONObject) errorResponse.getJSONArray("errors").get(0);
	    msg = jsonObject.getString("message");
	}
	catch (JSONException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return msg;
    }
    
    //Methods for Spinner
    public static OnItemSelectedListener getOnItemSelectedListener()
    {
	return new OnItemSelectedListener()
	{
	    @Override
	    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
	    {
		//String value = parent.getItemAtPosition(position).toString();
		String value =((Spinner) parent).getSelectedItem().toString();
		setSpinnerToValue(((Spinner) parent), value);
	    }

	    @Override
	    public void onNothingSelected(AdapterView<?> parent)
	    {
		// TODO Auto-generated method stub

	    }

	};
    }
    
    //Methods for Spinner
    public static void setSpinnerToValue(Spinner spinner, String value)
    {
	int index = 0;
	SpinnerAdapter adapter = spinner.getAdapter();
	for (int i = 0; i < adapter.getCount(); i++)
	{
	    if (adapter.getItem(i).equals(value))
	    {
		index = i;
	    }
	}
	spinner.setSelection(index);
    }
}
