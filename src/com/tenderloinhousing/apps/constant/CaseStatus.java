package com.tenderloinhousing.apps.constant;

public enum CaseStatus 
{
    CREATED("Created"),
    IN_REVIEW("In Review"),
    VERIFIED("Verified"),
    CLOSED("Closed");


    private final String value;

    CaseStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    
   

}
