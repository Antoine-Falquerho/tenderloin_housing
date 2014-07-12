package com.tenderloinhousing.apps.helper;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class GoogleServiceUtil
{
    public static boolean isGooglePlayServicesAvailable(FragmentActivity activity, int requestCode) {
	// Check that Google Play services is available
	int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
	// If Google Play services is available
	if (ConnectionResult.SUCCESS == resultCode) {
		// In debug mode, log the status
		Log.d("Location Updates", "Google Play services is available.");
		return true;
	} else {
		// Get the error dialog from Google Play services
		Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(resultCode, activity, requestCode);

		// If Google Play services can provide an error dialog
		if (errorDialog != null) {
			// Create a new DialogFragment for the error dialog
			ErrorDialogFragment errorFragment = new ErrorDialogFragment();
			errorFragment.setDialog(errorDialog);
			errorFragment.show(activity.getSupportFragmentManager(), "Location Updates");
		}

		return false;
	}
}
    
 // Define a DialogFragment that displays the error dialog
 	public static class ErrorDialogFragment extends DialogFragment {

 		// Global field to contain the error dialog
 		private Dialog mDialog;

 		// Default constructor. Sets the dialog field to null
 		public ErrorDialogFragment() {
 			super();
 			mDialog = null;
 		}

 		// Set the dialog to display
 		public void setDialog(Dialog dialog) {
 			mDialog = dialog;
 		}

 		// Return a Dialog to the DialogFragment.
 		@Override
 		public Dialog onCreateDialog(Bundle savedInstanceState) {
 			return mDialog;
 		}
 	}
}
