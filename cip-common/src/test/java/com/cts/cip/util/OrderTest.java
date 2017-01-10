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
package com.cts.cip.util;

import static org.junit.Assert.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.Test;

public class OrderTest {

	private static Validator validator;

	@BeforeClass
	public static void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testOrderNotNull() {
		Order order = new Order();
		order.setOrderNumber(null);
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		assertEquals(1, constraintViolations.size());
		assertEquals("may not be null", constraintViolations.iterator().next().getMessage());
	}
	
	@Test
	public void testOrderWithSpecialChar() {
		Order order = new Order();
		order.setOrderNumber("123");
		order.setShipViaCode("213-ABC");
		Set<ConstraintViolation<Order>> constraintViolations = validator.validate(order);
		assertEquals(1, constraintViolations.size());
		System.out.println(constraintViolations.iterator().next().getInvalidValue());
		
	}
	
	

}
