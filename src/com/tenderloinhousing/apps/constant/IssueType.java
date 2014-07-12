package com.tenderloinhousing.apps.constant;

public enum IssueType 
{
    MOLD("mold"),
    PESTS_MICE("pests (mice)"),
    PESTS_ROACHES("pests (roaches)"),
    PESTS_BED_BUGS("pests (bed bugs)"),
    NO_WORKING_HEATER("no working heater"),
    NON_FUNCTIONING_ELEVATOR("non-functioning elevator"),
    NO_HOT_WATER("no hot water"),
    BROKEN_WINDOWS_DOORS_WALLS("broken windows/doors/walls"),
    OBSTRUCTION_OF_EGRESS("obstruction of egress"),
    GENERAL_MAINTENANCE("general maintenance"),
    SECURITY("security"),
    FIRE_DETECTOR("fire detector"),
    CARBON_MONOXIDE_DETECTOR("carbon monoxide detector"),
    OTHER("other");


    private final String value;

    IssueType(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }
    
   

}
