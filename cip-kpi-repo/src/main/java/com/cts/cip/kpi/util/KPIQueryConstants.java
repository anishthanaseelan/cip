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
package com.cts.cip.kpi.util;

/**
 * @author 417765
 *
 */
public class KPIQueryConstants {

	public static final String GET_SHIPPING_UNITS_QUERY = "SELECT su.referance_id, su.status,su.tracking_nbr,su.last_updated_ts ,carrierservice.service_name, lu.reference_id, "
			+ "lu.status FROM shipping_unit su "
			+ "LEFT JOIN  load_shipping_unit lsu on lsu.shipping_unit_ref_id  = su.referance_id "
			+ "LEFT JOIN  load_unit lu on lu.id  = lsu.load_id "
			+ "LEFT JOIN  carrier_service carrierservice on carrierservice.code  = su.carrier_service_id "
			+ "LEFT JOIN  carrier carrier on  carrier.id = carrierservice.carrier_id "
			+ "LEFT JOIN  shipping_order so  on su.order_id = so.id ";

	public static final String GET_SHIPPING_UNITS_QUERY_INFORMIX = "SELECT skip :offset first :limit su.referance_id,su.status,su.tracking_nbr,su.last_updated_ts,carrierservice.service_name,lu.reference_id,"
			+ "lu.status FROM shipping_unit su "
			+ "LEFT JOIN  load_shipping_unit lsu on lsu.shipping_unit_ref_id  = su.referance_id "
			+ "LEFT JOIN  load_unit lu on lu.id  = lsu.load_id "
			+ "LEFT JOIN  carrier_service carrierservice on carrierservice.code  = su.carrier_service_id "
			+ "LEFT JOIN  carrier carrier on  carrier.id = carrierservice.carrier_id "
			+ "LEFT JOIN  shipping_order so  on su.order_id = so.id ";

	public static final String GET_SHIPPING_UNITS_CRITERIA_COUNT_QUERY = "SELECT count(su.referance_id) "
			+ "FROM shipping_unit su "
			+ "LEFT JOIN  load_shipping_unit lsu on lsu.shipping_unit_ref_id  = su.referance_id "
			+ "LEFT JOIN  load_unit lu on lu.id  = lsu.load_id "
			+ "LEFT JOIN  carrier_service carrierservice on carrierservice.code  = su.carrier_service_id "
			+ "LEFT JOIN  carrier carrier on  carrier.id = carrierservice.carrier_id "
			+ "LEFT JOIN  shipping_order so  on su.order_id = so.id ";

	public static final String GET_SHIPPING_UNITS_COUNT_QUERY = "SELECT COUNT(*) FROM shipping_unit su "
			+ "LEFT JOIN  load_shipping_unit lsu on lsu.shipping_unit_ref_id  = su.referance_id "
			+ "LEFT JOIN  load_unit lu on lu.id  = lsu.load_id "
			+ "LEFT JOIN  carrier_service carrierservice on carrierservice.code  = su.carrier_service_id ";

	public static final String GET_LOAD_UNITS_QUERY = "select " + "load.id," + "load.reference_id," + "load.status,"
			+ "load.create_date," + "load.create_ts," + "load.manifest_ts, "
			+ "count(load_ship_unit.shipping_unit_ref_id) " + "from load_nit load,load_shipping_unit load_ship_unit "
			+ "where  load_id:loadId and  load.id=load_ship_unit.load_id;";

	public static final String INCLUDE_NODE_FILTER = " and node in(:nodeList)";

	public static final String INCLUDE_CARRIER_FILTER = " and carrier in (:carrierList)";

	public static final String INCLUDE_CARRIER_SERVICE_FILTER = " and carrier in (:carrierServiceList)";

	public static final String INCLUDE_BU_FILTER = " and busniess_unit in(:buList) ";

	public static final String GROUP_BY_SUMMARY_UNIT = " group by summary_unit_id ";

	public static final String GET_OVERALL_SUMMARY_QUERY = "SELECT  count(su.referance_id) as count,su.status as label  FROM shipping_unit su "
			+ "JOIN  shipping_order so on so.id = su.order_id "
			+ "JOIN  carrier_service carrierservice on carrierservice.code  = su.carrier_service_id "
			+ "JOIN  carrier crr on crr.id  = carrierservice.carrier_id "
			+ "WHERE su.last_updated_ts between (:fromDate) and (:toDate) "
			+ "AND carrierservice.code in (:carrierServiceList) " + "AND so.node_id in (:nodeList) ";

	public static final String GET_PENDING_LABEL_QUERY = "select count(referance_id) from shipping_unit where status='VALIDATED' || status = 'INITIATED' || status = 'TRACKING_NUMBER_GENERATED'";
	
	public static final String INFORMIX_GET_PENDING_LABEL_QUERY ="select count(referance_id) from shipping_unit where status='VALIDATED'  OR status = 'INITIATED' OR status = 'TRACKING_NUMBER_GENERATED'";

	public static final String GET_PENDING_LOAD_QUERY = "select count(referance_id) from shipping_unit where status='LABEL_CREATED'";

	public static final String GET_PENDING_MANIFEST_QUERY = "select count(shipping_unit_ref_id) from load_shipping_unit where load_id in(select id from load_unit where status='LOADING');";

	public static final String GET_PENDING_LABEL_BEFORE_12_HOUR_QUERY = "select count(referance_id) from shipping_unit where status='VALIDATED' || status = 'INITIATED' || status = 'TRACKING_NUMBER_GENERATED' and last_updated_ts < DATE_SUB( NOW(), INTERVAL 12 hour)";
	
