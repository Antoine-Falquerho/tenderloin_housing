package com.tenderloinhousing.apps.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.CaseActivity;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.fragment.CaseDetailsFragment;
import com.tenderloinhousing.apps.helper.BuildingList;
import com.tenderloinhousing.apps.helper.GeocoderTask;
import com.tenderloinhousing.apps.helper.GoogleServiceUtil;
import com.tenderloinhousing.apps.model.Building;
import com.tenderloinhousing.apps.model.Case;

public class MapActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		SearchView.OnQueryTextListener,
		OnMarkerClickListener{

    	ParseUser user;
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private LocationClient mLocationClient;
	private HashMap<Marker, String> caseMarkerMap;
	private LatLngBounds.Builder bounds;
	private MenuItem searchItem;
	private SearchView mSearchView;
	private List<Case> mapCases;

	/*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		caseMarkerMap = new HashMap<Marker, String>();
		//geoCodeBuildings();
		geoCodeCases();
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_map);
		ParseAnalytics.trackAppOpened(getIntent());

		mLocationClient = new LocationClient(this, this, this);
		mapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
		if (mapFragment != null) {
			map = mapFragment.getMap();
			if (map != null) {
				Toast.makeText(this, "Map Fragment was loaded properly!", Toast.LENGTH_SHORT).show();
				map.setMyLocationEnabled(true);
			} else {
				Toast.makeText(this, "Error - Map was null!!", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_SHORT).show();
		}
		map.setOnMarkerClickListener(this);
	}

	/*
	 * Called when the Activity becomes visible.
	 */
	@Override
	protected void onStart() {
		super.onStart();
		// Connect the client.
		if (GoogleServiceUtil.isGooglePlayServicesAvailable(this, CONNECTION_FAILURE_RESOLUTION_REQUEST)) {
			mLocationClient.connect();
		}
	}

	/*
	 * Called when the Activity is no longer visible.
	 */
	@Override
	protected void onStop() {
		// Disconnecting the client invalidates it.
		mLocationClient.disconnect();
		super.onStop();
	}

	/*
	 * Handle results returned to the FragmentActivity by Google Play services
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Decide what to do based on the original request code
		switch (requestCode) {

		case CONNECTION_FAILURE_RESOLUTION_REQUEST:
			/*
			 * If the result code is Activity.RESULT_OK, try to connect again
			 */
			switch (resultCode) {
			case Activity.RESULT_OK:
				mLocationClient.connect();
				break;
			}

		}
	}

	

	/*
	 * Called by Location Services when the request to connect the client
	 * finishes successfully. At this point, you can request the current
	 * location or start periodic updates
	 */
	@Override
	public void onConnected(Bundle dataBundle) {
		// Display the connection status
		Location location = mLocationClient.getLastLocation();
		if (location != null) {
			Toast.makeText(this, "GPS location was found!", Toast.LENGTH_SHORT).show();
			LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
//			map.animateCamera(cameraUpdate);
		} else {
			Toast.makeText(this, "Current location was null, enable GPS on emulator!", Toast.LENGTH_SHORT).show();
		}
	}

	/*
	 * Called by Location Services if the connection to the location client
	 * drops because of an error.
	 */
	@Override
	public void onDisconnected() {
		// Display the connection status
		Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
	}

	/*
	 * Called by Location Services if the attempt to Location Services fails.
	 */
	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		/*
		 * Google Play services can resolve some errors it detects. If the error
		 * has a resolution, try sending an Intent to start a Google Play
		 * services activity that can resolve error.
		 */
		if (connectionResult.hasResolution()) {
			try {
				// Start an Activity that tries to resolve the error
				connectionResult.startResolutionForResult(this,
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
				/*
				 * Thrown if Google Play services canceled the original
				 * PendingIntent
				 */
			} catch (IntentSender.SendIntentException e) {
				// Log the error
				e.printStackTrace();
			}
		} else {
			Toast.makeText(getApplicationContext(),
					"Sorry. Location services not available to you", Toast.LENGTH_LONG).show();
		}
	}

	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    getMenuInflater().inflate(R.menu.menu_search, menu);
    MenuItem searchItem = menu.findItem(R.id.menu_search);
    mSearchView = (SearchView) searchItem.getActionView();
    setupSearchView(searchItem);
	getMenuInflater().inflate(R.menu.menu_login, menu);
	getMenuInflater().inflate(R.menu.menu_report, menu);
	return true;
    }
	
	 private void setupSearchView(MenuItem searchItem) {
	    	if (isAlwaysExpanded()) {
	            mSearchView.setIconifiedByDefault(false);
	        } else {
	            searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM
	                    | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
	        }
	    	
	    	 mSearchView.setOnQueryTextListener(this);
	    	
	    }

    // Respond to ActionBar icon click
    public boolean onOptionsItemSelected(MenuItem item)
    {
	switch (item.getItemId())
	{
        	case R.id.miLogin:
        	    doSignIn();
        	    return true;
        	case R.id.miReport:
        	    doReport();
        	    return true;
        	default:
        	    return super.onOptionsItemSelected(item);        	    
	}
    }
    
    private void doReport()
    {
	Intent intent = new Intent(this, CaseActivity.class);	
	startActivity(intent);		
    }

    private void doSignIn()
    {
	Intent intent = new Intent(this, LoginActivity.class);	
	startActivity(intent);	
    }
    
    public void clearMarkers(){
    	map.clear();
    }
    
    
    public void addMarkers(List <Case> caseList ){
    	bounds = new LatLngBounds.Builder(); 
    	for (Case inputCase : caseList) {
    		MarkerOptions markerOptions = new MarkerOptions();
    		if (inputCase.getlatLng()!=null){
    			 markerOptions.position(inputCase.getBuilding().getlatLng());
    	         markerOptions.title(inputCase.getCaseStatus());
    	         Marker m = map.addMarker(markerOptions);	
    	    	 caseMarkerMap.put(m,inputCase.getCaseId());
    	    	 bounds.include(inputCase.getBuilding().getlatLng());
    		}
           
    	}
    	map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 300));
    	
    }
    
    public void geoCodeBuildings() {
        ParseDAO.getAll(Building.class, new FindCallback<Building>() {
            @Override
            public void done(List<Building> buildingList, com.parse.ParseException e) {
                if (e == null) {
                    for (Building building : buildingList) {
                        Log.d("debug", " Building" + building.getAddress());
                        new GeocoderTask(getApplicationContext(),building).execute(building);
                        Log.d("debug", " CODED " + building.getGeoLocation());
                    }
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }
    
   public void geoCodeCases() {
	   ParseDAO.getAll(Case.class, new FindCallback<Case>() {
	            @Override
	            public void done(List<Case> caseList, com.parse.ParseException e) {
	                if (e == null) {
	                    for (Case inputCase : caseList) {
	                        Log.d("debug", " Obtained Building geo " + inputCase.getBuilding().getAddress());
	                    }
	                    mapCases = caseList;
	                    addMarkers(mapCases);
	                } else {
	                    Log.d("item", "Error: " + e.getMessage());
	                }
	            }
	        });
	    }
    

	@Override
	public boolean onMarkerClick(final Marker marker) {
		Intent intent = new Intent(this, CaseActivity.class);
		intent.putExtra("case_id", caseMarkerMap.get(marker));
		intent.putExtra("method", "10");

		startActivity(intent);
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		 ParseDAO.getCaseById(query, new GetCallback<Case>() {
	           @Override
				public void done(Case foundCase, ParseException e) {
					if (e == null) {
	                    if (foundCase!=null){
		                    Log.d("debug", " foundCase " + foundCase.getBuilding().getAddress());
	                    	List<Case> caseList = new ArrayList<Case>();
	                    	caseList.add(foundCase);
	                    	clearMarkers();
	                    	addMarkers(caseList);	
	                    }
	                } else {
                    	Toast.makeText(getApplicationContext(), "No case with that id",Toast.LENGTH_LONG).show();
	                    Log.d("item", "Error: " + e.getMessage());
	                }
					
				}
	        });
	
		return true;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected boolean isAlwaysExpanded() {
	      return false;
	}
 
}
