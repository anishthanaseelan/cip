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
package com.cts.cip.kpi.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author 417765
 *
 */
public class CIPKPIUtilities {

	/**
	 * 
	 */
	public CIPKPIUtilities() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method gives the string in terms yyyy-MM-dd hh:mm:ss pattern in UTC
	 * @param dateString
	 * @param isoDTTimeZonePattern
	 * @param isoDTPattern
	 * @return String
	 * @throws ParseException
	 */
	public static String getUTCDateTimeString(String dateString, String isoDTTimeZonePattern, 
			String isoDTPattern)	throws ParseException {
		
		SimpleDateFormat isoDateFormatter = new SimpleDateFormat(isoDTTimeZonePattern);
		isoDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date parsedDate = isoDateFormatter.parse(dateString);
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat(isoDTPattern);
		return dateFormatter.format(parsedDate);
		
	}
	/**
	 * Gets the milliseconds for the given time.
	 * @param dateString
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static long getDateTimeInMillies(String dateString, String pattern)
			throws ParseException {
		
		SimpleDateFormat isoDateFormatter = new SimpleDateFormat(pattern);
		isoDateFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date parsedDate = isoDateFormatter.parse(dateString);
		return parsedDate.getTime();
		
	}
	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String getUTCDateInString(Date date, String pattern) {
		SimpleDateFormat isoDateFormatter = new SimpleDateFormat(pattern);
		return isoDateFormatter.format(date);
	}
}
