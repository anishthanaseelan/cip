package com.cts.cip.master.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.master.mapper.NodeModelMapper;
import com.cts.cip.master.repository.entities.Node;
import com.cts.cip.master.util.TestDataProvider;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class NodeModelMapperTest {
	
	NodeModelMapper nodeModelMapper = new NodeModelMapper();
	
	Logger logger = LoggerFactory.getLogger(NodeModelMapperTest.class);
	
	
	@Test
	public void testToNodeDetail_1() {
		Node node = TestDataProvider.getNode("Simple");
		List<String> activeServiceCodes = new ArrayList<>();
		activeServiceCodes.add("1");
		NodeDetail nodeDetail =  nodeModelMapper.toNodeDetail(node, activeServiceCodes);
		assertThat(nodeDetail.getGlobalLocationNumber()).isEqualTo(node.getGlobalLocationNumber());		
	}
	
	@Test
	public void testToNodeEntity_1() {
		
		NodeDetail nodeDetail =  TestDataProvider.getNodeDetail("ServiceList");
		Node node =  TestDataProvider.getNode("Simple");
		
		Node actualNode = nodeModelMapper.toNodeEntity(nodeDetail,node);
		assertThat(actualNode.getGlobalLocationNumber()).isEqualTo("1234");
	}
	
	@Test
	public void testToNodeEntity_2() {
		
		NodeDetail nodeDetail =  TestDataProvider.getNodeDetail("ServiceList");
		Node node =  TestDataProvider.getNode("Simple");
		node.setNodeCarrierServiceList(null);
		Node actualNode = nodeModelMapper.toNodeEntity(nodeDetail,node);
		assertThat(actualNode.getGlobalLocationNumber()).isEqualTo("1234");
	}


}
