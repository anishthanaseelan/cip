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
package com.cts.cip.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(locations = { "classpath:spring/test-carrier-config.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class NotificationManagerTest {
	
	@Autowired
	ApplicationContext appContext;
	
	NotificationManager nManager;

	@Before
	public void setUp() throws Exception {
		nManager = (NotificationManager)appContext.getBean("notificationManager");
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendNotification() {
		nManager.sendNotification("Test Message", null, "Test Subject");
		
	}

}
