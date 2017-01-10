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
/**
 * 
 */
package com.cts.cip.master.repository.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.common.dto.NodeSearchCriteria;
import com.cts.cip.master.repository.entities.Carrier;
import com.cts.cip.master.repository.entities.CarrierService;
import com.cts.cip.master.repository.entities.Location;
import com.cts.cip.master.repository.entities.Node;
import com.cts.cip.master.repository.entities.Rule;
import com.cts.cip.master.repository.entities.Template;


/**
 * @author bdclab
 *
 */
@Service("masterDataService")
public class MasterDataServiceImpl implements MasterDataService {
	
	
    private CarrierRepository carrierRepository;
    private NodeRepository nodeRepository;
    private RuleRepository ruleRepository;
    private TemplateRepository templateRepository;

    @Autowired
    public MasterDataServiceImpl(CarrierRepository carrierRepository,NodeRepository nodeRepository,RuleRepository ruleRepository,
    		TemplateRepository templateRepository) {
       
        this.carrierRepository = carrierRepository;
        this.nodeRepository = nodeRepository;
        this.ruleRepository = ruleRepository;
        this.templateRepository = templateRepository;
    }

   
    @Transactional 
    public List<Carrier> findAllCarriers() throws DataAccessException {
    	return carrierRepository.findAll();
    }
    
    @Transactional    
    public List<Carrier> findCarriersByPage(int pageNumber,int pageSize,String sortField,String sortDirection) throws DataAccessException {
    	return carrierRepository.findByPage(pageNumber,pageSize,sortField,sortDirection);
    }
    
    @Transactional
	public int findCarriersCount() {
		
		return carrierRepository.getCarriersCount();
	}

    @Transactional
	public Carrier findCarrierById(int id) {
		
		return carrierRepository.findById(id);
	}

    @Transactional(readOnly=false)
	public void updateCarrier(Carrier carrier) throws DataAccessException {	    	
		carrierRepository.update(carrier);
	}
	
    
    @Transactional 
    public String  addNode(Node node) {
    	return nodeRepository.add(node);
    	
    }
    
    @Transactional 
    public List<Node>  findAllNodes() {
    	return nodeRepository.findAll();
    	
    }
    


	@Override
	public int findNodeCount() {
		return nodeRepository.findCount();
	}


	@Override
	public List<Node> findNodeByPage(int pageNumber, int pageSize, String sortField, String sortDirection) {
		return nodeRepository.findByPage(pageNumber,pageSize,sortField,sortDirection);		
		
	}


	@Override
	public Node findNodeById(String id) {
		return nodeRepository.findById(id);
		
	}


	@Override
	public void removeNode(String id) {	
		 nodeRepository.remove(id);
	}


	@Override
	public void updateNode(Node node) throws DataAccessException {
		
		nodeRepository.update(node);
		
	}


	@Override
	public List<Node> findNodesByCriteria(NodeSearchCriteria nodeSearchCriteria,boolean paginationRequired) {
		return nodeRepository.findByCriteria(nodeSearchCriteria,paginationRequired);
	}
	
	@Override
	@Transactional
	public List<Rule> findRulesByService(String serviceCode) {
		 List<Rule> ruleList =  ruleRepository.findRulesByCarrierService(serviceCode);
		 return ruleList;
	}
	
	@Override
	@Transactional
	public void  updateCarrierServiceRules(List<Rule> ruleServiceList) {
		 		
	}


	@Override
	public CarrierService findCarrierByCode(String code) {
		return carrierRepository.findByCode(code);
	}


	@Override
	public int addLocation(Location location) {
		return nodeRepository.addLocation(location);		
	}


	@Override
	public Location findLocationById(int id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void updateLocation(Location location) {
		 nodeRepository.updateLocation(location);
		
	}


	@Override
	public List<String> findActiveServiceCodes() {
		return carrierRepository.findActiveServiceCodes();		
	}


	@Override
	public List<Template> findTemplatesByCarrierService(int carrierServiceCode) {
		return templateRepository.findTemplatesByCarrierService(carrierServiceCode);		
	}
	
}
