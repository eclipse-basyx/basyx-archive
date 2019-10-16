package org.eclipse.basyx.regression.support.processengine.stubs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.Gateway;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.StartEvent;
import org.eclipse.basyx.vab.coder.json.serialization.DefaultTypeFactory;
import org.eclipse.basyx.vab.coder.json.serialization.GSONTools;
import org.activiti.bpmn.model.Process;

/**
 * A Factory that produces a defined BPMN-Model programically
 * 
 * @author zhangzai
 *
 */
public class BPMNModelFactory {
	// Path to the java class invoked by the process engine
	private static final String TASKI_MPL = "org.eclipse.basyx.components.processengine.connector.DeviceServiceDelegate";
	
	// All service tasks executed by the process-engine
	private List<ServiceTask> services = new ArrayList<>();
	
	/***
	 * Create the BPMN-Model and -Process
	 * @param processId 
	 * 				Id of the process
	 * @return
	 */
	public BpmnModel create(String processId) {
		return create( processId, TASKI_MPL);
	}
	
	
	/**
	 * Create the BPMN-Model and -process with specified process-ID and path to the java-delegate
	 * @param processId
	 * @param impl
	 * @return
	 */
	private BpmnModel create(String processId, String impl) {
		// Create the bpmn-model
		BpmnModel model = new BpmnModel();
		
		// Create the bpmn-process
	    Process process = new Process();
	    
	    // Add process to the model
	    model.addProcess(process);
	    
	    // Set process-ID
	    process.setId(processId);
	
	    // Add start event
	    process.addFlowElement(createStartEvent());
	    
	    // Create task 
	    ServiceTask task1 = createServiceTask("t1", "pickup the coil", impl, "liftTo", "coilcar", new Object[]{1});
	    services.add(task1);
	    
	    // Create task 
	    ServiceTask task2 =createServiceTask("t2", "move to the coil", impl, "moveTo", "coilcar", new Object[]{1});
	    services.add(task2);
	    
	    // Create task 
	    ServiceTask task3 = createServiceTask("t3", "drive the coil to the milling machine", impl, "moveTo", "coilcar", new Object[]{5});
	    services.add(task3);
	    
	    // Create task
	    ServiceTask task4 = createServiceTask("t4", "lift the coil to the expected position", impl, "liftTo", "coilcar", new Object[]{6});
	    services.add(task4);
	    
	    // Create task
	    ServiceTask task5 =createServiceTask("t5", "put the coil on the mandrel", impl, "moveTo", "coilcar", new Object[]{6});
	    services.add(task5);
	    
	    // Create task
	    ServiceTask task6 = createServiceTask("t6", "set the lifter to the start position", impl, "liftTo", "coilcar", new Object[]{0});
	    services.add(task6);
	    
	    // Create task
	    ServiceTask task7 =createServiceTask("t7", "drive the coilcar back to the start position", impl, "moveTo", "coilcar", new Object[]{0});
	    services.add(task7);
	    
	   // Add tasks to the process
	    for(ServiceTask t : services) {
	    	process.addFlowElement(t);
	    }
	  
	    // Add an exclusive gate to the process
	    process.addFlowElement(createGateway("gateway1", "check the current position of the coil"));
	    
	    // Add end event to the process
	    process.addFlowElement(createEndEvent());
	    
	    
	    // Connect the elements with links
	    process.addFlowElement(createSequenceFlow("start", "gateway1"));
	    process.addFlowElement(createSequenceFlowWithCondition("gateway1", "t1", "${coilposition==1}"));
	    process.addFlowElement(createSequenceFlowWithCondition("gateway1", "t2", "${coilposition==2}"));
	    process.addFlowElement(createSequenceFlow("t1", "t3"));
	    process.addFlowElement(createSequenceFlow("t2", "t1"));
	    process.addFlowElement(createSequenceFlow("t3", "t4"));
	    process.addFlowElement(createSequenceFlow("t4", "t5"));
	    process.addFlowElement(createSequenceFlow("t5", "t6"));
	    process.addFlowElement(createSequenceFlow("t6", "t7"));
	    process.addFlowElement(createSequenceFlow("t7", "end"));
	    
	    
	    return model;
	}
	
