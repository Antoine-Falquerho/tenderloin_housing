package com.tenderloinhousing.apps.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ConnectivityManager;

public class CommonUtil
{
    public static SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

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
	return DATE_FORMAT.format(date);
    }

    public static boolean isNetworkConnected(Context context)
    {
	ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	return (cm.getActiveNetworkInfo() != null) && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static String getJsonErrorMsg(JSONObject errorResponse)
    {
	String msg = "";

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

}
