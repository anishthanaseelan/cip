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
package com.cts.cip.master.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.common.dto.NodeInfo;
import com.cts.cip.common.dto.NodeSearchCriteria;
import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.mapper.NodeModelMapper;
import com.cts.cip.master.repository.dao.MasterDataService;
import com.cts.cip.master.repository.entities.CarrierService;
import com.cts.cip.master.repository.entities.Location;
import com.cts.cip.master.repository.entities.Node;
import com.cts.cip.master.repository.entities.NodeCarrierService;

public class MasterNodeServiceImpl implements MasterNodeService {

	MasterDataService masterDataService;

	Logger logger = LoggerFactory.getLogger(MasterNodeServiceImpl.class);

	@Override
	public List<NodeInfo> findAllNodes() {
		NodeModelMapper nodeModelMapper = new NodeModelMapper();
		List<Node> nodeList = masterDataService.findAllNodes();
		List<NodeInfo> nodeInfoList = new ArrayList<NodeInfo>();
		for (Node node : nodeList) {
			NodeInfo nodeInfo = nodeModelMapper.toNodeInfo(node);
			nodeInfoList.add(nodeInfo);
		}
		return nodeInfoList;

	}

	@Override
	public List<NodeInfo> findNodesByPage(int pageNumber, int pageSize, String sortField, String sortDirection) {
		List<Node> nodeList = masterDataService.findNodeByPage(pageNumber, pageSize, sortField, sortDirection);
		NodeModelMapper nodeModelMapper = new NodeModelMapper();
		List<NodeInfo> nodeInfoList = new ArrayList<NodeInfo>();
		for (Node node : nodeList) {
			NodeInfo nodeInfo = nodeModelMapper.toNodeInfo(node);
			nodeInfoList.add(nodeInfo);
		}

		return nodeInfoList;
	}

	@Override
	public NodeDetail findNodeById(String id) {
		Node node = masterDataService.findNodeById(id);
		List<String> activeServicCodes = masterDataService.findActiveServiceCodes();
		NodeModelMapper nodeModelMapper = new NodeModelMapper();
		NodeDetail nodeDetail = nodeModelMapper.toNodeDetail(node, activeServicCodes);
		return nodeDetail;

	}

	@Override
	public StatusResponse updateNode(NodeDetail nodeDetail) {
		StatusResponse statusResponse;
		try {
			Node node = masterDataService.findNodeById(nodeDetail.getId());
			if (node == null) {
				statusResponse = buildSErrorResponse("Error: No node was found for the id provided","002");
			} else {
				NodeModelMapper nodeModelMapper = new NodeModelMapper();
				nodeModelMapper.toNodeEntity(nodeDetail, node);
				masterDataService.updateNode(node);
				statusResponse = buildSucessResponse("node ", String.valueOf(node.getId()), "Updated");
				for (NodeCarrierService nodeCarrierService : node.getNodeCarrierServiceList()) {
					Location location = nodeCarrierService.getLocation();
					masterDataService.updateLocation(location);
				} 
			}

		} catch (DataAccessException e) {
			statusResponse = buildSErrorResponse(e.getMessage(), "002");
			logger.error(e.getMessage(), e);
		}

		return statusResponse;

	}

	@Override
	public StatusResponse addNode(NodeDetail nodeDetail) {

		StatusResponse statusResponse;
		Node node = null;
		try {
			NodeModelMapper nodeModelMapper = new NodeModelMapper();
			node = new Node();
			node = nodeModelMapper.toNodeEntity(nodeDetail, node);
			List<NodeCarrierService> newNodeCarrierServiceList = new ArrayList<NodeCarrierService>();
			newNodeCarrierServiceList = node.getNodeCarrierServiceList();
			node.setNodeCarrierServiceList(null);
			//TODO Need to check
			//String id = masterDataService.addNode(node);

			for (NodeCarrierService nodeCarrierService : newNodeCarrierServiceList) {
				CarrierService carrierService = masterDataService
						.findCarrierByCode(nodeCarrierService.getId().getCarrierServiceCode());
				nodeCarrierService.setCarrierService(carrierService);
				nodeCarrierService.setNode(node);
			}
			node.setNodeCarrierServiceList(newNodeCarrierServiceList);
			logger.debug("Actual Node  : " + node);
			String id = masterDataService.addNode(node);
			statusResponse = buildSucessResponse("node", String.valueOf(id), "Added");
		} catch (DataAccessException e) {
			logger.debug("Inside Data access Exception");
			statusResponse = buildSErrorResponse(e.getMessage(), "002");
			e.printStackTrace();
		}
		return statusResponse;

	}

	@Override
	public StatusResponse removeNode(NodeDetail carrierDetail) {
		return null;
	}

	@Override
	public int findNodeCount() {
		return masterDataService.findNodeCount();
	}

	private StatusResponse buildSucessResponse(String resourceName, String value, String type) {
		StatusResponse statusResponse = new StatusResponse();
		statusResponse.setCode("001");
		statusResponse.setCodeDescription("SUCCESS");
		statusResponse.setReturnValue(value);
		statusResponse.setDescription(type + " the " + resourceName + " changes successfully");
		return statusResponse;
	}

	private StatusResponse buildSErrorResponse(String errorMessage, String code) {
		StatusResponse statusResponse = new StatusResponse();
		statusResponse.setCode(code);
		statusResponse.setCodeDescription("ERROR");
		statusResponse.setDescription(errorMessage);
		return statusResponse;
	}

	@Override
	public List<NodeInfo> findNodesByCriteria(NodeSearchCriteria nodeSearchCriteria, boolean paginationRequired) {
		List<Node> nodeList = masterDataService.findNodesByCriteria(nodeSearchCriteria, paginationRequired);
		NodeModelMapper nodeModelMapper = new NodeModelMapper();
		List<NodeInfo> nodeInfoList = new ArrayList<NodeInfo>();
		for (Node node : nodeList) {
			NodeInfo nodeInfo = nodeModelMapper.toNodeInfo(node);
			nodeInfoList.add(nodeInfo);
		}

		return nodeInfoList;

	}

	public MasterDataService getMasterDataService() {
		return masterDataService;
	}

	public void setMasterDataService(MasterDataService masterDataService) {
		this.masterDataService = masterDataService;
	}

}
