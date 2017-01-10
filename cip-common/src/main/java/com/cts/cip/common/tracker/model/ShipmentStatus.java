package com.cts.cip.common.tracker.model;

public enum ShipmentStatus {
	
	ORDER_CREATED,
	IN_TRANSIT,
	PICKED_UP,
	OUT_FOR_DELIVERY,
	DELIVERED,
	INFO_NOT_AVAILABLE,
	HELD_FOR_PICKUP,
	DELIVERY_EXCEPTION;
	
    public String value() {
        return name();
    }

    public static ShipmentStatus fromValue(String v) {
        return valueOf(v);
    }
}
