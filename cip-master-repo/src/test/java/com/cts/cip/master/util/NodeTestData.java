package com.cts.cip.master.util;

import java.util.List;

import com.cts.cip.common.dto.NodeCarrierServiceInfo;
import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.common.dto.NodeInfo;
import com.cts.cip.common.dto.NodeSearchCriteria;
import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.repository.entities.Node;
import com.cts.cip.master.repository.entities.NodeCarrierService;

public class NodeTestData {
	
	private Node node_Simple; 
	private List<Node> nodeList_Simple;
	private NodeInfo nodeInfo_Simple;
	private List<NodeInfo> nodeInfoList_Simple;
	private NodeDetail nodeDetail_Simple;
	private NodeDetail nodeDetail_ServiceList;
	private List<NodeCarrierService> nodeCarrierService_Simple;
	private List<NodeCarrierServiceInfo> nodeCarrierServiceInfo_Simple;
	private StatusResponse statusResponse_Simple;
	private NodeSearchCriteria nodeSearchCriteria_Simple;
	private NodeSearchCriteria nodeSearchCriteria_Empty;
	private StatusResponse statusResponse_Empty;
	
	public Node getNode_Simple() {
		return node_Simple;
	}
	public void setNoder_Simple(Node noder_Simple) {
		this.node_Simple = noder_Simple;
	}
	public List<Node> getNodeList_Simple() {
		return nodeList_Simple;
	}
	public void setNodeList_Simple(List<Node> nodeList_Simple) {
		this.nodeList_Simple = nodeList_Simple;
	}
	public NodeInfo getNodeInfo_Simple() {
		return nodeInfo_Simple;
	}
	public void setNodeInfo_Simple(NodeInfo nodeInfo_Simple) {
		this.nodeInfo_Simple = nodeInfo_Simple;
	}
	public List<NodeInfo> getNodeInfoList_Simple() {
		return nodeInfoList_Simple;
	}
	public void setNodeInfoList_Simple(List<NodeInfo> nodeInfoList_Simple) {
		this.nodeInfoList_Simple = nodeInfoList_Simple;
	}
	public NodeDetail getNodeDetail_Simple() {
		return nodeDetail_Simple;
	}
	public void setNodeDetail_Simple(NodeDetail nodeDetail_Simple) {
		this.nodeDetail_Simple = nodeDetail_Simple;
	}
	
	public List<NodeCarrierServiceInfo> getNodeCarrierServiceInfo_Simple() {
		return nodeCarrierServiceInfo_Simple;
	}
	public void setNodeCarrierServiceInfo_Simple(List<NodeCarrierServiceInfo> nodeCarrierServiceInfo_Simple) {
		this.nodeCarrierServiceInfo_Simple = nodeCarrierServiceInfo_Simple;
	}
	public void setNode_Simple(Node node_Simple) {
		this.node_Simple = node_Simple;
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
	public List<NodeCarrierService> getNodeCarrierService_Simple() {
		return nodeCarrierService_Simple;
	}
	public void setNodeCarrierService_Simple(List<NodeCarrierService> nodeCarrierService_Simple) {
		this.nodeCarrierService_Simple = nodeCarrierService_Simple;
	}
	public NodeSearchCriteria getNodeSearchCriteria_Empty() {
		return nodeSearchCriteria_Empty;
	}
	public void setNodeSearchCriteria_Empty(NodeSearchCriteria nodeSearchCriteria_Empty) {
		this.nodeSearchCriteria_Empty = nodeSearchCriteria_Empty;
	}
	public NodeSearchCriteria getNodeSearchCriteria_Simple() {
		return nodeSearchCriteria_Simple;
	}
	public void setNodeSearchCriteria_Simple(NodeSearchCriteria nodeSearchCriteria_Simple) {
		this.nodeSearchCriteria_Simple = nodeSearchCriteria_Simple;
	}
	public NodeDetail getNodeDetail_ServiceList() {
		return nodeDetail_ServiceList;
	}
	public void setNodeDetail_ServiceList(NodeDetail nodeDetail_ServiceList) {
		this.nodeDetail_ServiceList = nodeDetail_ServiceList;
	}
	
	
	
				            
}
