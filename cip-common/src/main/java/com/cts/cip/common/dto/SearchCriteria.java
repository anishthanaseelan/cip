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
package com.cts.cip.common.dto;

import java.util.List;

public class SearchCriteria {
	
	@Override
	public String toString() {
		return "SearchCriteria [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", sortField=" + sortField
				+ ", sortDirection=" + sortDirection + "]";
	}
	int pageNumber; 
	int pageSize;
	String sortField; 
	String sortDirection;	
	List<FilterCriteria> filterCriteria;
	
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	public String getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}
	public List<FilterCriteria> getFilterCriteria() {
		return filterCriteria;
	}
	public void setFilterCriteria(List<FilterCriteria> filterCriteria) {
		this.filterCriteria = filterCriteria;
	}
	
	

}
