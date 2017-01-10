package com.cts.cip.common.tracker.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="TrackUnits")
@XmlAccessorType(XmlAccessType.FIELD)
public class TrackResponseContainer {
	
	@XmlElement(name="TrackUnit")
	private List<TrackUnitResponse> trackResponseList;
	
	public List<TrackUnitResponse> getTrackResponseList() {
		if (null == trackResponseList){
			trackResponseList = new ArrayList<TrackUnitResponse>();
		}
		return trackResponseList;
	}
	

}
