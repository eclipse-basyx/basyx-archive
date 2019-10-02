package org.eclipse.basyx.regression.processengineconnector.tests;


import static org.junit.Assert.assertEquals;

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
		processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", variables);

		// 5. Check if task is available
		HistoryService history = activitiRule.getHistoryService();
		List<HistoricActivityInstance> activitiInstances = history.createHistoricActivityInstanceQuery().list();

		// Check, if the last executed activities are correct
		String[] expectedSequence = new String[] { "t1", "t3", "t4", "t5", "t6", "t7", "end" };
		for (int i = 0; i < expectedSequence.length; i++) {
			// -2: # of gateway activities
			int actualIndex = activitiInstances.size() - expectedSequence.length + i - 2;
			assertEquals(expectedSequence[i], activitiInstances.get(actualIndex).getActivityId());
		}
	}
	  
	@Test
	public void testDynamicDeployPath2() throws Exception {
		// 4. Start a process instance
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("coilposition", 2);
		processInstance = activitiRule.getRuntimeService().startProcessInstanceByKey("my-process", variables);
		// 5. Check if task is available
		HistoryService history = activitiRule.getHistoryService();
		List<HistoricActivityInstance> activitiInstances = history.createHistoricActivityInstanceQuery().list();

		// Check, if the last executed activities are correct
		String[] expectedSequence = new String[] { "t2", "t1", "t3", "t4", "t5", "t6", "t7", "end" };
		for (int i = 0; i < expectedSequence.length; i++) {
			// -2: # of gateway activities
			int actualIndex = activitiInstances.size() - expectedSequence.length + i - 2;
			assertEquals(expectedSequence[i], activitiInstances.get(actualIndex).getActivityId());
		}
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