/*******************************************************************************
 * (C) Copyright 2016 Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *  
 *   Contributors:
 *       Cognizant Worldwide Limited (fka, CTS UK Ltd) (“CWW”)
 *******************************************************************************/
package com.cts.cip.master.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.dto.NodeCarrierInfo;
import com.cts.cip.common.dto.NodeCarrierServiceInfo;
import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.common.dto.NodeInfo;
import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.Location;
import com.cts.cip.master.repository.entities.Node;
import com.cts.cip.master.repository.entities.NodeCarrierService;
import com.cts.cip.master.repository.entities.NodeCarrierServiceId;
import com.cts.cip.util.CommonUtility;

public class NodeModelMapper {
	Logger logger = LoggerFactory.getLogger(NodeModelMapper.class);

	private ModelMapper modelMapper = new ModelMapper();

	public NodeInfo toNodeInfo(Node node) {
		NodeInfo nodeInfo = modelMapper.map(node, NodeInfo.class);
		return nodeInfo;
	}

	public NodeDetail toNodeDetail(Node node, List<String> activeServicCodes) { 
		
		NodeDetail nodeDetail = modelMapper.map(node, NodeDetail.class);		
		Map<Integer, NodeCarrierInfo> carrierServiceMap = new HashMap<Integer, NodeCarrierInfo>();	

		// Construct node level carriers and node level carrier service mapping
		for (NodeCarrierService nodeCarrierService : node.getNodeCarrierServiceList()) {
			
			if(activeServicCodes!=null){				
				if(!activeServicCodes.contains(nodeCarrierService.getId().getCarrierServiceCode())) {					
					continue;
				}
			}
			
			NodeCarrierServiceInfo carrierServiceInfo = modelMapper.map(nodeCarrierService, NodeCarrierServiceInfo.class);
			//NodeCarrierServiceInfo carrierServiceInfo = new NodeCarrierServiceInfo();
			carrierServiceInfo.setActive(nodeCarrierService.isActive());
			carrierServiceInfo.setName(nodeCarrierService.getCarrierService().getServiceName());
			carrierServiceInfo.setCarrierServiceId(nodeCarrierService.getId().getCarrierServiceCode());
			carrierServiceInfo.setId(nodeCarrierService.getId().getNodeId());
			carrierServiceInfo.setAccountNumber(nodeCarrierService.getAccountNumber());
			carrierServiceInfo.setMeterNumber(nodeCarrierService.getMeterNumber());
			Carrier carrier = nodeCarrierService.getCarrierService().getCarrier();
			int carrierId = carrier.getId();		
		
			
			if (carrierServiceMap.containsKey(carrierId)) {				
				carrierServiceMap.get(carrierId).getCarrierServiceInfoList().add(carrierServiceInfo);
				if(carrierServiceInfo.isActive()) {
					carrierServiceMap.get(carrierId).setActive(true);
				}
			} else {

				NodeCarrierInfo nodeCarrierInfo = new NodeCarrierInfo();
				nodeCarrierInfo.setCarrierName(carrier.getName());	
				nodeCarrierInfo.setActive(carrierServiceInfo.isActive());
				String url = CommonUtility.getUrlFromFileName(carrier.getIconUrl() ,"/properties/cip-master.properties");				
				nodeCarrierInfo.setIconUrl(url);
				nodeCarrierInfo.setId(carrier.getId());
				carrierServiceMap.put(carrierId, nodeCarrierInfo);
				carrierServiceMap.get(carrierId).getCarrierServiceInfoList().add(carrierServiceInfo);
			}

		}

		List<NodeCarrierInfo> nodeCarrierInfos = new ArrayList<NodeCarrierInfo>();
		for (Integer carrierId : carrierServiceMap.keySet()) {
			nodeCarrierInfos.add(carrierServiceMap.get(carrierId));
		}
		
		nodeDetail.setNodeCarrierInfoList(nodeCarrierInfos);
		nodeDetail.setNodeStatusActive(node.isActive());
		return nodeDetail;
	}

