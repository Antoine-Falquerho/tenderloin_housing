package com.tenderloinhousing.apps.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.tenderloinhousing.apps.R;
import com.tenderloinhousing.apps.constant.IConstants;

public class CaseFragment extends Fragment implements IConstants
{    
    private Button saveButton;
  	private Button cancelButton;
  	private TextView postContent;
  	
    @Override   public void onAttach(Activity activity)
    {
	super.onAttach(activity);
	
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
	super.onCreate(savedInstanceState);
	//itemAdapter = new TweetArrayAdapter(getActivity(), tweetList);

	//boolean isNetworkAvaialble = getArguments().getBoolean(NETWORK_ON_FLAG);
	//populateTimeline();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
	View view = inflater.inflate(R.layout.fragment_case, parent, false);
//	
//	postContent = ((EditText) findViewById(R.id.blog_post_content));
//
//	saveButton = ((Button) findViewById(R.id.btnSubmit);
	return view;
    }
    
    
    private void onSubmit(View v)
    {
	// When the user clicks "Save," upload the post to Parse
				// Create the Post object
				ParseObject post = new ParseObject("Post");
				post.put("textContent", postContent.getText().toString());

				// Create an author relationship with the current user
				post.put("author", ParseUser.getCurrentUser());

				// Save the post and return
				post.saveInBackground(new SaveCallback () {

					@Override
					public void done(ParseException e) {
						if (e == null) {
//							setResult(Activity.RESULT_OK);
//							finish();
						} else {
							//Toast.makeText(getApplicationContext(), "Error saving: " + e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}
					
				});
    }
    
    private void onCancel(View v)
    {
//	setResult(Activity.RESULT_CANCELED);
//	finish();
    }

   



    public static CaseFragment newInstance(Bundle args)
    {
	CaseFragment fragment = new CaseFragment();   	
   	fragment.setArguments(args);

   	return fragment;
    }
    
    
}
