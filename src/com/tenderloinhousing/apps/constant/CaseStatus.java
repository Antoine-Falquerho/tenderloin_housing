package com.tenderloinhousing.apps.constant;

public enum CaseStatus 
{
    SUBMITTED("Submitted"),
    IN_REVIEW("In Review"),
    VERIFIED("Verified");


    private final String value;

    CaseStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    
   

}
