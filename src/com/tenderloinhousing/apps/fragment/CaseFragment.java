package com.tenderloinhousing.apps.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.constant.IConstants;
import com.tenderloinhousing.apps.dao.ParseDAO;
import com.tenderloinhousing.apps.helper.BuildingList;
import com.tenderloinhousing.apps.model.Building;
import com.tenderloinhousing.apps.model.Case;
import com.tenderloinhousing.apps.model.User;

public class CaseFragment extends Fragment implements IConstants
{
    private Button submitButton;
    private Button cancelButton;
    private Spinner spIssueType;
    private EditText etDescription;
    private Spinner spBuilding;
    private EditText etUnit;
    private CheckBox cbMultiUnit;
    private EditText etName;
    private EditText etPhone;
    private EditText etEmail;
    private EditText etLanguage;
    private LinearLayout photoContainer;
    private EditText etAddress;
    private ImageView ivPhoto;
    LatLng laglng;
    ArrayList<ParseFile> pictureList = new ArrayList<ParseFile>();
    String photoFileName;

    @Override
    public void onAttach(Activity activity)
    {
	super.onAttach(activity);

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
	laglng = getArguments().getParcelable(LATLNG_KEY);

	View view = inflater.inflate(R.layout.fragment_case, parent, false);

	photoContainer = (LinearLayout) view.findViewById(R.id.photoContainer);
	submitButton = (Button) view.findViewById(R.id.btnSubmit);
	cancelButton = (Button) view.findViewById(R.id.btnCancel);
	spIssueType = (Spinner) view.findViewById(R.id.spIssueType);
	etDescription = (EditText) view.findViewById(R.id.etDescription);
	spBuilding = (Spinner) view.findViewById(R.id.spBuilding);
	etUnit = (EditText) view.findViewById(R.id.etUnit);
	cbMultiUnit = (CheckBox) view.findViewById(R.id.cbMultiUnit);
	etName = (EditText) view.findViewById(R.id.etName);
	etPhone = (EditText) view.findViewById(R.id.etPhone);
	etEmail = (EditText) view.findViewById(R.id.etEmail);
	etLanguage = (EditText) view.findViewById(R.id.etLanguage);
	etAddress = (EditText) view.findViewById(R.id.etAddress);
	ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);

	spIssueType.setOnItemSelectedListener(getOnItemSelectedListener());
	spIssueType.setSelection(0); // default to the first item hint
	spBuilding.setOnItemSelectedListener(getOnItemSelectedListener());
	spBuilding.setSelection(0); // default to the first item hint
	ivPhoto.setOnClickListener(getOnClickListener());
	submitButton.setOnClickListener(getOnSubmitListener());
	cancelButton.setOnClickListener(getOnCancelListener());

