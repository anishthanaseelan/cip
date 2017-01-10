package com.cts.cip.kpi.repository.dao;

public class Metric {
	
	@Override
	public String toString() {
		return "Metric [count=" + count + ", identifier=" + identifier + "]";
	}
	private long count;
	private String identifier;
	
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	

}
