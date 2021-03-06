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

import java.util.List;

import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.common.dto.NodeInfo;
import com.cts.cip.common.dto.NodeSearchCriteria;
import com.cts.cip.common.model.StatusResponse;

public interface MasterNodeService {
	
	public List<NodeInfo> findAllNodes();
	public List<NodeInfo> findNodesByPage(int offset, int limit, String sortField, String sortDirection);	
	public List<NodeInfo> findNodesByCriteria(NodeSearchCriteria nodeSearchCriteria,boolean paginationRequired);
	public NodeDetail findNodeById(String id);
	public StatusResponse updateNode(NodeDetail nodeDetail);
	public StatusResponse addNode(NodeDetail carrierDetail);
	public StatusResponse removeNode(NodeDetail carrierDetail);
	public int findNodeCount();


}
