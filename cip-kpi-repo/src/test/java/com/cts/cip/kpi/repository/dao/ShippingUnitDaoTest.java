package com.cts.cip.kpi.repository.dao;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class ShippingUnitDaoTest {
	
	Logger logger = LoggerFactory.getLogger(ShippingUnitDaoTest.class);

	@Mock
	NamedParameterJdbcTemplate paramJdbcTemplate;

	@InjectMocks
	private ShippingUnitDao shippingUnitDao = new ShippingUnitDaoImpl();
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testCount() {
		assert(true);
	}
}
