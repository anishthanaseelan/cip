package com.cts.cip.common.tracker.model;

public enum TrackerSpecialService {
    CASH_ON_DELIVERY("CASH ON DELIVERY"),
    HOLD_AT_LOCATION("PACKAGE_HOLD_AT_LOCATION"),
    HAZARDOUS_MATERIAL("HAZARDOUS"),
	
	
	DELIVERY_CONFIRM("DELIVERY CONFIRMATION"),
	DELIVERY_CONFIRM_SIGN_REQD("CONFIRMATION SIGNATURE REQUIRED ON DELIVERY"),
	DELIVERY_CONFIRM_ADULT_SIGN_REQD("ADULT CONFIRMATION SIGNATURE REQUIRED ON DELIVERY");
	
	private final String description;
	private TrackerSpecialService(String value){
		this.description = value;
	}
	public String getDescription(){
		return this.description;
	}
    public String value() {
        return name();
    }

    public static TrackerSpecialService fromValue(String v) {
        return valueOf(v);
    }

}
