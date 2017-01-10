package com.cts.cip.common.tracker.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="AcvityDetails")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrackActivityContainer {
	
	@XmlElement(name="activity")
	private List<TrackActivity> acvityDetails;
	public List<TrackActivity> getAcvityDetails() {
		return acvityDetails;
	}
	public void setAcvityDetails(List<TrackActivity> acvityDetails) {
		this.acvityDetails = acvityDetails;
	}

}
