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
package com.cts.cip.kpi.util;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;

import org.junit.Test;

import com.cts.ip.kpi.util.CommonUtil;


public class CommonUtilTest {
	
	@Test
	public void testConvertDateToId() {
		LocalDateTime localDateTime1  = LocalDateTime.of(2016, 9, 5, 10, 0);
		int id = CommonUtil.toIdFromDateTime(localDateTime1);
		assertEquals(2016090510,id);
		
		LocalDateTime localDateTime2  = LocalDateTime.of(2016, 9, 5, 17, 0);
		id = CommonUtil.toIdFromDateTime(localDateTime2);
		assertEquals(2016090517,id);
		
	}
	
	public void testConvertIdtoDate() {
		
	}
}
