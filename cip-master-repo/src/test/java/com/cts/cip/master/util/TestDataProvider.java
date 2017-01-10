package com.cts.cip.master.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.cts.cip.common.dto.CarrierDetail;
import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.common.dto.NodeInfo;
import com.cts.cip.common.dto.NodeSearchCriteria;
import com.cts.cip.common.dto.RuleInfo;
import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.Node;
import com.cts.cip.master.repository.entities.Rule;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TestDataProvider {
	
	private static CarrierTestData carrierTestData;
	private static NodeTestData nodeTestData;
	
	static {		
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			String carrierTestDataFile = "C:/Users/234174/git/CarrierIntegrationPlatform_cip/cip-master-repo/src/test/resources/data/CarrierTestData.json";
			String nodeTestDataFile ="C:/Users/234174/git/CarrierIntegrationPlatform_cip/cip-master-repo/src/test/resources/data/NodeTestData.json";
			carrierTestData= mapper.readValue(new File(carrierTestDataFile),CarrierTestData.class);
			nodeTestData =  mapper.readValue(new File(nodeTestDataFile), NodeTestData.class);
		} catch (IOException e) {			
			e.printStackTrace();
		} 
	}
	
	
	
	public static Node getNode(String type) {
		Node node = null;
		switch(type) {
			case "Simple": {
				node = nodeTestData.getNode_Simple();			
				break;
			}
			default: {
				node = nodeTestData.getNode_Simple();
				break;
			}
		}
		return node;
	}

	public static NodeInfo getNodeInfo(String type) {
		NodeInfo nodeInfo = null;
		switch(type) {
			case "Simple": {
				nodeInfo = nodeTestData.getNodeInfo_Simple();			
				break;
			}
			default: {
				nodeInfo = nodeTestData.getNodeInfo_Simple();
				break;
			}
		}
		return nodeInfo;
	}
	public static NodeDetail getNodeDetail(String type) {
		NodeDetail nodeDetail = null;
		switch(type) {
			case "Simple": {
				nodeDetail = nodeTestData.getNodeDetail_Simple();			
				break;
			}
			case "ServiceList": {
				nodeDetail =  nodeTestData.getNodeDetail_ServiceList();
				break;
			}
			default: {
				nodeDetail = nodeTestData.getNodeDetail_Simple();
				break;
			}
		}
		return nodeDetail;
	}
	
	public static List<Node> getNodeList(String type) {
		List<Node> nodeList=null;
		switch(type) {
			case "Simple": {
				nodeList = nodeTestData.getNodeList_Simple();
				break;
			}
			default: {
				nodeList =  nodeTestData.getNodeList_Simple();
				break;
			}
		}
		return nodeList;
	}
	
	public static NodeSearchCriteria getNodeSearchCriteria(String type) {
		NodeSearchCriteria criteria = null;
		switch(type) {
			case "Simple": {
				criteria = nodeTestData.getNodeSearchCriteria_Simple();
				break;
			}
			case "Empty": {
				criteria = nodeTestData.getNodeSearchCriteria_Empty();
				break;
			}
			default: {
				criteria = nodeTestData.getNodeSearchCriteria_Simple();				
				break;
			}
		}
		return criteria;	
	}
	 
	
	public static List<NodeInfo> getNodeInfoList(String type) {
		List<NodeInfo> nodeInfoList=null;
		switch(type) {
			case "Simple": {
				nodeInfoList = nodeTestData.getNodeInfoList_Simple();
				break;
			}
			default: {
				nodeInfoList = nodeTestData.getNodeInfoList_Simple();
				break;
			}
		}
		return nodeInfoList;
	}
	
	
	public static List<CarrierInfo> getCarrierInfoList(String type) {
		
		List<CarrierInfo> carrierInfoList=null;
		switch(type) {
			case "Simple": {
				carrierInfoList = carrierTestData.getCarrierInfoList_Simple();
				break;
			}
			default: {
				carrierInfoList = carrierTestData.getCarrierInfoList_Simple();
				break;
			}
		}
		return carrierInfoList;
		
	}

	
	public static List<Carrier> getCarrierList(String type) {
		List<Carrier> carrierList=null;
		switch(type) {
			case "Simple": {
				carrierList = (List<Carrier>) carrierTestData.getCarrierList_Simple();
				break;
			}
			default: {
				carrierList = (List<Carrier>) carrierTestData.getCarrierList_Simple();
				break;
			}
		}
		return carrierList;
	}
	
	public static Carrier getCarrier(String type) {
		Carrier carrier = null;
		switch(type) {
			case "Simple": {
				carrier = (Carrier) carrierTestData.getCarrier_Simple();
				break;
			}
			default: {
				carrier = (Carrier) carrierTestData.getCarrier_Simple();
				break;
			}
		}
		return carrier;	
	}
	
	public static CarrierDetail getCarrierDetail(String type) {
		CarrierDetail carrierDetail = null;
		switch(type) {
			case "Simple": {
				carrierDetail = carrierTestData.getCarrierDetail_Simple();			
				break;
			}
			default: {
				carrierDetail = carrierTestData.getCarrierDetail_Simple();
				break;
			}
		}
		return carrierDetail;
	}
	
	public static CarrierInfo getCarrierInfo(String type) {
		CarrierInfo carrierInfo = null;
		switch(type) {
			case "Simple": {
				carrierInfo = carrierTestData.getCarrierInfo_Simple();
				break;
			}
			default: {
				carrierInfo=  carrierTestData.getCarrierInfo_Simple();
				break;
			}
		}
		return carrierInfo;
	}	
	

	public static StatusResponse getStatusReponse(String type) {
		StatusResponse statusResponse = null;
		switch(type) {
			case "Simple": {
				statusResponse  = (StatusResponse) carrierTestData.getStatusResponse_Simple();
				break;
			}
			case "EmptyResponse": {
				statusResponse  = (StatusResponse) carrierTestData.getStatusResponse_Empty();				
				break;
			}
			default: {
				statusResponse  = (StatusResponse) carrierTestData.getStatusResponse_Simple();
				
				break;
			}
		}		
		return statusResponse;
	}

	public static List<Rule> getRuleList(String type) {
		List<Rule> ruleList = null;
		
		switch(type) {
			case "Simple": {
				ruleList  = (List<Rule>) carrierTestData.getRuleList_Simple();
				break;
			}		
			default: {
				ruleList  = (List<Rule>) carrierTestData.getRuleList_Simple();				
				break;
			}
		}		
		return ruleList;
	}


	public static List<RuleInfo> getRuleInfoList(String type) {
		List<RuleInfo> ruleInfoList = null;		
		switch(type) {
			case "Simple": {
				ruleInfoList  = carrierTestData.getRuleInfoList_Simple();
				break;
			}		
			default: {
				ruleInfoList  = carrierTestData.getRuleInfoList_Simple();				
				break;
			}
		}		
		return ruleInfoList;		
	}
}
