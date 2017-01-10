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
package com.cts.cip.master.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.common.dto.NodeInfo;
import com.cts.cip.common.dto.NodeSearchCriteria;
import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.core.MasterNodeService;

import io.swagger.annotations.Api;

@Path("/node")
@Api(value = "/node", description = "Node service")
@Component
public class NodeResource {

	@Autowired
	MasterNodeService masterService;

	Logger logger = LoggerFactory.getLogger(NodeResource.class);

	@GET
	@Path("/test")
	public String test() {
		String message = "You have reached the node resource end point and the resource is available";
		return message;
	}

	@GET
	@Produces({ "application/json","application/xml" })	
	@Transactional
	public Response getNodeListByPage(@Context HttpHeaders headers, @QueryParam("offset") int offset,
			@QueryParam("limit") int limit, @QueryParam("sortField") String sortField,
			@QueryParam("sortDirection") String sortDirection) {

		logger.info(" Get node By Page Info : " + offset + "-" + limit + "-" + sortField + "-" + sortDirection);

		List<NodeInfo> nodesList;
		int totalRecords = 0;

		/*
		 * By Default if user sends the offset or size as 0 or not pass the
		 * value all the carriers will be returned
		 */
		if (offset <= 0 || limit <= 0) {
			nodesList = masterService.findAllNodes();
			totalRecords = nodesList.size();
		} else {
			logger.info("Entry for getNodesByPage");
			nodesList = masterService.findNodesByPage(offset, limit,sortField,sortDirection);
			totalRecords = masterService.findNodeCount();
		}

		GenericEntity<List<NodeInfo>> genericList = new GenericEntity<List<NodeInfo>>(nodesList) {
		};
		Response response = Response.ok(genericList).header("totalRecords", totalRecords)				
				.build();
		return response;

	}
	
	@PUT		
	@Produces({ "application/json","application/xml" })	
	@Consumes({ "application/json" })	
	@Transactional
	public Response getNodeListByCriteria(@Context HttpHeaders headers, NodeSearchCriteria nodeSearchCriteria) {
		

		List<NodeInfo> nodesList;
		int totalRecords = 0;
		/*
		 * By Default if user sends the offset or size as 0 or not pass the
		 * value all the carriers will be returned
		 */
		
		logger.info("Entry for getNodesByCriteria");
		/* TODO
		 * This logic needs to be removed once the UI is updated the show the page model 
		 * with next and previous instead of page numbers or another view table created for statistics 
		*/
		nodesList = masterService.findNodesByCriteria(nodeSearchCriteria,false);
		if (nodesList!=null) {
			totalRecords = nodesList.size();
			nodesList=null;
		}
		nodesList = masterService.findNodesByCriteria(nodeSearchCriteria,true);
		
		
		GenericEntity<List<NodeInfo>> genericList = new GenericEntity<List<NodeInfo>>(nodesList) {};
		Response response = Response.ok(genericList).header("totalRecords", totalRecords).build();
		return response;

	}


	@GET	
	@Produces({ "application/json","application/xml" })	
	@Path("/{id}")
	@Transactional
	public Response getNodeById(@Context HttpHeaders headers, @PathParam("id") int id) {	
		Response response = null;
		NodeDetail nodeDetail = masterService.findNodeById(String.valueOf(id));
		if(nodeDetail==null){
			response = Response.noContent().build();
		} else {
			
			logger.debug("getNodeById response: " + nodeDetail);
			response = Response.ok(nodeDetail).build();
		}
		return response;
	}

	@PUT
	@Produces({ "application/json","application/xml" })	
	@Consumes({ "application/json","application/xml" })	
	@Path("/{id}")
	@Transactional
	public Response updateNodeById(@Context HttpHeaders headers, NodeDetail NodeDetail) {
		logger.info("node request : " + NodeDetail);
		StatusResponse statusReponse = masterService.updateNode(NodeDetail);
		logger.debug("update node response : " + statusReponse );
		Response response = Response.ok(statusReponse).build();
		return response;
	}
	
	@POST
	@Produces({ "application/json","application/xml" })	
	@Consumes({ "application/json","application/xml" })	
	@Path("/{id}")
	@Transactional
	public Response addNodeById(@Context HttpHeaders headers, NodeDetail nodeDetail) {
		logger.info("node request : " + nodeDetail);
		StatusResponse statusReponse = masterService.addNode(nodeDetail);
		logger.info("node response : " + nodeDetail);
		Response response = Response.ok(statusReponse).build();
		return response;
	}
	
	
	
	@DELETE
	@Produces({ "application/json","application/xml" })	
	@Path("/{id}")
	@Transactional
	public Response removeNodeById(@Context HttpHeaders headers, NodeDetail nodeDetail) {
		logger.info("Node request : " + nodeDetail);
		StatusResponse statusReponse = masterService.removeNode(nodeDetail);
		logger.info("Node response : " + nodeDetail);
		Response response = Response.ok(statusReponse).build();
		return response;
	}
	
	
	
	

}