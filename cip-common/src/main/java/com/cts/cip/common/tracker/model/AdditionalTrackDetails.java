package com.cts.cip.common.tracker.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;
@XmlType(name="AdditionalDetail")
public class AdditionalTrackDetails {
	private Map<String,String> properties;

	public Map<String, String> getProperties() {
		properties = new HashMap<String,String>();
		return properties;
	}

	public void setProperties(Map<String, String> properties) {
		this.properties = properties;
	}
	

}
