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
package com.cts.cip.agent.model;

import java.util.Arrays;

public class ParameterMap {
	
	
	
	private KeyValuePair[] properties;
	private int size = 0;
	private int capacity = 0;

	public ParameterMap(int capacity) {
		super();
		this.capacity = capacity;
		properties = new KeyValuePair[capacity];
		
		
	}

	public ParameterMap() {
		super();
	}

	public KeyValuePair[] getProperties() {
		return properties;
	}

	public void setProperties(KeyValuePair[] properties) {
		this.properties = properties;
	}

	public void addEntry(KeyValuePair entry){
		if (null != properties && (size < capacity)){
			properties[size++] = entry;
		}
	}

	@Override
	public String toString() {
		return "ParameterMap [properties=" + Arrays.toString(properties) + ", size=" + size + ", capacity=" + capacity
				+ "]";
	}
	
	
}
