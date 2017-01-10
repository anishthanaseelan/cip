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
package com.cts.cip.master.repository.dao;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.dto.NodeSearchCriteria;
import com.cts.cip.master.repository.dao.CarrierRepository;
import com.cts.cip.master.repository.dao.MasterDataService;
import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.Location;
import com.cts.cip.master.repository.entities.Node;
import com.cts.cip.master.repository.entities.NodeCarrierService;





@ContextConfiguration(locations = {"classpath:spring/core-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class NodeRepositoryTest {
	
	Logger logger  =  LoggerFactory.getLogger(CarrierRepository.class);
	
	@Autowired
	MasterDataService masterDataService;
	
	

	@Test
	@Transactional
	public void testSave() {
		
		Node node = new Node();
		node.setId("4001");
		node.setBusinessUnitId(401);
		node.setName("Main Distribuion Center");
		node.setActive(true);
		
		Location location = new Location();
		location.setName("Main Distribution Center location");
		location.setCity("Eagan");
		location.setAddressLine1("3800 South Lane");
		location.setPrimaryContactName("4001 - Manager");		
		node.setLocation(location);
		
		int oldSize = masterDataService.findNodeCount();
		
		masterDataService.addNode(node);
		int newSize = masterDataService.findNodeCount();
		assertThat(newSize).isEqualTo(oldSize+1);			
		
	}
	

	
	
	@Test
	@Transactional
	public void testFindAllNodes() {
		List<Node> nodeList = masterDataService.findAllNodes();
		assertThat(nodeList.size()).isEqualTo(13);	
		
	}
	
	@Test
	@Transactional
	public void testfindById() {		
	
		Node node = masterDataService.findNodeById("1001");		
		assertThat(node.getBusinessUnitId()).isEqualTo(3);
		assertThat(node.getName()).isEqualTo("Williamsburg");
		logger.debug("Node-Details:",node.toString());		
	}
	
	@Test
	@Transactional
	public void testfindByIdForActiveCarriers() {		
	
		Node node = masterDataService.findNodeById("1001");			
		assertThat(node.getBusinessUnitId()).isEqualTo(3);
		assertThat(node.getName()).isEqualTo("Williamsburg");		
		for(NodeCarrierService nodeCarrierService: node.getNodeCarrierServiceList()) {
			System.out.println(nodeCarrierService);
		}
		logger.debug("Node-Details:",node.toString());		
	}
	
	@Test
	@Transactional
	public void testFindByCriteria () {
		NodeSearchCriteria nodeSearchCriteria = new NodeSearchCriteria();
		nodeSearchCriteria.setNodeId("200");
		List<Node> nodeList = masterDataService.findNodesByCriteria(nodeSearchCriteria,true);
		assertThat(nodeList.size()).isEqualTo(0);
		nodeSearchCriteria.setNodeId("100");
		nodeList = masterDataService.findNodesByCriteria(nodeSearchCriteria,true);
		assertThat(nodeList.size()).isEqualTo(4);
	
	}
	
	
	
	@Test
	@Transactional
	public void testRemove() {
		int oldSize = masterDataService.findNodeCount();
		masterDataService.removeNode("1002");
		int newSize =  masterDataService.findNodeCount();
		assertThat(newSize).isEqualTo(oldSize-1);	
	}
	
	
	@Test
	@Transactional
	public void testUpdate() {
		Node node = masterDataService.findNodeById("1001");
		assertThat(node.isActive()).isEqualTo(true);
		assertThat(node.getName()).isEqualTo("Williamsburg");
		assertThat(node.getNodeCarrierServiceList().get(0).isActive()).isEqualTo(true);
		
		node.setActive(true);
		node.setName("MainUpdated");
		node.getNodeCarrierServiceList().get(0).setActive(false);		
		masterDataService.updateNode(node);
		
		
		Node updateNode = masterDataService.findNodeById("1001");
		assertThat(updateNode.isActive()).isEqualTo(true);
		assertThat(updateNode.getName()).isEqualTo("MainUpdated");
		assertThat(updateNode.getNodeCarrierServiceList().get(0).isActive()).isEqualTo(false);
		
	}
	
	
	
}
