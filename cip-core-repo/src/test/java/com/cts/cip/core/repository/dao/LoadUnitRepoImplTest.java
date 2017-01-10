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
package com.cts.cip.core.repository.dao;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cts.cip.common.dto.FilterCriteria;
import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.model.Load;
import com.cts.cip.core.repository.LoadRepo;
import com.cts.cip.core.test.util.TestDBUtility;

@ContextConfiguration(locations = { "classpath:spring/test-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test-jpa")
public class LoadUnitRepoImplTest {
	
	Logger logger = LoggerFactory.getLogger(ShippingUnitRepoImplTest.class);

	@Autowired
	LoadRepo loadUnitRepo;

	@Autowired
	TestDBUtility dbUtil;
	
	

	@Before
	public void initDB() {

		try {
			dbUtil.setDBData("src/test/resources/db/TransationTestData.sql");
		} catch (IOException e) {
			logger.error("Couldn't read sql file" + e.getMessage() , e);
		}
	}

	
	@Test
	public void testFindById() {
		Load load;
		try {
			load = loadUnitRepo.findById(1);
			assertTrue(load.getShippingUnits().size()==2);		
		} catch (ResourceNotExistException e) {			
			e.printStackTrace();
		}
	}	

	@Test
	public void testFindByPage() {
		List<Load> loadList;
		try {
			
			SearchCriteria pageSearchCriteria = new SearchCriteria();			
			loadList = loadUnitRepo.findbyPage(pageSearchCriteria);
			assertTrue(loadList.size()==13);		
		} catch (ResourceNotExistException e) {			
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindByPageWithPageSize() {
		List<Load> loadList;
		try {
				
			SearchCriteria pageSearchCriteria = new SearchCriteria();
			pageSearchCriteria.setPageNumber(1);
			pageSearchCriteria.setPageSize(5);
			
			loadList = loadUnitRepo.findbyPage(pageSearchCriteria);			
			assertTrue(loadList.size()==5);	
			
			pageSearchCriteria.setPageNumber(3);
			pageSearchCriteria.setPageSize(5);
			
			loadList = loadUnitRepo.findbyPage(pageSearchCriteria);			
			assertTrue(loadList.size()==3);	
		
			
		} catch (ResourceNotExistException e) {			
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindByPageWithStatus() {
		List<Load> loadList;
		try {
			
			SearchCriteria searchCriteria = new SearchCriteria();
			searchCriteria.setPageNumber(1);
			searchCriteria.setPageSize(5);
			List<FilterCriteria> filterCriterias  = new ArrayList<>();
			FilterCriteria filterCriteria = new FilterCriteria();
			filterCriteria.setFieldName("load_search.loadName");
			filterCriteria.setFieldValue("Load 101");
			filterCriterias.add(filterCriteria);
			searchCriteria.setFilterCriteria(filterCriterias);			
			loadList = loadUnitRepo.findbyPage(searchCriteria);	
			assertTrue(loadList.size()==1);
			
		} catch (ResourceNotExistException e) {
			e.printStackTrace();
		}
		
	}
	

}
