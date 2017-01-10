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

import com.cts.cip.common.dto.NodeSearchCriteria;
import com.cts.cip.master.repository.entities.Location;
import com.cts.cip.master.repository.entities.Node;

public interface  NodeRepository  {
	
	String add(Node node);

	void update(Node node);
	
	void remove(String id);

	Node findById(String id);		

	List<Node> findAll();

	int findCount();

	List<Node> findByPage(int pageNumber, int pageSize, String sortField, String sortDirection);
	
	List<Node> findByCriteria(NodeSearchCriteria nodeSearchCriteria,boolean paginationRequired);

	int addLocation(Location location);

	Location findLocationById(int id);

	void updateLocation(Location location);
	

}
