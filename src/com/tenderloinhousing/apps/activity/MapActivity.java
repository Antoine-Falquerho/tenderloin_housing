package com.tenderloinhousing.apps.activity;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.ReportActivity;
import com.tenderloinhousing.apps.dao.CaseDAO;
import com.tenderloinhousing.apps.helper.GeocoderTask;
import com.tenderloinhousing.apps.helper.GoogleServiceUtil;
import com.tenderloinhousing.apps.model.Case;

public class MapActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		OnMarkerClickListener{

    	ParseUser user;
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private LocationClient mLocationClient;
	private HashMap<Marker, String> caseMarkerMap;

	/*
	 * Define a request code to send to Google Play services This code is
	 * returned in Activity.onActivityResult
	 */
	private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		caseMarkerMap = new HashMap<Marker, String>();

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
		Toast.makeText(this, "Error - Map Fragment was null!!", Toast.LENGTH_LONG).show();
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
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17);
			map.animateCamera(cameraUpdate);
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
	getMenuInflater().inflate(R.menu.menu_login, menu);
	return true;
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
	Intent intent = new Intent(this, ReportActivity.class);	
	startActivity(intent);		
    }

    private void doSignIn()
    {
	Intent intent = new Intent(this, LoginActivity.class);	
	startActivity(intent);	
    }
    
    
    public void addMarkers(List <Case> caseList ){
    	for (Case inputCase : caseList) {
    		MarkerOptions markerOptions = new MarkerOptions();
    		if (inputCase.getlatLng()!=null){
    			 markerOptions.position(inputCase.getlatLng());
    	         markerOptions.title(inputCase.getCaseStatus());
    	         Marker m = map.addMarker(markerOptions);	
    	    	 caseMarkerMap.put(m,inputCase.getCaseId());
    		}
           
    	}
    	
    }
    
    public void geoCodeCases() {
        CaseDAO.getAll(Case.class, new FindCallback<Case>() {
            @Override
            public void done(List<Case> caseList, com.parse.ParseException e) {
                if (e == null) {
                    for (Case inputCase : caseList) {
                        Log.d("debug", " Case " + inputCase.getBuilding().getAddress());
                        new GeocoderTask(getApplicationContext(),inputCase).execute(inputCase);
                        inputCase.saveInBackground();
                        Log.d("debug", " CODED " + inputCase.getGeoLocation());
                    }
                    addMarkers(caseList);
                } else {
                    Log.d("item", "Error: " + e.getMessage());
                }
            }
        });
    }

	@Override
	public boolean onMarkerClick(final Marker marker) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Open Detail view of case id" + caseMarkerMap.get(marker), Toast.LENGTH_LONG).show();
		return true;
	}
 
}
