package com.cts.cip.common.tracker.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class TrackNumContainer {
	
	@XmlElement(name="TrackingNumber")
	private List<String> trackingNumList;
	
	public List<String> getTrackingNumList() {
		if (null == trackingNumList){
			trackingNumList = new ArrayList<String>();
		}
		return trackingNumList;
	}

}
