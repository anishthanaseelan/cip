package com.cts.cip.common.tracker.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ShipmentTrackRequest {
	private String carrier;
	@XmlElement(name="TrackingNumbers")
	private TrackNumContainer trackNumberContainer;
	
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	public TrackNumContainer getTrackNumberContainer() {
		return trackNumberContainer;
	}
	public void setTrackNumberContainer(TrackNumContainer trackNumberContainer) {
		this.trackNumberContainer = trackNumberContainer;
	}
	
	
}