	return view;
    }

    public OnClickListener getOnSubmitListener()
    {
	return new OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		submitCase();
	    }
	};
    }

    public void submitCase()
    {
	boolean isOk = true;

	Case newCase = buildCase(isOk);

	// Tenant
	newCase.setTenant(buildUser(isOk));

	// Building
	Building buildingObj = buildBuilding(isOk);
	if(buildingObj != null)
	    newCase.setBuilding(buildingObj);

	// Pictures
	if (!pictureList.isEmpty())
	    newCase.setPictures(pictureList); 

	if (isOk)
	{
	    // Save the post and return
	    ParseDAO.createCase(newCase, new SaveCallback()
	    {
		@Override
		public void done(ParseException e)
		{
		    if (e == null)
		    {
			Toast.makeText(getActivity(), "Case is submitted successfully. ", Toast.LENGTH_SHORT).show();
			getActivity().finish();
		    }
		    else
		    {
			Toast.makeText(getActivity(), "Remote server call failed. " + e.getMessage(), Toast.LENGTH_SHORT).show();
			Log.d(ERROR, "createCase failure : " + e.getMessage());
		    }
		}
	    });
	}
    }

    private Case buildCase(boolean isOk)
    {
	Case newCase = new Case();

	String issueType = spIssueType.getSelectedItem().toString();
	if (issueType == null || issueType.startsWith(SPINNER_HINT_PREFIX))
	{
	    Toast.makeText(getActivity(), "Please select a Violation Type. ", Toast.LENGTH_SHORT).show();
	    isOk = false;
	}
	else
	{
	    newCase.setIssueType(issueType);
	    newCase.setDescription(etDescription.getText().toString());
	    newCase.setUnit(etUnit.getText().toString());
	    newCase.setIsMultiUnitPetition(cbMultiUnit.isChecked());
	    newCase.setGeoLocation(laglng.latitude, laglng.longitude);
	}
	return newCase;
    }

    private User buildUser(boolean isOk)
    {
	User user = (User) ParseUser.getCurrentUser();

	// Name
	String name = etName.getText().toString();
	if (StringUtils.isEmpty(name))
	{
	    Toast.makeText(getActivity(), "Please enter your full name. ", Toast.LENGTH_SHORT).show();
	    isOk = false;
	}
	else
	{
	    user.setName(name);
	}

	// Email
	user.setEmail(etEmail.getText().toString());

	// Phone
	String phone = etPhone.getText().toString();
	if (StringUtils.isEmpty(phone))
	{
	    Toast.makeText(getActivity(), "Please enter your phone. ", Toast.LENGTH_SHORT).show();
	    isOk = false;
	}
	else
	    user.setPhone(phone);

	// Lanuage
	user.setLanguage(etLanguage.getText().toString());

	return user;
    }

    private Building buildBuilding(boolean isOk)
    {
	Building building = null;
	String buildingName = spBuilding.getSelectedItem().toString();
	if (buildingName == null || buildingName.startsWith(SPINNER_HINT_PREFIX))
	{
	    Toast.makeText(getActivity(), "Please select a building. ", Toast.LENGTH_SHORT).show();
	    isOk = false;
	}
	else
	{
	    String buildingId = BuildingList.getInstance().getBuildingIdByName(buildingName);
	    building = (Building) ParseObject.createWithoutData("Building", buildingId);
	}
	return building;
    }

    public ArrayList<ParseFile> addToParseFileList(Bitmap bitmap)
    {
	ByteArrayOutputStream stream = new ByteArrayOutputStream();
	bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
	byte[] imgData = stream.toByteArray();

	ParseFile imgFile = new ParseFile(photoFileName, imgData);
	imgFile.saveInBackground();
	pictureList.add(imgFile);

	return pictureList;
    }

    // ===================== Camera START =====================================
    // Respond to image placeholder to launch camera
    private OnClickListener getOnClickListener()
    {
	return new OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		// create Intent to take a picture and return control to the calling application
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		photoFileName = System.currentTimeMillis() + PHOTO_NAME_SUFIX;
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName)); // set the image file name
		// Start the image capture intent to take photo
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	    }
	};
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
	if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
	{
	    if (resultCode == Activity.RESULT_OK)
	    {
		Uri takenPhotoUri = getPhotoFileUri(photoFileName);

		// by this point we have the camera photo on disk
		Bitmap takenImage = decodeSampledBitmapFromUri(takenPhotoUri.getPath(), 220, 220);

		// Add image to srolling view
		ImageView imageView = new ImageView(getActivity().getApplicationContext());
		imageView.setLayoutParams(new LayoutParams(220, 220));
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(takenImage);

		photoContainer.addView(imageView);

		// Add to Parse
		addToParseFileList(takenImage);
	    }
	    else
	    { // Result was a failure
		Toast.makeText(getActivity(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
	    }
	}
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName)
    {
	// Get safe storage directory for photos
	File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), APP_TAG);

	// Create the storage directory if it does not exist
	if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs())
	{
	    Log.d(APP_TAG, "failed to create directory");
	}

	// Return the file target for the photo based on filename
	return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }

    public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int reqHeight)
    {
	Bitmap bm = null;

	// First decode with inJustDecodeBounds=true to check dimensions
	final BitmapFactory.Options options = new BitmapFactory.Options();
	options.inJustDecodeBounds = true;
	BitmapFactory.decodeFile(path, options);

	// Calculate inSampleSize
	options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	// Decode bitmap with inSampleSize set
	options.inJustDecodeBounds = false;
	bm = BitmapFactory.decodeFile(path, options);

	return bm;
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
	// Raw height and width of image
	final int height = options.outHeight;
	final int width = options.outWidth;
	int inSampleSize = 1;

	if (height > reqHeight || width > reqWidth)
	{
	    if (width > height)
	    {
		inSampleSize = Math.round((float) height / (float) reqHeight);
	    }
	    else
	    {
		inSampleSize = Math.round((float) width / (float) reqWidth);
	    }
	}

	return inSampleSize;
    }

    // ===================== Camera END =====================================

    // Methods for Spinner
    public OnItemSelectedListener getOnItemSelectedListener()
    {
	return new OnItemSelectedListener()
	{
	    @Override
	    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
	    {
		// String value = parent.getItemAtPosition(position).toString();
		String value = ((Spinner) parent).getSelectedItem().toString();
		setSpinnerToValue(((Spinner) parent), value);
		
		if(((Spinner) parent)== spBuilding)
		   etAddress.setText(BuildingList.getInstance().getBuildingAddressByName(value)); 		
	    }

	    @Override
	    public void onNothingSelected(AdapterView<?> parent)
	    {
		// TODO Auto-generated method stub

	    }

	};
    }

    // Methods for Spinner
    public void setSpinnerToValue(Spinner spinner, String value)
    {
	int index = 0;
	SpinnerAdapter adapter = spinner.getAdapter();
	for (int i = 0; i < adapter.getCount(); i++)
	{
	    if (adapter.getItem(i).equals(value))
	    {
		index = i;
	    }
	}
	spinner.setSelection(index);
    }

    private OnClickListener getOnCancelListener()
    {
	return new OnClickListener()
	{
	    @Override
	    public void onClick(View v)
	    {
		getActivity().setResult(Activity.RESULT_CANCELED);
		getActivity().finish();
	    }
	};
    }

    public static CaseFragment newInstance(Bundle args)
    {
	CaseFragment fragment = new CaseFragment();
	fragment.setArguments(args);

	return fragment;
    }

}
