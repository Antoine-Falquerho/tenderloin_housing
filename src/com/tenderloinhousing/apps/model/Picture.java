package com.tenderloinhousing.apps.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Picture")
public class Picture extends ParseUser
{
    String pictureId;
    ParseObject thcCase;
    ParseFile imageFile;

    public Picture()
    {
	super();
    }

    public String getPictureId()
    {
	return getString("objectId");
    }

    public Case getThcCase()
    {
        return (Case) getParseObject("case");
    }

    public void setThcCase(ParseObject thcCase)
    {
	put("case", thcCase);
    }

    public ParseFile getImageFile()
    {
        return getParseFile("imageFile");
    }

    public void setImageFile(ParseFile imageFile)
    {
	put("imageFile", imageFile);
    }
    
    
}
