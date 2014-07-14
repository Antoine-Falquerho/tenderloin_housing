package com.tenderloinhousing.apps.constant;

public enum CaseStatus 
{
    SUBMITTED("mold"),
    IN_PROGRESS("pests (mice)"),
    VERIFIED("pests (roaches)");


    private final String value;

    CaseStatus(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    
   

}
