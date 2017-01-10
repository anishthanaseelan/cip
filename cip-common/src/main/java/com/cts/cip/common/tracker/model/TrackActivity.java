package com.cts.cip.common.tracker.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="activity")
public class TrackActivity {
	
	private String activityDate;
	private String activityLocation;
	private ShipmentStatus activityStatusCode;
	//private String activityStatusCode;//INTRANSIT , DELIVERED
	private String statusDescription;//Picked Up, Arrival Scan , Departure Scan
	private String nextActivityDate;
	
	public String getActivityDate() {
		return activityDate;
	}
	public void setActivityDate(String activityDate) {
		this.activityDate = activityDate;
	}
	public String getActivityLocation() {
		return activityLocation;
	}
	public void setActivityLocation(String activityLocation) {
		this.activityLocation = activityLocation;
	}
	
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public String getNextActivityDate() {
		return nextActivityDate;
	}
	public void setNextActivityDate(String nextActivityDate) {
		this.nextActivityDate = nextActivityDate;
	}
	public ShipmentStatus getActivityStatusCode() {
		return activityStatusCode;
	}
	public void setActivityStatusCode(ShipmentStatus activityStatusCode) {
		this.activityStatusCode = activityStatusCode;
	}


}
