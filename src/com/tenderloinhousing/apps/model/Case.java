package com.tenderloinhousing.apps.model;

import android.util.Log;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;

@ParseClassName("Case")
public class Case extends ParseObject {
	//	@property (retain) NSString *caseId;
	//	@property (retain) NSString *buildingId;
	//	@property (retain) NSString *name;
	//	@property (retain) NSString *address;
	//	@property (retain) NSString *unit;
	//	@property (retain) NSString *phoneNumber;
	//	@property (retain) NSString *email;
	//	@property (retain) NSString *languageSpoken;
	//	@property (retain) NSString *description;
	//	@property (retain) NSString *userId;
	//	@property status status;

	// Ensure that your subclass has a public default constructor
	public Case() {
		super();
	}

	// Add a constructor that contains core properties
	public Case(String buildingId, String name, String address, String unit, String phoneNumber, String email, String languageSpoken, String description, ParseUser user) {
		super();
		setBuilding(buildingId);
		setName(name);
		setAddress(address);
		setUnit(unit);
		setPhoneNumber(phoneNumber);
		setEmail(email);
		setLanguageSpoken(languageSpoken);
		setDescription(description);
		setUser(user);	    
	}

	private void setUser(ParseUser user) {
		ParseRelation<ParseUser> relation = this.getRelation("user");
		relation.add(user);
	}

	private void setDescription(String description) {
		put("description", description);
	}

	private void setLanguageSpoken(String languageSpoken) {
		put("languageSpoken", languageSpoken);
	}

	private void setEmail(String email) {
		put("email", email);
	}

	private void setPhoneNumber(String phoneNumber) {
		put("phoneNumber", phoneNumber);

	}

	private void setUnit(String unit) {
		put("unit", unit);
	}

	private void setAddress(String address) {
		put("address", address);
	}

	private void setName(String name) {
		put("name", name);
	}
	
	public void setAddressLocation(Double Latitude, Double Longitude){
		ParseGeoPoint point = new ParseGeoPoint(Latitude, Longitude);
		Log.d("geocode", "insid"+Longitude);
		put("addressLocation",point);
		saveInBackground();
	}
	
	public ParseGeoPoint getAddressLocation(){
		ParseGeoPoint addressLocation = (ParseGeoPoint) get("addressLocation");
		return addressLocation;
	}

	public void setBuilding(String buildingId) {
		put("buildingId", buildingId);
	}
	public String getBuildingId(){
		return getString("buildingId");
	}
	public String getName(){
		return getString("name");
	}
	public String getUnit(){
		return getString("unit");
	}
	public String getEmail(){
		return getString("email");
	}
	public String getLanguageSpoken(){
		return getString("languageSpoken");
	}	
	public String getDescription(){
		return getString("description");
	}	
	public String getAddress(){
		return getString("address");
	}
	
}
