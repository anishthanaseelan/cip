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
package com.cts.cip.core.bo.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cts.cip.common.exceptions.MultipleResourceExistException;
import com.cts.cip.common.exceptions.ResourceNotExistException;
import com.cts.cip.common.model.Load;
import com.cts.cip.core.bo.LoadBO;

public class LoadServiceImpl implements LoadService {

	LoadBO loadBO;
	
	public LoadBO getLoadBO() {
		return loadBO;
	}

	public void setLoadBO(LoadBO loadBO) {
		this.loadBO = loadBO;
	}

	Logger logger = LoggerFactory.getLogger(LoadServiceImpl.class);

	public Boolean isSUExist(String suRefId) throws MultipleResourceExistException {
		Load load = null;
		try {
			load = loadBO.getBySU(suRefId);
		} catch (ResourceNotExistException e) {
			logger.info(e.getMessage(), e);
			return false;
		}

		return load != null;
	}

}