	public static final String INFORMIX_GET_PENDING_LABEL_BEFORE_12_HOUR_QUERY="select count(referance_id) from shipping_unit where status='VALIDATED'  OR status = 'INITIATED' OR status = 'TRACKING_NUMBER_GENERATED' and last_updated_ts < (CURRENT - 13 UNITS hour)";

	public static final String GET_PENDING_LOAD_BEFORE_12_HOUR_QUERY = "select count(referance_id) from shipping_unit where status='LABEL_CREATED' and last_updated_ts < DATE_SUB( NOW(), INTERVAL 12 hour)";
	
	public static final String INFORMIX_GET_PENDING_LOAD_BEFORE_12_HOUR_QUERY = "select count(referance_id) from shipping_unit where status='LABEL_CREATED' and last_updated_ts < (CURRENT - 13 UNITS hour)";

	public static final String GET_PENDING_MANIFEST_BEFORE_12_HOUR_QUERY = "select count(shipping_unit_ref_id) from load_shipping_unit where load_id in(select id from load_unit lu where status='LOADING' and update_ts < DATE_SUB( NOW(), INTERVAL 12 hour))";
	
	public static final String INFORMIX_GET_PENDING_MANIFEST_BEFORE_12_HOUR_QUERY="select count(shipping_unit_ref_id) from load_shipping_unit lsu where load_id in(select id from load_unit where status='LOADING' and update_ts < (CURRENT - 13 UNITS hour))  ";	

	public static final String GET_PENDING_LABEL_BY_HOUR_QUERY = "SELECT CONCAT(DATE(last_updated_ts),' ',HOUR(last_updated_ts), ':00') AS label , COUNT(*) AS count FROM     shipping_unit WHERE    last_updated_ts BETWEEN (NOW()- INTERVAL 12 hour) AND NOW() and status='VALIDATED' || status = 'INITIATED' || status = 'TRACKING_NUMBER_GENERATED' GROUP BY HOUR(last_updated_ts)";
	
	public static final String INFORMIX_GET_PENDING_LABEL_BY_HOUR_QUERY = "select (DATE(last_updated_ts)||' '||(last_updated_ts::datetime hour to hour::char(2)) ||':00') AS label,count(id) as count  from shipping_unit WHERE last_updated_ts > (CURRENT - 13 UNITS MONTH) and status = 'VALIDATED' or status= 'INITIATED' or status ='TRACKING_NUMBER_GENERATED' group by 1";

	public static final String GET_PENDING_LOAD_BY_HOUR_QUERY = "SELECT CONCAT(DATE(last_updated_ts),' ',HOUR(last_updated_ts), ':00') AS label , COUNT(*) AS count FROM     shipping_unit WHERE    last_updated_ts BETWEEN  (NOW()- INTERVAL 12 hour) AND NOW() and status='LABEL_CREATED' GROUP BY HOUR(last_updated_ts)";
	
	public static final String INFORMIX_GET_PENDING_LOAD_BY_HOUR_QUERY = "select (DATE(last_updated_ts)||' '||(last_updated_ts::datetime hour to hour::char(2)) ||':00') AS label,count(id) as count  from shipping_unit WHERE last_updated_ts > (CURRENT - 13 UNITS MONTH) and status = 'LABEL_CREATED' group by 1";	

	public static final String GET_PENDING_MANIFEST_BY_HOUR_QUERY = "select CONCAT(DATE(lu.update_ts),' ',HOUR(lu.update_ts), ':00') AS label ,count(lsu.shipping_unit_ref_id) as count from load_unit lu join load_shipping_unit lsu on lu.id=lsu.load_id AND lu.update_ts > (NOW() - INTERVAL 12 hour) and lu.status = 'LOADING' group by HOUR(lu.update_ts)";
	
	public static final String INFORMIX_GET_PENDING_MANIFEST_BY_HOUR_QUERY = "select (DATE(lu.update_ts)||' '||(lu.update_ts::datetime hour to hour::char(2)) ||':00') AS label,count(shipping_unit_ref_id) as count  from load_unit lu join load_shipping_unit lsu on lu.id=lsu.load_id where lu.update_ts > (CURRENT - 13 UNITS MONTH) and lu.status = 'LOADING' group by 1";

	public static final String GET_PENDING_SHIPMENT_DATE_FILTER = "where last_updated_ts between (:fromDate) and (:toDate)";

	public static final String GET_PENDING_LOAD_DATE_FILTER = " where updated_ts between (:fromDate) and (:toDate)";

	public static final String GET_OVERALL_REPRINT_QUERY = "select count(id) from request_log where time_stamp between (:fromDate) and (:toDate) and event_type='Reprint'";

	public static final String GET_ALL_NODE_ID_QUERY = "select id from node";

	public static final String GET_ALL_CARRIER_SERVICE_CODES_QUERY = "select code from carrier_service";

	public static final String GET_ALL_CARRIER_SERVICE_CODES_BY_CARRIER_QUERY = "select code from carrier_service where carrier_id=:carrierId";

	public static final String GET_OVEALL_PENDING_SUMMARY_BY_HOUR_QUERY = "SELECT id,sum(pnd_load) as pending_load,sum(pnd_mfst) as pending_manifest from shipment_summary where create_date between(:fromDate) AND (:toDate)";

	
}
