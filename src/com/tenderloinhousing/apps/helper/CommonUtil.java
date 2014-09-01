package com.tenderloinhousing.apps.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.text.format.DateUtils;

import com.parse.ParseFile;

public class CommonUtil
{
    public static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    public static Date getDateFromString(String dateString)
    {
	DATE_TIME_FORMAT.setLenient(true);
	Date date = null;
	try
	{
	    date = DATE_TIME_FORMAT.parse(dateString);
	}
	catch (ParseException e)
	{
	    e.printStackTrace();
	}

	return date;
    }

   public static String getStringFromDateTime(Date date)
    {
	return DATE_TIME_FORMAT.format(date);
    }

    public static String getStringFromDate(Date date)
    {
	return DATE_FORMAT.format(date);
    }

    public static String getRelativeTimeAgo(String rawJsonDate)
    {
	long dateMillis = getDateFromString(rawJsonDate).getTime();
	String relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();

	return formatRelativeDateString(relativeDate);	
    }
    
    public static String getRelativeTimeAgo(Date date)
    {
	long dateMillis = date.getTime();
	String relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis, System.currentTimeMillis(), DateUtils.DAY_IN_MILLIS).toString();

	return relativeDate;
	//return formatRelativeDateString(relativeDate);		
    }
    
    private static String formatRelativeDateString(String relativeDate)
    {
	relativeDate = StringUtils.replace(relativeDate, " hours", "h");
	relativeDate = StringUtils.replace(relativeDate, " hour", "h");
	relativeDate = StringUtils.replace(relativeDate, " minutes", "m");
	relativeDate = StringUtils.replace(relativeDate, " minute", "m");
	relativeDate = StringUtils.replace(relativeDate, " seconds", "s");
	relativeDate = StringUtils.replace(relativeDate, " second", "s");
	relativeDate = StringUtils.replace(relativeDate, " days", "d");
	relativeDate = StringUtils.replace(relativeDate, " day", "d");
	relativeDate = StringUtils.replace(relativeDate, " ago", "");
	return relativeDate;
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

    public static Bitmap convertParseImageFile(ParseFile pictureFile)
    {
	byte[] byteArray = null;

	try
	{
	    byteArray = pictureFile.getData();
	}
	catch (com.parse.ParseException e)
	{
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

	return bmp;
    }
}