	/**
	 * Create an exclusive gateway
	 * @param gwid	
	 * 				id of the gateway
	 * @param gwname
	 * 				name of the gateway
	 * @return
	 */
	protected Gateway createGateway(String gwid, String gwname) {
		Gateway gw = new ExclusiveGateway();
		gw.setId(gwid);
		gw.setName(gwname);
		return gw;
	}
	
	
	/**
	 * Create a conditional link between two elements 
	 * @param from
	 * 				source of the link
	 * @param to
	 * 				gain of the link
	 * @param conditionExpression
	 * 				Condition that must be valid for this link
	 * @return
	 */
	protected SequenceFlow createSequenceFlowWithCondition(String from, String to, String conditionExpression) {
	    SequenceFlow flow = new SequenceFlow();
	    flow.setSourceRef(from);
	    flow.setTargetRef(to);
	    flow.setConditionExpression(conditionExpression);
	    return flow;
    }
	

	/**
	 * Create a simple link without condition
	 * @param from -- source
	 * @param to -- gain
	 * @return
	 */
    protected SequenceFlow createSequenceFlow(String from, String to) {
	    SequenceFlow flow = new SequenceFlow();
	    flow.setSourceRef(from);
	    flow.setTargetRef(to);
	    return flow;
    }
  
    
    /**
     * Create a start event
     * @return start event
     */
    protected StartEvent createStartEvent() {
	    StartEvent startEvent = new StartEvent();
	    startEvent.setId("start");
	    return startEvent;
    }
  
    /**
     * Create an end event
     * @return end event
     */
    protected EndEvent createEndEvent() {
	    EndEvent endEvent = new EndEvent();
	    endEvent.setId("end");
	    return endEvent;
    }
    
    /**
     * Create a service task with java-delegate
     * @param taskid	-- id of the task
     * @param taskName 	-- name of the task
     * @param impl		-- path to the java-deleate class
     * @param serviceName	-- name of the service
     * @param serviceProvider	-- resource that executes the service
     * @param params	-- parameters required by the service
     * @return
     */
    public ServiceTask createServiceTask(String taskid, String taskName, String impl, String serviceName, String serviceProvider, Object[]  params) {
		// Create the service task
    	ServiceTask serviceTask = new ServiceTask();
    	
    	// Add java-delegate class to the task
	    serviceTask.setImplementation(impl);
	    serviceTask.setImplementationType("class");
	    
	    // Set task id
	    serviceTask.setId(taskid);
	    // Set task name
	    serviceTask.setName(taskName);
	    
	    // Add field extensions: serviceName, serviceProvider, serviceParameter, set the value of them
	    List<FieldExtension> fes = new ArrayList<>();
	    fes.add(createFieldExtension("serviceName", serviceName));
	    fes.add(createFieldExtension("serviceProvider", serviceProvider));
	    fes.add(createFieldExtension("serviceParameter", generateJsonString(params)));
	    
	    // Set field extension to this task
	    serviceTask.setFieldExtensions(fes);
	 
	    return serviceTask;
	}
	
	
	/***
	 * Create field extension. This is used to exchange data between a java-delegate and a bpmn-model
	 * @param fname	-- Name of the field
	 * @param fexpression	-- value of the field
	 * @return
	 */
	private FieldExtension createFieldExtension(String fname, String fexpression) {
 	 FieldExtension snf = new FieldExtension();
	    snf.setFieldName(fname);
	    snf.setExpression(fexpression);
	    return snf;
	}
	
	/**
	 * Generate JSON String of a parameter array 
	 * @param params
	 * @return
	 */
	private String generateJsonString(Object[] params) {
		// Create serializer using BaSys SDK 
		GSONTools gson = new GSONTools(new DefaultTypeFactory());
		
		// serialize array
		String to = gson.serialize(new ArrayList<Object>(Arrays.asList(params)));
		return to;
	}
}
