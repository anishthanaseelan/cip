package com.cts.cip.master.Mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.mapper.RestResourceMapper;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class RestResourceMapperTest {
	
	
	
	Logger logger = LoggerFactory.getLogger(RestResourceMapperTest.class);
	
	
	@Test
	public void testBuildSuccessResponse() {
		StatusResponse actualResponse = RestResourceMapper.buildSuccessResponse("TestResource", "Test", "TestType");
		assertThat(actualResponse.getCode()).isEqualTo("001");
		assertThat(actualResponse.getDescription()).isEqualTo("TestType the TestResource changes successfully");
		
	}
	
	@Test
	public void testBuildErrorResponse() {
		StatusResponse actualResponse = RestResourceMapper.buildErrorResponse("ErrorMessage", "002");
		assertThat(actualResponse.getCode()).isEqualTo("002");
		assertThat(actualResponse.getDescription()).isEqualTo("ErrorMessage");
		
	}


}
