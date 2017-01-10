package com.cts.cip.common.tracker.model;

public enum ServiceStatus {
	YES,
	NO;
	
    public String value() {
        return name();
    }

    public static ServiceStatus fromValue(String v) {
        return valueOf(v);
    }

}
