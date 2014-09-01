package com.tenderloinhousing.apps.constant;

public enum UserType 
{
    STAFF("S"),
    TENANT("T");

    private final String value;

    UserType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    
   

}
