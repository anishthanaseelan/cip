package com.cts.cip.master.Mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cts.cip.common.model.DocumentTemplate;
import com.cts.cip.common.model.ShippingDocumentContentType;
import com.cts.cip.common.model.ShippingDocumentType;
import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.mapper.RestResourceMapper;
import com.cts.cip.master.mapper.TemplateModelMapper;
import com.cts.cip.master.repository.entities.Template;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class TemplateModelMapperTest {
	
	
	
	Logger logger = LoggerFactory.getLogger(TemplateModelMapperTest.class);
	TemplateModelMapper templateModelMapper = new TemplateModelMapper();
	
	
	@Test
	public void testToTemplateEntity() {
		DocumentTemplate documentTemplate = new DocumentTemplate();
		documentTemplate.setId(1);
		documentTemplate.setOutputContentType(ShippingDocumentContentType.PDF);
		documentTemplate.setTemplateType(ShippingDocumentType.INVOICE_WITH_LABEL);
		documentTemplate.setTemplatePath("TESTPATH");
		
		Template actualTemplate = templateModelMapper.toTemplateEntity(documentTemplate);
		assertThat(actualTemplate.getTemplatePath()).isEqualTo("TESTPATH");		
	}
	
	@Test
	public void testToDocTemplate() {
		Template template =  new Template();
		template.setTemplatePath("TESTPATH");
		template.setId(1);
		template.setName("TESTNAME");
		template.setTemplateOutputFormat("PDF");
		template.setTemplateDocumentType("INVOICE");
		DocumentTemplate documentTemplate = templateModelMapper.toDocTemplate(template);
		assertThat(documentTemplate.getTemplatePath()).isEqualTo("TESTPATH");
		
	}


}
