package com.tenderloinhousing.apps.dao;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class CaseDAO {

	
	 public static <T extends ParseObject> void getAll(Class<T> classObj, FindCallback<T> callback) {
	        ParseQuery<T> query = ParseQuery.getQuery(classObj);
	        query.findInBackground(callback);
	 }
	
}
