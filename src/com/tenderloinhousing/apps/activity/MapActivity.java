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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
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
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.tenderloinhousing.apps.CaseActivity;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.adapter.MapCaseAdapter;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.helper.BuildingList;
import com.tenderloinhousing.apps.helper.GeocoderTask;
import com.tenderloinhousing.apps.helper.GoogleServiceUtil;
import com.tenderloinhousing.apps.model.Building;
import com.tenderloinhousing.apps.model.Case;

public class MapActivity extends FragmentActivity implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		SearchView.OnQueryTextListener,
		IConstants,
		SlidingUpPanelLayout.PanelSlideListener,
		OnMarkerClickListener{

    	ParseUser user;
    
	private ListView mListView;
    private SlidingUpPanelLayout mSlidingUpPanelLayout;

    private View mTransparentHeaderView;
    private View mTransparentView;
    private View mSpaceView;

    	
	private SupportMapFragment mapFragment;
	private GoogleMap map;
	private LocationClient mLocationClient;
	private HashMap<Marker, String> caseMarkerMap;
	private LatLngBounds.Builder bounds;
	private MenuItem searchItem;
	private SearchView mSearchView;
	private List<Case> mapCases;
	private ArrayList<Case> caseList;
	private MapCaseAdapter caseListAdapter;

	private LatLng latLng;
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
		BuildingList buildingList = BuildingList.getInstance();
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		setContentView(R.layout.activity_map);
		ParseAnalytics.trackAppOpened(getIntent());
		
		 mListView = (ListView) findViewById(R.id.list);
	        mListView.setOverScrollMode(ListView.OVER_SCROLL_NEVER);

	        mSlidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.slidingLayout);
	        mSlidingUpPanelLayout.setEnableDragViewTouchEvents(true);

	        int mapHeight = getResources().getDimensionPixelSize(R.dimen.map_height);
	        mSlidingUpPanelLayout.setPanelHeight(mapHeight); // you can use different height here
	        mSlidingUpPanelLayout.setScrollableView(mListView, mapHeight);

	        mSlidingUpPanelLayout.setPanelSlideListener(this);

	        // transparent view at the top of ListView
	        mTransparentView = findViewById(R.id.transparentView);

	        // init header view for ListView
	        mTransparentHeaderView = LayoutInflater.from(this).inflate(R.layout.transparent_header_view, null, false);
	        mSpaceView = mTransparentHeaderView.findViewById(R.id.space);


	        mListView.addHeaderView(mTransparentHeaderView);
	        caseList = new ArrayList <Case>();
	        caseListAdapter = new MapCaseAdapter(this,caseList);
	        mListView.setAdapter(caseListAdapter);
	        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	            @Override
	            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	                mSlidingUpPanelLayout.collapsePane();
	                openCaseDetailIntent(caseList.get(position).getCaseId());
	            }
	        });
		
		mapFragment = SupportMapFragment.newInstance();
	        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
	        fragmentTransaction.add(R.id.mapContainer, mapFragment, "map");
	        fragmentTransaction.commit();

		mLocationClient = new LocationClient(this, this, this);
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
	}

	@Override
	protected void onResume() {
		super.onStart();
		geoCodeCases();
		map.setOnMarkerClickListener(this);
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
			latLng = new LatLng(location.getLatitude(), location.getLongitude());
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
	intent.putExtra(METHOD_KEY, METHOD_CODE_CREATE);
	intent.putExtra(LATLNG_KEY, latLng);
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
    
    public void repopulateCasestoList(List <Case> caseList){
    	caseListAdapter.clear();
    	caseListAdapter.addAll(caseList);
    	caseListAdapter.notifyDataSetChanged();
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
    	
    	if (caseList.size() > 0){
    	  map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 300));
    	}
    	
    }
    
    public void geoCodeBuildings() {
        ParseDAO.getAll(Building.class, new FindCallback<Building>() {
            @Override
            public void done(List<Building> buildingList, com.parse.ParseException e) {
                if (e == null) {
                    for (Building building : buildingList) {
                        Log.d(DEBUG, " Building" + building.getAddress());
                        new GeocoderTask(getApplicationContext(),building).execute(building);
                        Log.d(DEBUG, " CODED " + building.getGeoLocation());
                    }
                } else {
                    Log.d(ERROR, "Error: " + e.getMessage());
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
//	                        Log.d(DEBUG, " Obtained Building geo " + inputCase.getBuilding().getAddress());
	                    }
	                    mapCases = caseList;
	                    addMarkers(caseList); 
	                    repopulateCasestoList(caseList);
	                    
	                } else {
	                    Log.d("item", "Error: " + e.getMessage());
	                }
	            }
	        });
	    }
    

	@Override
	public boolean onMarkerClick(final Marker marker) {
		populateCasebyId(caseMarkerMap.get(marker));
//		openCaseDetailIntent(caseMarkerMap.get(marker));
		mSlidingUpPanelLayout.collapsePane();
		return true;
	}

	private void openCaseDetailIntent(String caseId){
		Intent intent = new Intent(this, CaseActivity.class);
		intent.putExtra(CASE_ID_KEY, caseId);
		intent.putExtra(METHOD_KEY, METHOD_CODE_DETAIL);

		startActivity(intent);
	}
	
	@Override
	public boolean onQueryTextSubmit(String query) {
		populateCasebyId(query);
		return true;
	}

	private void populateCasebyId(String caseId){
		 ParseDAO.getCaseById(caseId, new GetCallback<Case>() {
	           @Override
				public void done(Case foundCase, ParseException e) {
					if (e == null) {
	                    if (foundCase!=null){
		                    Log.d(DEBUG, " foundCase " + foundCase.getBuilding().getAddress());
	                    	List<Case> caseList = new ArrayList<Case>();
	                    	caseList.add(foundCase);
	                    	clearMarkers();
	                    	addMarkers(caseList);
	                    	repopulateCasestoList(caseList);
	                    }
	                } else {
                  	Toast.makeText(getApplicationContext(), "No case with that id",Toast.LENGTH_LONG).show();
	                    Log.d(ERROR, "Error: " + e.getMessage());
	                }
					
				}
	        });
	}
	
	 private void collapseMap() {
	        mSpaceView.setVisibility(View.VISIBLE);
	        mTransparentView.setVisibility(View.GONE);
	    }

	    private void expandMap() {
	        mSpaceView.setVisibility(View.GONE);
	        mTransparentView.setVisibility(View.INVISIBLE);
	    }
	
	@Override
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		return false;
	}
	
	protected boolean isAlwaysExpanded() {
	      return false;
	}

	@Override
	public void onPanelSlide(View panel, float slideOffset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPanelCollapsed(View panel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPanelExpanded(View panel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPanelAnchored(View panel) {
		// TODO Auto-generated method stub
		
	}
 
}
