package com.tenderloinhousing.apps.helper;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.model.Building;

public final class BuildingList<T> extends ArrayList<T> implements IConstants
{
    private static BuildingList<Building> instance = new BuildingList<Building>();

    private BuildingList()
    {
    }

    public static BuildingList<Building> getInstance()
    {
	if (instance.isEmpty())
	{
	    ParseDAO.getAll(Building.class, new FindCallback<Building>()
	    {
		@Override
		public void done(List<Building> buildingList, com.parse.ParseException e)
		{
		    if (e == null)
		    {
			for (Building building : buildingList)
			    instance.add(building);
		    }
		    else
		    {
			Log.d(DEBUG, "Error loading building list " + e.getMessage());
		    }
		}
	    });
	}

	return instance;
    }

    public static String getBuildingIdByAddress(String address)
    {
	String buildingId = null;

	List<Building> buildingList = getInstance();
	if (!buildingList.isEmpty())
	{
	    for (Building building : buildingList)
	    {
		if (building.getAddress().equals(address)) // TODO Will visit this later. Should do a "like" kind of matching
		    buildingId = building.getBuildingId();
	    }
	}

	return buildingId;
    }

    public static String getBuildingIdByName(String name)
    {
	String buildingId = null;

	List<Building> buildingList = getInstance();
	if (!buildingList.isEmpty())
	{
	    for (Building building : buildingList)
	    {
		if (building.getName().equals(name))
		    buildingId = building.getBuildingId();
	    }
	}

	return buildingId;
    }

    public static String getBuildingAddressByName(String name)
    {
	String address = null;

	List<Building> buildingList = getInstance();
	if (!buildingList.isEmpty())
	{
	    for (Building building : buildingList)
	    {
		if (building.getName().equals(name)) // TODO Will visit this later. Should do a "like" kind of matching
		    address = building.getAddress();
	    }
	}

	return address;
    }
    
    public static Building getBuildingById(String buildingId)
    {
	Building result = null;

	List<Building> buildingList = getInstance();
	if (!buildingList.isEmpty())
	{
	    for (Building building : buildingList)
	    {
		if (building.getBuildingId().equals(buildingId)) 
		     result = building;
	    }
	}

	return result;
    }

}
