package com.cts.cip.common.tracker.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.cts.cip.common.model.Status;

@XmlRootElement(name="ShipmentTrackResponse",namespace="http://www.cts.com/shipmentTracker/ShipmentTrackResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShipmentTrackResponse {

	@XmlElement(name="Status")
	private Status status;
	@XmlElement(name="TrackUnits" ,namespace="http://www.cts.com/shipmentTracker/ShipmentTrackResponse")
	private TrackResponseContainer trackResponseContainer;
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public TrackResponseContainer getTrackResponseContainer() {
		return trackResponseContainer;
	}
	public void setTrackResponseContainer(TrackResponseContainer trackResponseContainer) {
		this.trackResponseContainer = trackResponseContainer;
	}

	

}
