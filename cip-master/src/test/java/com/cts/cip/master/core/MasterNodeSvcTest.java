package com.cts.cip.master.core;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.cts.cip.common.dto.NodeDetail;
import com.cts.cip.common.dto.NodeInfo;
import com.cts.cip.common.exceptions.SystemFailure;
import com.cts.cip.common.model.StatusResponse;
import com.cts.cip.master.repository.dao.MasterDataService;
import com.cts.cip.master.repository.entities.Node;
import com.cts.cip.master.util.TestDataProvider;

@ContextConfiguration(locations = { "classpath:spring/core-config.xml" })
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class MasterNodeSvcTest {


	Logger logger = LoggerFactory.getLogger(MasterCarrierSvcTest.class);

	@Mock
	MasterDataService masterDataSvc;
	@Mock
	JdbcTemplate template;

	@InjectMocks
	private MasterNodeService masterNodeService = new MasterNodeServiceImpl();
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Test
	public void testFindAllNodes() {
		Mockito.when(masterDataSvc.findAllNodes()).thenReturn(TestDataProvider.getNodeList("Simple"));		
		List<NodeInfo> nodeInfoList = masterNodeService.findAllNodes();
		assertThat(nodeInfoList).isEqualTo(TestDataProvider.getNodeInfoList("Simple"));		
	}
	
	@Test
	public void testfindNodesByPage_1() {
		Mockito.when(masterDataSvc.findNodeByPage(0, 1, null, null)).thenReturn(TestDataProvider.getNodeList("Simple"));		
		List<NodeInfo> nodeInfoList = masterNodeService.findNodesByPage(0, 1, null, null);
		assertThat(nodeInfoList).isEqualTo(TestDataProvider.getNodeInfoList("Simple"));
	}
	
	@Test
	public void testfindNodesByPage_2() {
		Mockito.when(masterDataSvc.findNodeByPage(0, 1, null, null)).thenReturn(new ArrayList<Node>());		
		List<NodeInfo> nodeInfoList = masterNodeService.findNodesByPage(10, 1, null, null);
		assertThat(nodeInfoList.isEmpty());
	}
	
	@Test
	public void testfindNodesByCriteria_1() {
		Mockito.when(masterDataSvc.findNodesByCriteria(TestDataProvider.getNodeSearchCriteria("Simple"), true)).thenReturn(TestDataProvider.getNodeList("Simple"));		
		List<NodeInfo> nodeInfoList = masterNodeService.findNodesByCriteria(TestDataProvider.getNodeSearchCriteria("Simple"),true);
		assertThat(nodeInfoList).isEqualTo(TestDataProvider.getNodeInfoList("Simple"));		
	}
	@Test
	public void testfindNodesByCriteria_2() {
		Mockito.when(masterDataSvc.findNodesByCriteria(TestDataProvider.getNodeSearchCriteria("Simple"), true)).thenReturn(new ArrayList<Node>());		
		List<NodeInfo> nodeInfoList = masterNodeService.findNodesByCriteria(TestDataProvider.getNodeSearchCriteria("Simple"),true);
		assertThat(nodeInfoList.isEmpty());		
	}

	
	@Test
	public void testFindNodeById() {
		Mockito.when(masterDataSvc.findNodeById("1")).thenReturn(TestDataProvider.getNode("Simple"));
		List<String> activeCodes =  new ArrayList<>();
		activeCodes.add("1");
		Mockito.when(masterDataSvc.findActiveServiceCodes()).thenReturn(activeCodes);
		NodeDetail nodeDetail = masterNodeService.findNodeById("1");
		assertThat(nodeDetail).isEqualTo(TestDataProvider.getNodeDetail("Simple"));
	}
	
	@Test
	public void testAddNode_1() {
		Mockito.when(masterDataSvc.addNode(TestDataProvider.getNode("Simple"))).thenReturn("SUCCESS");
		StatusResponse statusResponse = masterNodeService.addNode(TestDataProvider.getNodeDetail("Simple"));
		assertThat(statusResponse.getDescription()).isEqualTo("Added the node changes successfully");
	}
	
	
	@Test
	public void testAddNode_2() {	
		Mockito.when(masterDataSvc.addNode(Matchers.<Node>any())).thenThrow(new DataRetrievalFailureException("Test Message"));		
		StatusResponse statusResponse = masterNodeService.addNode(TestDataProvider.getNodeDetail("Simple"));
		assertThat(statusResponse.getDescription()).isEqualTo("Test Message");
	}
	
	@Test
	public void testUpdateNode_1() {
		Mockito.when(masterDataSvc.findNodeById("1")).thenReturn(null);		
		StatusResponse statusResponse = masterNodeService.updateNode(TestDataProvider.getNodeDetail("Simple"));
		assertThat(statusResponse.getDescription()).isEqualTo("Error: No node was found for the id provided");
		assertThat(statusResponse.getCode().equalsIgnoreCase("002"));
	}
	
	@Test
	public void testUpdateNode_2() {
		Mockito.when(masterDataSvc.findNodeById("0")).thenReturn(TestDataProvider.getNode("Simple"));	
		
		StatusResponse statusResponse = masterNodeService.updateNode(TestDataProvider.getNodeDetail("Simple"));
		assertThat(statusResponse.getDescription()).isEqualTo("Updated the node  changes successfully");
		assertThat(statusResponse.getCode().equalsIgnoreCase("001"));
	}
	
	@Test
	public void testRemoveNode() {		
		StatusResponse statusResponse = masterNodeService.removeNode(TestDataProvider.getNodeDetail("Simple"));
		assertThat(statusResponse).isEqualTo(null);
		
	}
	
	@Test
	public void testFindNodeCount() {
		Mockito.when(masterDataSvc.findNodeCount()).thenReturn(10);			
		int result  =  masterNodeService.findNodeCount();		
		assertThat(result).isEqualTo(10);
	}
	
	
}
