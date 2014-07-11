package com.tenderloinhousing.apps.helper;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.util.Log;

import com.parse.ParseException;
import com.tenderloinhousing.apps.model.Case;

// An AsyncTask class for accessing the GeoCoding Web Service
public class GeocoderTask extends AsyncTask<Object, Void, Address>{
	
	private Context mContext;
	private Case processCase;
	
	public GeocoderTask(Context context, Case inputCase) {
	        mContext = context;
	        processCase= processCase;
	    } 
	@Override
    protected Address doInBackground(Object... inputCases) {
        // Creating an instance of Geocoder class
        Geocoder geocoder = new Geocoder(mContext);
        List<Address> addresses = null;
        processCase = (Case) inputCases[0];

        try {
            // Getting a maximum of 3 Address that matches the input text
            addresses = geocoder.getFromLocationName(processCase.getBuilding().getAddress(), 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Address address = (Address) addresses.get(0);
        //inputCase.setAddressLocation(address.getLatitude(), address.getLongitude());
        Log.d("geocode", "Geocoded: " +processCase.getBuilding().getAddress() + "to"+ address.getLatitude()+","+address.getLongitude());
        return address;
    }
	
	@Override
	protected void onPostExecute(Address result) {
		// TODO Auto-generated method stub
		Log.d("geocode", ""+result);
		processCase.setGeoLocation(result.getLatitude(), result.getLongitude());
		try{
		processCase.save();
		} catch (ParseException e) {
            e.printStackTrace();
        }
	}

}