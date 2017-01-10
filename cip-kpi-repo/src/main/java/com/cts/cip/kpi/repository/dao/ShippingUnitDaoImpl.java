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
package com.cts.cip.kpi.repository.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cts.cip.common.dto.FilterCriteria;
import com.cts.cip.common.dto.SearchCriteria;
import com.cts.cip.common.dto.ShipmentLoadInfo;
import com.cts.cip.common.exceptions.CriteriaValidationException;
import com.cts.cip.kpi.repository.mapper.ShipmentLoadInfoRowMapper;
import com.cts.cip.kpi.util.KPIQueryConstants;

@Repository
public class ShippingUnitDaoImpl implements ShippingUnitDao {

	Logger logger = LoggerFactory.getLogger(ShippingUnitDaoImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Value("${jpa.database}")
	String databaseName = "";	
 
	@Override
	public Integer getShippingUnitCount() {
		logger.debug("ShippingUnitKPIRepoImpl::Started processing getShippingUnitCount()-->");
		String shippingUnitsCountQuery = KPIQueryConstants.GET_SHIPPING_UNITS_COUNT_QUERY;
		Integer shippingUnitsCount;
		Map<String, Object> parameters = null;
		shippingUnitsCount = namedParameterJdbcTemplate.queryForObject(shippingUnitsCountQuery, parameters,
				Integer.class);
		logger.debug("ShippingUnitKPIRepoImpl::Completed processing getShippingUnitCount()<--");
		return shippingUnitsCount;
	}

	@Override
	public Long getShippingUnitsCriteriaCount(SearchCriteria searchCriteria) throws CriteriaValidationException {
		Long shippingUnitsCount = 0l;
		String shippingUnitsCriteriaCountQuery = KPIQueryConstants.GET_SHIPPING_UNITS_CRITERIA_COUNT_QUERY;
		if (searchCriteria == null) {
			throw new CriteriaValidationException("Invalid search Criteria");
		}
		if (searchCriteria.getPageNumber() == 0 || searchCriteria.getPageSize() == 0) {

			throw new CriteriaValidationException("Invalid search Criteria.Page number or page size cannot be 0.");
		}
		shippingUnitsCriteriaCountQuery = applyFilterCriteria(searchCriteria, shippingUnitsCriteriaCountQuery);
		
		Map<String, Object> parameters = null;
		shippingUnitsCount = namedParameterJdbcTemplate.queryForObject(shippingUnitsCriteriaCountQuery, parameters,
				Long.class);
		logger.debug(
				"ShippingUnitKPIRepoImpl::Completed processing getShippingUnitsCriteriaCount - " + shippingUnitsCount);

		return shippingUnitsCount;
	}

	@Override
	public List<ShipmentLoadInfo> getShippingUnitList(SearchCriteria searchCriteria)
			throws CriteriaValidationException {

		String shippingUnitsQuery;
		
		if (databaseName.equalsIgnoreCase("INFORMIX")) {
			shippingUnitsQuery = KPIQueryConstants.GET_SHIPPING_UNITS_QUERY_INFORMIX;
		} else {
			shippingUnitsQuery = KPIQueryConstants.GET_SHIPPING_UNITS_QUERY;
		}

		List<ShipmentLoadInfo> shipmentLoadInfoList;

		if (searchCriteria == null) {
			throw new CriteriaValidationException("Invalid search Criteria");
		}
		if (searchCriteria.getPageNumber() == 0 || searchCriteria.getPageSize() == 0) {

			throw new CriteriaValidationException("Invalid search Criteria.Page number or page size cannot be 0.");
		}	

		shippingUnitsQuery = applyFilterCriteria(searchCriteria, shippingUnitsQuery);		

		shippingUnitsQuery = applySearchCriteria(searchCriteria, shippingUnitsQuery);

		Map<String, Object> parameterMap = new HashMap<String, Object>();
		int offset = (searchCriteria.getPageNumber() - 1) * searchCriteria.getPageSize();
		parameterMap.put("offset", offset);
		parameterMap.put("limit", searchCriteria.getPageSize());
		logger.debug("Offset : " + offset + " Limit: " + searchCriteria.getPageSize());
		shipmentLoadInfoList = namedParameterJdbcTemplate.query(shippingUnitsQuery, parameterMap,
				new ShipmentLoadInfoRowMapper());

		if (shipmentLoadInfoList == null || shipmentLoadInfoList.isEmpty()) {
			shipmentLoadInfoList = new ArrayList<ShipmentLoadInfo>(0);
		}
		logger.debug("ShippingUnitQueryListing:" + shippingUnitsQuery);
		return shipmentLoadInfoList;

	}

	
	public String applyFilterCriteria(SearchCriteria searchCriteria, String query)
			throws CriteriaValidationException {
		if (searchCriteria.getFilterCriteria() != null && !searchCriteria.getFilterCriteria().isEmpty()) {
			String keyword = "where ";

			for (FilterCriteria filterCriteria : searchCriteria.getFilterCriteria()) {

				String fieldName = getFilterCriteriaMap().get(filterCriteria.getFieldName());
				if (fieldName == null || fieldName.trim().equals("")) {
					throw new CriteriaValidationException("Invalid search Criteria.The provided field name '"
							+ filterCriteria.getFieldName() + "' is not suported");
				}
				// For date range field the operator between will be used
				if (filterCriteria.getFieldName().contains("lastUpdatedTmstmp")) {
					String[] dates = filterCriteria.getFieldValue().split(",");
					if (dates != null && dates.length == 2) {
						query = query + keyword + "(" + fieldName + " between '" + dates[0]
								+ "' and '" + dates[1] + "') ";
					}
				} else {
					query = query + keyword + fieldName + " = '"
							+ filterCriteria.getFieldValue() + "'";
				}
				keyword = "AND ";
			}
		}
		return query;
	}

	public String applySearchCriteria(SearchCriteria searchCriteria, String shippingUnitsQuery) {

		if (searchCriteria.getSortDirection() != null && searchCriteria.getSortField() != null) {
			String sorField = getFilterCriteriaMap().get(searchCriteria.getSortField());
			shippingUnitsQuery = shippingUnitsQuery + " ORDER BY " + sorField;

			if ("asc".equals(searchCriteria.getSortDirection())) {
				shippingUnitsQuery = shippingUnitsQuery + " asc ";
				if (!databaseName.equalsIgnoreCase("INFORMIX")) {
					shippingUnitsQuery = shippingUnitsQuery + " LIMIT :limit OFFSET :offset ";
				}
			} else if ("desc".equals(searchCriteria.getSortDirection())) {
				shippingUnitsQuery = shippingUnitsQuery + " desc ";
				if (!databaseName.equalsIgnoreCase("INFORMIX")) {
					shippingUnitsQuery = shippingUnitsQuery + " LIMIT :limit OFFSET :offset";
				}
			}
		} else {
			shippingUnitsQuery = shippingUnitsQuery + " ORDER BY su.last_updated_ts desc";
			if (!databaseName.equalsIgnoreCase("INFORMIX")) {
				shippingUnitsQuery = shippingUnitsQuery + " LIMIT :limit OFFSET :offset";
			}
		}
		return shippingUnitsQuery;
	}
	
	private Map<String, String> getFilterCriteriaMap() {
		
		Map<String, String> filterCriteriaMap = new HashMap<String, String>();
		filterCriteriaMap.put("Shipment.carrierServiceType", "carrierservice.service_name");
		filterCriteriaMap.put("Shipment.trackingNumber", "su.tracking_nbr");
		filterCriteriaMap.put("Shipment.shipmentStatus", "su.status");
		filterCriteriaMap.put("Shipment.shipReferenceId", "su.referance_id");
		filterCriteriaMap.put("Shipment.lastUpdatedTmstmp", "su.last_updated_ts");
		filterCriteriaMap.put("Shipment.loadReferenceId", "lu.reference_id");
		filterCriteriaMap.put("Shipment.loadStatus", "lu.status");
		filterCriteriaMap.put("Shipment.carrierName", "carrier.name");
		filterCriteriaMap.put("Shipment.nodeName", "carrier.name");
		return filterCriteriaMap;
	}


}
