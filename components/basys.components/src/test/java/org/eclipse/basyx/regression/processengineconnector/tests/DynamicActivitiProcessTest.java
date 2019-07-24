package org.eclipse.basyx.regression.processengineconnector.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.BpmnAutoLayout;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.apache.commons.io.FileUtils;
import org.eclipse.basyx.components.processengine.connector.DeviceServiceDelegate;
import org.eclipse.basyx.regression.support.processengine.executor.ExecutionSequence;
import org.eclipse.basyx.regression.support.processengine.stubs.BPMNModelFactory;
import org.eclipse.basyx.regression.support.processengine.stubs.DeviceServiceExecutorStub;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * Test the BPMN-Model created programically, verify the execution sequence of the tasks 
 * 
 * @author zhangzai
 *
 */
public class DynamicActivitiProcessTest {
	
	/**
	 * Process isntance to be executed
	 */
	ProcessInstance processInstance;
	
	/**
	 * Deployment of the process engine 
	 */
	Deployment deployment;
	
	/**
	 * configure the process engine before execution 
	 */
	  @Rule
	  public ActivitiRule activitiRule = new ActivitiRule(new StandaloneProcessEngineConfiguration()
				.setJdbcUrl("jdbc:h2:mem:activiti;DB_CLOSE_DELAY=1000").setJdbcUsername("test").setJdbcPassword("test")
				.setJdbcDriver("org.h2.Driver")
				.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE)
				.setAsyncExecutorActivate(false)
				.buildProcessEngine());
	  
	  /**
	   * Depoly the process 
	   */
	  @Before
	  public void deploy() {
		  // 1. Build up the model from scratch
		  BpmnModel model = new BPMNModelFactory().create("my-process");
		  DeviceServiceDelegate.setDeviceServiceExecutor(new DeviceServiceExecutorStub());
		  
		  // 2. Generate graphical information
		  new BpmnAutoLayout(model).execute();
		    
		  // 3. Deploy the process to the engine 
		  deployment = activitiRule.getRepositoryService().createDeployment()
		        .addBpmnModel("dynamic-model.bpmn", model).name("Dynamic process deployment").deploy();
	  }
	  
  /**
   * Test the first branch of the BPMN-process, verify the execution sequence
   * 	  
   * @throws Exception
   */
  @Test
  public void testDynamicDeployPath1() throws Exception {
	  
	  // 4. Start a process instance
      Map<String, Object> variables = new HashMap<String, Object>();
	  variables.put("coilposition", 1);
      processInstance = activitiRule.getRuntimeService()
        .startProcessInstanceByKey("my-process", variables);
      
      // 5. Check if task is available
      HistoryService history = activitiRule.getHistoryService();
	  List<HistoricActivityInstance> activitiInstances = history.createHistoricActivityInstanceQuery().list();
	
	assertEquals(ExecutionSequence.expectedSequence1[0], activitiInstances.get(0).getActivityId());
	assertEquals(ExecutionSequence.expectedSequence1[1], activitiInstances.get(1).getActivityId());
	assertEquals(ExecutionSequence.expectedSequence1[2], activitiInstances.get(2).getActivityId());
	assertEquals(ExecutionSequence.expectedSequence1[3], activitiInstances.get(3).getActivityId());
	assertEquals(ExecutionSequence.expectedSequence1[4], activitiInstances.get(4).getActivityId());
	assertEquals(ExecutionSequence.expectedSequence1[5], activitiInstances.get(5).getActivityId());
	assertEquals(ExecutionSequence.expectedSequence1[6], activitiInstances.get(6).getActivityId());
	
  }
    
    	
  
  @Test
  public void testDynamicDeployPath2() throws Exception {
		// 4. Start a process instance
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("coilposition", 2);
		processInstance = activitiRule.getRuntimeService()
		    .startProcessInstanceByKey("my-process", variables);
		// 5. Check if task is available
		HistoryService history = activitiRule.getHistoryService();
		List<HistoricActivityInstance> activitiInstances = history.createHistoricActivityInstanceQuery().list();
		
		assertEquals(ExecutionSequence.expectedSequence2[0], activitiInstances.get(9).getActivityId());
		assertEquals(ExecutionSequence.expectedSequence2[1], activitiInstances.get(10).getActivityId());
		assertEquals(ExecutionSequence.expectedSequence2[2], activitiInstances.get(11).getActivityId());
		assertEquals(ExecutionSequence.expectedSequence2[3], activitiInstances.get(12).getActivityId());
		assertEquals(ExecutionSequence.expectedSequence2[4], activitiInstances.get(13).getActivityId());
		assertEquals(ExecutionSequence.expectedSequence2[5], activitiInstances.get(14).getActivityId());
		assertEquals(ExecutionSequence.expectedSequence2[6], activitiInstances.get(15).getActivityId());
		assertEquals(ExecutionSequence.expectedSequence2[7], activitiInstances.get(16).getActivityId());
	
  }
  

    
    @After
    public void generateOutputFiles() throws IOException {
	    // 6. Save process diagram to a file  
	    InputStream processDiagram = activitiRule.getRepositoryService().getProcessDiagram(processInstance.getProcessDefinitionId());
	    FileUtils.copyInputStreamToFile(processDiagram, new File("target/diagram.png"));
	    
	    // 7. Save resulting BPMN xml to a file
	    InputStream processBpmn = activitiRule.getRepositoryService().getResourceAsStream(deployment.getId(), "dynamic-model.bpmn");
	    FileUtils.copyInputStreamToFile(processBpmn, new File("target/process.bpmn20.xml"));
  }
  
 
}