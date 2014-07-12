package com.tenderloinhousing.apps.model;

import java.util.ArrayList;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Case")
public class Case extends ParseObject
{
    // Ensure that your subclass has a public default constructor
    public Case()
    {
	super();
    }

    public String getCaseId()
    {
	return getObjectId();
    }    

    public String getDescription()
    {
	return getString("description");
    }

    public void setDescription(String description)
    {
	put("description", description);
    }

    public String getUnit()
    {
	return getString("unit");
    }

    public void setUnit(String unit)
    {
	put("unit", unit);
    }

    public void setGeoLocation(Double Latitude, Double Longitude)
    {
	ParseGeoPoint point = new ParseGeoPoint(Latitude, Longitude);
	Log.d("geocode", "insid" + Longitude);
	put("geoLocation", point);
	// saveInBackground();
    }

    public ParseGeoPoint getGeoLocation()
    {
	ParseGeoPoint geoLocation = (ParseGeoPoint) get("geoLocation");
	return geoLocation;
    }
    
    public String getIssueType()
    {
	return getString("issueType");
    }

    public void setIssueType(String issueType)
    {
	put("issueType", issueType);
    }

    public Boolean getIsMultiUnitPetition()
    {
	return getBoolean("isMultiUnitPetition");
    }

    public void setIsMultiUnitPetition(Boolean isMultiUnitPetition)
    {
	put("isMultiUnitPetition", isMultiUnitPetition);
    }

    public String getCaseStatus()
    {
	return getString("caseStatus");
    }

    public void setCaseStatus(String caseStatus)
    {
	put("caseStatus", caseStatus);
    }

    // One-To-One
    public void setBuilding(Building building)
    {
	put("building", building);
    }

    public Building getBuilding()
    {
	return (Building) getParseObject("building");
    }

    // One-To-One
    public ParseUser getTenant()
    {
	return getParseUser("tenant");
    }
    
    public void setTenant(ParseUser tenant)
    {
	put("tenant", tenant);
    }

    // One-To-One
    public ParseUser getStaff()
    {
	return getParseUser("staff");
    }

    public void setStaff(ParseUser staff)
    {
	put("staff", staff);
    }
    
    // One-To-Mange
    public ArrayList<ParseFile> getPictures()
    {
        return (ArrayList<ParseFile>) get("pictures");
    }

    public void setPictures(ArrayList<ParseFile> pictures)
    {
	put("pictures", pictures);
    }
    
    

}
