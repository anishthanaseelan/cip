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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.cts.cip.common.dto.ShipmentLoadInfo;
import com.cts.cip.common.model.CIPKPIConstants;



public class ShipmentLoadInfoRowMapper implements RowMapper<ShipmentLoadInfo> {

	@Override
	public ShipmentLoadInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		 ResultSetMetaData rsmd;
         rsmd = rs.getMetaData();
         int columnCount = rsmd.getColumnCount();
         String tableName="";
         String columnName ="";
         SimpleDateFormat sdf = new SimpleDateFormat(CIPKPIConstants.DATE_FORMAT);
 		 ShipmentLoadInfo shipmentLoadInfo = new ShipmentLoadInfo();
 		 
         for (int i = 1; i <= columnCount; i++) {        	 
        	 tableName = rsmd.getTableName(i);
        	 columnName = rsmd.getColumnName(i);      	 
        	 if(columnName.equalsIgnoreCase("status")) {           	 
        		 if(tableName.equalsIgnoreCase("shipping_unit")) {        		
        			 shipmentLoadInfo.setShipmentStatus(rs.getString(i));
        		 } else {        		
        			 shipmentLoadInfo.setLoadStatus(rs.getString(i));
        		 }
        	 }
         }	 
		
		shipmentLoadInfo.setShipReferenceId(rs.getString("referance_id"));
		
		

		if (shipmentLoadInfo.getShipmentStatus() != null)
			shipmentLoadInfo
					.setShipmentStatus(shipmentLoadInfo.getShipmentStatus().replaceAll("_", " "));

		shipmentLoadInfo.setLastUpdatedTmstmp(sdf.format(rs.getTimestamp(("last_updated_ts"))));
		

		if (shipmentLoadInfo.getLoadStatus() != null)
			shipmentLoadInfo.setLoadStatus(shipmentLoadInfo.getLoadStatus().replaceAll("_", " "));

		shipmentLoadInfo.setLoadReferenceId(rs.getString("reference_id"));
		shipmentLoadInfo.setCarrierServiceType(rs.getString("service_name"));
		shipmentLoadInfo.setTrackingNumber(rs.getString("tracking_nbr"));
		return shipmentLoadInfo;		
	}

}
