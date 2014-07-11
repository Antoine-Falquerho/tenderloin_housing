package com.tenderloinhousing.apps.model;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Building")
public class Building extends ParseObject
{
    public Building()
    {
	super();
    }

    public String getBuildingId()
    {
	return getString("objectId");
    }
    
    
    public String getAddress()
    {
	return getString("buildingAddress");
    }

    public void setAddress(String address)
    {
	put("buildingAddress", address);
    }

    public String getName()
    {
	return getString("buildingName");
    }

    public void setName(String buildingName)
    {
	put("buildingName", buildingName);
    }

}
