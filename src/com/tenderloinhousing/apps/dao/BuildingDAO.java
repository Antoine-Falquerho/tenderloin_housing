package com.tenderloinhousing.apps.dao;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.constant.IssueType;
import com.tenderloinhousing.apps.model.Building;
import com.tenderloinhousing.apps.model.Case;
import com.tenderloinhousing.apps.model.User;

public class BuildingDAO implements IConstants
{
    public static <T extends ParseObject> void getAll(Class<T> classObj, FindCallback<T> callback)
    {
	ParseQuery<T> query = ParseQuery.getQuery(classObj);
	query.include("tenant");
	// query.include("pictures");
	query.include("building");
	query.findInBackground(callback);
    }

    public static void getCaseById(final String caseId, GetCallback<Case> callBack)
    {
	ParseQuery<Case> query = ParseQuery.getQuery("Case");
	query.include("tenant");
	query.include("building");

	query.getInBackground(caseId, callBack);
    }

    public static void createCase(Drawable img)
    {
	Case newCase = new Case();
	newCase.setUnit("22");
	newCase.setCaseStatus("In Progress");
	newCase.setDescription("This is a test 3");
	newCase.setIsMultiUnitPetition(true);
	newCase.setGeoLocation(0.55, 0.66);
	newCase.setIssueType(IssueType.PESTS_BED_BUGS.toString());

	// Building
	Building building = (Building) ParseObject.createWithoutData("Building", "eC53xf5qDw");
	newCase.setBuilding(building);

	// Tenant
	newCase.setTenant(ParseUser.getCurrentUser());

	// Pictures
	newCase.setPictures(createPicture(img));
	newCase.saveInBackground();

	getCaseById(newCase.getObjectId(), null);

    }

    // This is for testing purpose
    private static GetCallback<Case> getCallBack()
    {
	return new GetCallback<Case>()
	{
	    public void done(Case htcCase, ParseException e)
	    {
		if (e == null)
		{
		    printData(htcCase);
		}
		else
		{
		    Log.d(DEBUG, "Error in CaseDAO.getCaseById()");
		}
	    }
	};
    }

    // This is for testing purpose
    private static void printData(Case htcCase)
    {
	Log.d(DEBUG, htcCase.getCaseId() + ",   " +
		htcCase.getCaseStatus() + ",   " +
		htcCase.getDescription() + ",   " +
		htcCase.getGeoLocation() + ",   " +
		htcCase.getUnit() + ",   " +
		htcCase.getIssueType() + ",   " +
		htcCase.getIsMultiUnitPetition() + ",   " +
		htcCase.getCaseStatus());

	Building building = htcCase.getBuilding();
	Log.d(DEBUG, building.getBuildingId() + ",   " +
		building.getName() + ",   " +
		building.getAddress());

	User user = (User) htcCase.getTenant();
	Log.d(DEBUG, user.getUserId() + ",   " +
		user.getEmail() + ",   " +
		user.getPhone() + ",   " +
		user.getLanguage() + ",   " +
		user.getUserType() + ",   " +
		user.getName());

	ArrayList<ParseFile> pictureList = htcCase.getPictures();
	ParseFile picture = pictureList.get(0);

	try
	{
	    Log.d(DEBUG, picture.getName() + ",   " + picture.getData());
	}
	catch (ParseException e)
	{
	    e.printStackTrace();
	}

    }

    public static ArrayList<ParseFile> createPicture(Drawable img)
    {
	ArrayList<ParseFile> pictureList = new ArrayList<ParseFile>();

	byte[] imgData = (img.toString()).getBytes();
	ParseFile imgFile = new ParseFile("background.png", imgData);
	imgFile.saveInBackground();

	pictureList.add(imgFile);
	return pictureList;
    }
}
