package com.cts.cip.master.util;

import java.util.List;
import com.cts.cip.common.dto.CarrierDetail;
import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.RuleInfo;
import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.Rule;

public class CarrierTestData {
	
	private Carrier carrier_Simple; 
	private List<Carrier> carrierList_Simple;
	private CarrierInfo carrierInfo_Simple;
	private List<CarrierInfo> carrierInfoList_Simple;
	private CarrierDetail carrierDetail_Simple;
	private List<Rule> ruleList_Simple;
	private List<RuleInfo> ruleInfoList_Simple;
	private StatusResponse statusResponse_Simple;
	private StatusResponse statusResponse_Empty;
				            
	
	public Carrier getCarrier_Simple() {
		return carrier_Simple;
	}
	public void setCarrier_Simple(Carrier carrier_Simple) {
		this.carrier_Simple = carrier_Simple;
	}
	public List<Carrier> getCarrierList_Simple() {
		return carrierList_Simple;
	}
	public void setCarrierList_Simple(List<Carrier> carrierList_Simple) {
		this.carrierList_Simple = carrierList_Simple;
	}
	public CarrierInfo getCarrierInfo_Simple() {
		return carrierInfo_Simple;
	}
	public void setCarrierInfo_Simple(CarrierInfo carrierInfo_Simple) {
		this.carrierInfo_Simple = carrierInfo_Simple;
	}
	public List<CarrierInfo> getCarrierInfoList_Simple() {
		return carrierInfoList_Simple;
	}
	public void setCarrierInfoList_Simple(List<CarrierInfo> carrierInfoList_Simple) {
		this.carrierInfoList_Simple = carrierInfoList_Simple;
	}
	public CarrierDetail getCarrierDetail_Simple() {
		return carrierDetail_Simple;
	}
	public void setCarrierDetail_Simple(CarrierDetail carrierDetail_Simple) {
		this.carrierDetail_Simple = carrierDetail_Simple;
	}
	public List<Rule> getRuleList_Simple() {
		return ruleList_Simple;
	}
	public void setRuleList_Simple(List<Rule> ruleList_Simple) {
		this.ruleList_Simple = ruleList_Simple;
	}
	public StatusResponse getStatusResponse_Simple() {
		return statusResponse_Simple;
	}
	public void setStatusResponse_Simple(StatusResponse statusResponse_Simple) {
		this.statusResponse_Simple = statusResponse_Simple;
	}
	public StatusResponse getStatusResponse_Empty() {
		return statusResponse_Empty;
	}
	public void setStatusResponse_Empty(StatusResponse statusResponse_Empty) {
		this.statusResponse_Empty = statusResponse_Empty;
	}
	public List<RuleInfo> getRuleInfoList_Simple() {
		return ruleInfoList_Simple;
	}
	public void setRuleInfoList_Simple(List<RuleInfo> ruleInfoList_Simple) {
		this.ruleInfoList_Simple = ruleInfoList_Simple;
	}	
}
