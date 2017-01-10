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
package com.cts.cip.core.test.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cts.cip.core.repository.entities.ShippingUnitEntity;

@Transactional
@Component
public class TestDBUtilityImpl implements TestDBUtility {

	Logger logger = LoggerFactory.getLogger(TestDBUtilityImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public void setDBData(String sqlfilename) throws IOException {
		
		if (isDBPopulated()){
			return;
		}
		File sqlFile = new File(sqlfilename);
		List<String> queryList = Files.readAllLines(Paths.get(sqlFile.toURI()), Charset.defaultCharset());
		StringBuilder queryString = new StringBuilder();

		logger.debug(queryString.toString());
		for (String query : queryList) {
			if (query.startsWith("#") || query.trim().length() == 0) {
				continue;
			}
			try {
				em.createNativeQuery(query.trim()).executeUpdate();
			} catch (Exception e) {
				logger.error("Persisting Test data failed" + query.trim());
				throw e;
			}
		}
		return;
	}

	private boolean isDBPopulated() {
		if (em.find(ShippingUnitEntity.class, 1) != null ){
			return true;
		}
		return false;
	}

}
