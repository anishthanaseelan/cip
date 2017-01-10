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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cts.cip.common.model.kpi.SummaryGroup;

public class SummaryGroupRowMapper implements RowMapper<SummaryGroup> {

	@Override
	public SummaryGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ResultSetMetaData rsmd;
        rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        SummaryGroup summaryGroup = new SummaryGroup();
        
        for (int i = 1; i <= columnCount; i++) {
        	if(rsmd.getColumnName(i).equalsIgnoreCase("id")) {
        		summaryGroup.setId(rs.getInt("id"));
        	} else if(rsmd.getColumnName(i).equalsIgnoreCase("bu_name")) {
        		summaryGroup.setBusinessUnitName(rs.getString("bu_name"));
        	} else if(rsmd.getColumnName(i).equalsIgnoreCase("org_name")) {
        		summaryGroup.setOrganizationName(rs.getString("org_name"));        		
        	} else if(rsmd.getColumnName(i).equalsIgnoreCase("carrier_name")) {
        		summaryGroup.setCarrierName(rs.getString("carrier_name"));        		
        	} else if(rsmd.getColumnName(i).equalsIgnoreCase("carrier_svc_name")) {
        		summaryGroup.setCarrierServiceName(rs.getString("carrier_svc_name"));		
        	} else if(rsmd.getColumnName(i).equalsIgnoreCase("node_name")) {
        		summaryGroup.setNodeName(rs.getString("node_name"));        		
        	} 	
        }
		return summaryGroup;
	}

}
