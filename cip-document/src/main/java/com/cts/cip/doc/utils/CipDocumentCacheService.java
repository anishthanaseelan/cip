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
package com.cts.cip.doc.utils;

import java.util.concurrent.TimeUnit;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cts.cip.common.exceptions.SystemFailure;

import jersey.repackaged.com.google.common.cache.CacheBuilder;
import jersey.repackaged.com.google.common.cache.CacheLoader;
import jersey.repackaged.com.google.common.cache.LoadingCache;

/**
 * The Purpose of this class is to provide the CipDocumentCacheService.java implementation
 * @author Cognizant Technology Solutions
 *
 */
@Service
public class CipDocumentCacheService {
	
	private static Logger logger = LoggerFactory.getLogger(CipDocumentCacheService.class);
	
	public LoadingCache<String, Object> templateTransformerCache = CacheBuilder.newBuilder()
            .maximumSize(100) // maximum 100 records can be cached
            .refreshAfterWrite(30, TimeUnit.MINUTES).build(new CacheLoader<String, Object>(){

				@Override
				public Object load(String templatePath) throws Exception {
					Transformer transformer;
					try {
						TransformerFactory factory = TransformerFactory.newInstance();
						transformer = factory.newTransformer(new StreamSource(templatePath));
						transformer.setParameter("versionParam", "2.0");
					} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
						logger.error(e.getMessage());
						throw new SystemFailure ( "Exception while creating TransformerFactory from XSL templete :" + e.getMessage() );
					}
					
					return transformer;
				}}
     ) ; 

}