	public Node toNodeEntity(NodeDetail nodeDetail, Node node) {
		
		node.setId(nodeDetail.getId());		
		node.setGlobalLocationNumber(nodeDetail.getGlobalLocationNumber());
		node.setName(nodeDetail.getName());
		Location location = toLocationEntity(nodeDetail,node.getLocation());
		node.setLocation(location);
		node.setActive(nodeDetail.isNodeStatusActive());
		Map<String, Boolean> nodeCarSvcStatusMap = new HashMap<String, Boolean>();
		Map<String, NodeCarrierServiceInfo> nodeCarSvcMap =  new HashMap<String,NodeCarrierServiceInfo>();
		String nodeCarSvcId ="";
		for (NodeCarrierInfo nodeCarrierInfo : nodeDetail.getNodeCarrierInfoList()) {
			
			for (NodeCarrierServiceInfo nodeCarrierServiceInfo : nodeCarrierInfo.getCarrierServiceInfoList()) {	
				nodeCarSvcId =  String.valueOf(nodeCarrierServiceInfo.getId()) + String.valueOf(nodeCarrierServiceInfo.getCarrierServiceId());
				nodeCarSvcMap.put(nodeCarSvcId, nodeCarrierServiceInfo);				
				if (!nodeCarrierInfo.isActive) {					
					nodeCarSvcStatusMap.put(nodeCarrierServiceInfo.getCarrierServiceId(), false);
				} else {					
					nodeCarSvcStatusMap.put(nodeCarrierServiceInfo.getCarrierServiceId(),nodeCarrierServiceInfo.isActive());
				}
				
			}
		}	
		// Set Node Carrier Service Status

		if(node.getNodeCarrierServiceList()!=null) {	
			List<NodeCarrierService> updateNodeCarSvcList =  node.getNodeCarrierServiceList();
			for (NodeCarrierService nodeCarrierService :updateNodeCarSvcList) {	
				
				if (!nodeCarSvcStatusMap.containsKey(nodeCarrierService.getId().getCarrierServiceCode())) {
					continue;
				}
				
				nodeCarSvcId= String.valueOf(nodeCarrierService.getId().getNodeId()) + nodeCarrierService.getId().getCarrierServiceCode();				
				if (nodeCarSvcStatusMap.containsKey(nodeCarrierService.getId().getCarrierServiceCode())) {
					nodeCarrierService.setActive(nodeCarSvcStatusMap.get(nodeCarrierService.getId().getCarrierServiceCode()));
				}
				NodeCarrierServiceInfo nodeCarrierServiceInfo = nodeCarSvcMap.get(nodeCarSvcId);				
				nodeCarrierService.setAccountNumber(nodeCarrierServiceInfo.getAccountNumber());
				nodeCarrierService.setMeterNumber(nodeCarrierServiceInfo.getMeterNumber());				
				Location updatedLocation = toLocationEntity(nodeCarrierServiceInfo, nodeCarrierService.getLocation());				
				nodeCarrierService.setLocation(updatedLocation);
				
			}
			node.setNodeCarrierServiceList(updateNodeCarSvcList);
		} else  {
			List<NodeCarrierService> nodeCarrierServiceList = new ArrayList<NodeCarrierService>();
			for (NodeCarrierInfo nodeCarrierInfo : nodeDetail.getNodeCarrierInfoList()) {
				for(NodeCarrierServiceInfo nodeCarrierServiceInfo : nodeCarrierInfo.getCarrierServiceInfoList()) {
					NodeCarrierService nodeCarrierService = mapToNodeCarrierService(nodeCarrierServiceInfo);
					
					nodeCarrierService.getId().setNodeId(Integer.parseInt(node.getId()));
					nodeCarrierServiceList.add(nodeCarrierService);
				}
			}
			node.setNodeCarrierServiceList(nodeCarrierServiceList);
		}
		
		return node;

	}
	
	private NodeCarrierService mapToNodeCarrierService(NodeCarrierServiceInfo nodeCarrierServiceInfo) {
		System.out.println("mapToNodeCarrierService entry");
		NodeCarrierService nodeCarrierService = new NodeCarrierService();
		if(nodeCarrierServiceInfo!=null && nodeCarrierServiceInfo.getAccountNumber()!=null) {
			nodeCarrierService.setAccountNumber(nodeCarrierServiceInfo.getAccountNumber());
		}		
		nodeCarrierService.setActive(nodeCarrierService.isActive());
			
		
		if(nodeCarrierServiceInfo!=null && nodeCarrierServiceInfo.getAccountNumber()!=null) {
			nodeCarrierService.setMeterNumber(nodeCarrierServiceInfo.getAccountNumber());
		}
		
		Location updatedLocation = toLocationEntity(nodeCarrierServiceInfo, null);
		nodeCarrierService.setLocation(updatedLocation);
		nodeCarrierService.setId(new NodeCarrierServiceId());
		nodeCarrierService.getId().setCarrierServiceId(nodeCarrierServiceInfo.getCarrierServiceId()	);
		System.out.println("mapToNodeCarrierService exit");
		return nodeCarrierService;
	}
	
	

	private Location toLocationEntity(NodeDetail nodeDetail, Location location) {		
		if(location==null) {
			location = new Location();
		}
		// SetLocation
		location.setName(nodeDetail.getName());
		location.setAddressLine1(nodeDetail.getLocationAddressLine1());
		location.setAddressLine2(nodeDetail.getLocationAddressLine2());
		location.setPrimaryContactName(nodeDetail.getLocationPrimaryContactName());
		location.setPrimaryContactNumber(nodeDetail.getLocationPrimaryContactNumber());
		location.setPrimaryContactEmail(nodeDetail.getLocationPrimaryContactEmail());
		location.setZipCode(nodeDetail.getLocationZipcode());
		location.setCity(nodeDetail.getLocationCity());
		location.setStateCode(nodeDetail.getLocationStateCode());
		location.setCountryCode(nodeDetail.getLocationCountryCode());
		return location;
	}
	
	private Location toLocationEntity(NodeCarrierServiceInfo nodeCarrierServiceInfo, Location location) {		
		if(location==null) {
			location = new Location();
		}
		// SetLocation
		location.setName(nodeCarrierServiceInfo.getName());
		location.setAddressLine1(nodeCarrierServiceInfo.getLocationAddressLine1());
		location.setAddressLine2(nodeCarrierServiceInfo.getLocationAddressLine2());
		location.setPrimaryContactName(nodeCarrierServiceInfo.getLocationPrimaryContactName());
		location.setPrimaryContactNumber(nodeCarrierServiceInfo.getLocationPrimaryContactNumber());
		location.setPrimaryContactEmail(nodeCarrierServiceInfo.getLocationPrimaryContactEmail());
		location.setZipCode(nodeCarrierServiceInfo.getLocationZipcode());
		location.setCity(nodeCarrierServiceInfo.getLocationCity());
		location.setStateCode(nodeCarrierServiceInfo.getLocationStateCode());
		location.setCountryCode(nodeCarrierServiceInfo.getLocationCountryCode());
		return location;
	}

}
