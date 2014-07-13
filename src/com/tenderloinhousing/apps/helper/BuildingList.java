package com.tenderloinhousing.apps.helper;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.model.Building;

public final class BuildingList extends ArrayList<Building> implements IConstants
{
    private static BuildingList instance;

    private BuildingList()
    {
    }

    public static BuildingList getInstance()
    {
	if (instance == null)
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

	for (Building building : getInstance())
	{
	    if (building.getAddress().equals(address)) // TODO Will visit this later. Should do a "like" kind of matching
		buildingId = building.getBuildingId();
	}

	return buildingId;
    }

    public static String getBuildingIdByName(String name)
    {
	String buildingId = null;

	for (Building building : getInstance())
	{
	    if (building.getName().equals(name))
		buildingId = building.getBuildingId();
	}

	return buildingId;
    }
}
