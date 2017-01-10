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
package com.cts.cip.kpi.repository.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cts.cip.common.model.kpi.ShipmentSummary;



public class SummaryListRowMapper implements RowMapper<ShipmentSummary> {

	@Override
	public ShipmentSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ShipmentSummary shipmentSummary = new ShipmentSummary();
		shipmentSummary.setLabel(rs.getString("label"));
		shipmentSummary.setRequestCount(rs.getLong("total_request"));
		shipmentSummary.setLabelCount(rs.getLong("total_labels"));
		shipmentSummary.setLoadCount(rs.getLong("total_loads"));
		shipmentSummary.setManifestCount(rs.getLong("total_manifest"));
		shipmentSummary.setErrorCount(rs.getLong("total_errors"));	
		return shipmentSummary;
	}

}
