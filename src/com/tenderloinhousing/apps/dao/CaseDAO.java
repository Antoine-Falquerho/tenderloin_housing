package com.tenderloinhousing.apps.dao;

import java.util.List;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tenderloinhousing.apps.model.Case;

public class CaseDAO {

	
	public void getAll(){
		 ParseQuery<Case> query = ParseQuery.getQuery("Case");
		 query.findInBackground(new FindCallback<Case>() {
		     public void done(List<Case> objects, ParseException e) {
		         if (e == null) {//		             
		             int j = 0;
		             
		             while (j < objects.size()) {
		            	 Log.d("debug", objects.get(j).getAddress() + "geo" + objects.get(j).getAddressLocation());
		            	 j++;
		             }
			         	
		         } else {
		        	 Log.d("debug", "Error in CaseDAO#getAll");
		         }
		     }
		 });
		
	}
	
	 public static <T extends ParseObject> void getAll(Class<T> classObj, FindCallback<T> callback) {
	        ParseQuery<T> query = ParseQuery.getQuery(classObj);
	        query.findInBackground(callback);
	 }
	
}
