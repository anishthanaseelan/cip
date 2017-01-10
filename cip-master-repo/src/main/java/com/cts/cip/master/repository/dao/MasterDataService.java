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

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.cts.cip.common.dto.NodeSearchCriteria;
import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.CarrierService;
import com.cts.cip.master.repository.entities.Location;
import com.cts.cip.master.repository.entities.Node;
import com.cts.cip.master.repository.entities.Rule;
import com.cts.cip.master.repository.entities.Template;


public interface MasterDataService {

	

	List<Carrier> findAllCarriers() throws DataAccessException;

	List<Carrier> findCarriersByPage(int pageNumber, int pageSize,String sortField,String sortDirection);
	
	Carrier   findCarrierById (int id);
	
	void updateCarrier (Carrier carrier);

	int findCarriersCount();
	
	String addNode(Node node);

	int findNodeCount();
	
	List<Node> findAllNodes();

	List<Node> findNodeByPage(int pageNumber, int pageSize, String sortField, String sortDirection);
	public List<Node> findNodesByCriteria(NodeSearchCriteria nodeSearchCriteria,boolean paginationRequired);

	Node findNodeById(String string);

	void removeNode(String i);

	void updateNode(Node node);	

	void updateCarrierServiceRules(List<Rule> ruleServiceList);

	List<Rule> findRulesByService(String serviceCode);

	CarrierService findCarrierByCode(String string);

	int addLocation(Location location);

	Location findLocationById(int id);

	void updateLocation(Location location);
	
	List<String> findActiveServiceCodes();

	List<Template> findTemplatesByCarrierService(int carrierServiceCode);

}
