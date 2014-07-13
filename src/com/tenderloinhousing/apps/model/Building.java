package com.tenderloinhousing.apps.model;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
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
	  return getObjectId();
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
    
    public void setGeoLocation(ParseGeoPoint point){
      put("geoLocation",point);	
    }
    
    public ParseGeoPoint getGeoLocation()
    {
	  ParseGeoPoint geoLocation = (ParseGeoPoint) get("geoLocation");
	  return geoLocation;
    }
    
	public LatLng getlatLng(){
		 ParseGeoPoint point = getGeoLocation();
		 if (point !=null){
			 return new LatLng(point.getLatitude(), point.getLongitude());
		 }else{
			 return null;
		 }
    }

}
