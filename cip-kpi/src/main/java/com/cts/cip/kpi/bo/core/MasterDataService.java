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
package com.cts.cip.kpi.bo.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cts.cip.common.dto.CarrierDetail;
import com.cts.cip.common.dto.CarrierInfo;
import com.cts.cip.common.dto.CarrierServiceInfo;
import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.common.dto.NodeInfo;
import com.cts.cip.common.exceptions.BusinessException;
import com.cts.cip.common.model.kpi.BusinessUnit;
import com.cts.cip.common.model.kpi.Carrier;
import com.cts.cip.common.model.kpi.CarrierList;
import com.cts.cip.common.model.kpi.CarrierService;
import com.cts.cip.common.model.kpi.Node;
import com.cts.cip.common.model.kpi.NodeList;

@Service
public class MasterDataService {
	
	Logger logger = LoggerFactory.getLogger(MasterDataService.class);

	public List<BusinessUnit> getBusinessUnitList() {
		List<NodeInfo> nodeInfos = null;

		ClientConfig config = new ClientConfig();
		Client restClient = ClientBuilder.newClient(config);
		Properties properties =  getProperties();
		String masterServiceUri = properties.getProperty("cip.master.service.uri");
		WebTarget childWebTarget = restClient.target(masterServiceUri+"node");
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		if (response.getStatus() == 200) {
			nodeInfos = (List<NodeInfo>) response.readEntity(new GenericType<List<NodeInfo>>() {
			});
		} else {
			nodeInfos = new ArrayList<NodeInfo>();
		}

		Map<String, BusinessUnit> businessUnitMap = new HashMap<String, BusinessUnit>();

		for (NodeInfo nodeInfo : nodeInfos) {
			BusinessUnit businessUnit;
			Node node = new Node();
			node.setId(Integer.parseInt(nodeInfo.getId()));
			node.setRefId(nodeInfo.getId());
			String businessUnitId = nodeInfo.getBusinessUnitId();
			if (businessUnitMap.get(businessUnitId) == null) {
				businessUnit = new BusinessUnit();
				businessUnit.setNodes(new NodeList());
				if (nodeInfo.getBusinessUnitId() == null || nodeInfo.getBusinessUnitId().equalsIgnoreCase("")) {
					nodeInfo.setBusinessUnitId("0");
				}
				businessUnit.setId(Integer.parseInt(nodeInfo.getBusinessUnitId()));
				businessUnitMap.put(businessUnitId, businessUnit);
			}
			businessUnitMap.get(businessUnitId).getNodes().getNode().add(node);

		}
		List<BusinessUnit> businessUnits = new ArrayList<BusinessUnit>();
		for (String businessUnitId : businessUnitMap.keySet()) {
			businessUnits.add(businessUnitMap.get(businessUnitId));
		}
		return businessUnits;

	}

	
	public CarrierList getCarriersList() {
		
		List<CarrierInfo> carrierInfos = null;

		ClientConfig config = new ClientConfig();	
		Client restClient = ClientBuilder.newClient(config);
		Properties properties =  getProperties();
		String carrierServiceUri = properties.getProperty("cip.master.service.uri");
		WebTarget childWebTarget = restClient.target(carrierServiceUri+"carrier");				
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		CarrierList carrierList =  new CarrierList();
		
		if (response.getStatus() == 200) {			
			carrierInfos = (List<CarrierInfo>) response.readEntity(new GenericType<List<CarrierInfo>>(){});			
		} else {
			carrierInfos =  new ArrayList<CarrierInfo>();
		}
		
		for (CarrierInfo carrierInfo : carrierInfos) {
			Carrier carrier  = new Carrier();
			CarrierDetail carrierDetail = getCarrierDetail(carrierInfo.getId());
			carrier.setId(carrierDetail.getId());			
			for (CarrierServiceInfo carrierServiceInfo : carrierDetail.getCarrierServiceInfoList() )  {
				CarrierService carrierService = new CarrierService();
				carrierService.setId(Integer.parseInt(carrierServiceInfo.getCode()));
				carrierService.setRefId(carrierServiceInfo.getCode());
				carrier.getCarrierServices().add(carrierService);
			}			
			carrierList.getCarrier().add(carrier);
		}
		
		return carrierList;
		
	}
	
	
	public NodeDetail findNodeById(String id) throws BusinessException {
		Response response=null;
		NodeDetail nodeDetail = null ;
		try {
		
			ClientConfig config = new ClientConfig();	
			Client restClient = ClientBuilder.newClient(config);
			Properties properties =  getProperties();		
			String nodeServiceUri = properties.getProperty("cip.master.service.uri") + "node/" + id;
			logger.debug("Node Service URL : " + nodeServiceUri);
			WebTarget childWebTarget = restClient.target(nodeServiceUri);				
			Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_JSON);
			response = invocationBuilder.get();
		} catch (Exception e) {
			logger.error(e.getMessage() ,e);
			throw new BusinessException("Errror occured when trying to retrive the node details for node id " + id);
		}
		logger.debug("reponse : " + response);
		if (response!=null && response.getStatus() == 200) {
			
			nodeDetail = response.readEntity(NodeDetail.class);			
		} else {
				throw new BusinessException("Node details not found for the node id : " + id);
		}
		
		return nodeDetail;
	}
	
	private CarrierDetail getCarrierDetail (String carrierId) {
		
		CarrierDetail carrierDetail = null ;
		ClientConfig config = new ClientConfig();	
		Client restClient = ClientBuilder.newClient(config);
		Properties properties =  getProperties();
		String carrierServiceUri = properties.getProperty("cip.master.service.uri");
		String resource = carrierServiceUri + "carrier/"+ carrierId;
		WebTarget childWebTarget = restClient.target(resource);		
		Invocation.Builder invocationBuilder = childWebTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		 
		
		if (response.getStatus() == 200) {			
			carrierDetail =response.readEntity(CarrierDetail.class);			
		} 
		
		return carrierDetail;
		
		
	}
	
	
	
	
	public  Properties getProperties () {	
			
			Properties properties = new Properties();			
			try {
				InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("properties/cip-kpi.properties");
				if (inputStream == null)
					throw new IOException("cip-kpi.properties file path is not found.");
				properties.load(inputStream);
				inputStream.close();
				logger.debug("cip master properties has been loaded." + properties);				
			} catch (IOException e) {
				logger.error("Exception occured while reading the Properties file in " + MasterDataService.class.getName() + "::" + e);			
			}
					
			return properties;
	}
		

}
