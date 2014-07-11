package com.tenderloinhousing.apps.model;

import com.parse.ParseClassName;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser
{

    // Ensure that your subclass has a public default constructor
    public User()
    {
	super();
    }

    public String getUsername()
    {
	return getString("username");
    }

    public void setUsername(String username)
    {
	put("username", username);
    }

    public String getUserId()
    {
	return getString("objectId");
    }

    public String getPhone()
    {
	return getString("phone");
    }

    public void setPhone(String phone)
    {
	put("phone", phone);
    }

    public String getUserType()
    {
	return getString("userType");
    }

    public void setUserType(String userType)
    {
	put("userType", userType);
    }

    public String getLanguage()
    {
	return getString("language");
    }

    public void setLanguage(String language)
    {
	put("language", language);
    }

    public void setEmail(String email)
    {
	put("email", email);
    }

    public void setName(String name)
    {
	put("name", name);
    }

    public String getName()
    {
	return getString("name");
    }

    public String getEmail()
    {
	return getString("email");
    }

}
