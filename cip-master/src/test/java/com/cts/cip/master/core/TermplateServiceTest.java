package com.cts.cip.master.core;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cts.cip.common.model.DocumentTemplate;
import com.cts.cip.master.repository.dao.MasterDataService;
import com.cts.cip.master.repository.entities.Template;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class TermplateServiceTest {
	
	Logger logger = LoggerFactory.getLogger(TermplateServiceTest.class);

	@Mock
	MasterDataService masterDataService;

	@InjectMocks
	private TemplateService templateService = new TemplateServiceImpl();
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testFindTemplatesByCarrierService() {
		List<Template> templateList = new ArrayList<>();
		Template template = new Template();
		template.setId(1);
		template.setName("TEST");
		template.setDescription("TESTDESCRIPTION");
		template.setTemplateOutputFormat("PDF");
		template.setTemplateDocumentType("INVOICE");
		templateList.add(template);
		Mockito.when(masterDataService.findTemplatesByCarrierService(1)).thenReturn(templateList);		
		List<DocumentTemplate> templateDocumentList =  templateService.findTemplatesByCarrierService(1);
		assertThat(templateDocumentList.isEmpty());
		
	}


}
