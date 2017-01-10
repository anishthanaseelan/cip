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
package com.cts.cip.rule.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import com.cts.cip.rule.bean.RuleBeanConfig;

public class RuleBeanLocator {
	
	private static ApplicationContext appContext;
	public static Object getBean(String beanName){
		Object beanRef = null;
		if (null == appContext){
			appContext = new AnnotationConfigApplicationContext(RuleBeanConfig.class);
			 ((AbstractApplicationContext)appContext).registerShutdownHook();
		
		}
		beanRef = appContext.getBean(beanName);
		return beanRef;
	}


}
