package org.eclipse.basyx.regression.support.processengine.stubs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.activiti.bpmn.model.ActivitiListener;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.EndEvent;
import org.activiti.bpmn.model.ExclusiveGateway;
import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.Gateway;
import org.activiti.bpmn.model.ImplementationType;
import org.activiti.bpmn.model.SequenceFlow;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.bpmn.model.StartEvent;
import org.activiti.bpmn.model.UserTask;
import org.eclipse.basyx.aas.backend.http.tools.GSONTools;
import org.eclipse.basyx.aas.backend.http.tools.factory.DefaultTypeFactory;
import org.eclipse.basyx.components.processengine.connector.DeviceServiceDelegate;
import org.activiti.bpmn.model.Process;


public class BPMNModelFactory {
	
	private static final String TASKI_MPL = "org.eclipse.basyx.components.processengine.connector.DeviceServiceDelegate";
	
	private List<ServiceTask> services = new ArrayList<>();
	
	public BpmnModel create(String processId) {
		return create( processId, TASKI_MPL);
	}
	
	
	
	private BpmnModel create(String processId, String impl) {
		BpmnModel model = new BpmnModel();
	    Process process = new Process();
	    model.addProcess(process);
	    process.setId(processId);
	
	   
	    

	    
	    process.addFlowElement(createStartEvent());
	    
	    ServiceTask task1 = createServiceTask("t1", "pickup the coil", impl, "liftTo", "coilcar", new Object[]{1});
	    services.add(task1);
	    
	    
	    ServiceTask task2 =createServiceTask("t2", "move to the coil", impl, "moveTo", "coilcar", new Object[]{1});
	    services.add(task2);
	    
	    ServiceTask task3 = createServiceTask("t3", "drive the coil to the milling machine", impl, "moveTo", "coilcar", new Object[]{5});
	    services.add(task3);
	    
	    ServiceTask task4 = createServiceTask("t4", "lift the coil to the expected position", impl, "liftTo", "coilcar", new Object[]{6});
	    services.add(task4);
	    
	    ServiceTask task5 =createServiceTask("t5", "put the coil on the mandrel", impl, "moveTo", "coilcar", new Object[]{6});
	    services.add(task5);
	    
	    ServiceTask task6 = createServiceTask("t6", "set the lifter to the start position", impl, "liftTo", "coilcar", new Object[]{0});
	    services.add(task6);
	    
	    ServiceTask task7 =createServiceTask("t7", "drive the coilcar back to the start position", impl, "moveTo", "coilcar", new Object[]{0});
	    services.add(task7);
	    
	   
	    for(ServiceTask t : services) {
	    	process.addFlowElement(t);
	    	//t.setExecutionListeners(executionListeners);
	    }
	  
	    
	    process.addFlowElement(createGateway("gateway1", "check the current position of the coil"));
	    
	    process.addFlowElement(createEndEvent());
	    
	    
	    
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
	
	protected Gateway createGateway(String gwid, String gwname) {
		Gateway gw = new ExclusiveGateway();
		gw.setId(gwid);
		gw.setName(gwname);
		return gw;
	}
	
	protected SequenceFlow createSequenceFlowWithCondition(String from, String to, String conditionExpression) {
	    SequenceFlow flow = new SequenceFlow();
	    flow.setSourceRef(from);
	    flow.setTargetRef(to);
	    flow.setConditionExpression(conditionExpression);
	    return flow;
    }
    protected UserTask createUserTask(String id, String name, String assignee) {
    	UserTask userTask = new UserTask();
    	userTask.setName(name);
    	userTask.setId(id);
    	userTask.setAssignee(assignee);
    	return userTask;
    }
  
    protected SequenceFlow createSequenceFlow(String from, String to) {
	    SequenceFlow flow = new SequenceFlow();
	    flow.setSourceRef(from);
	    flow.setTargetRef(to);
	    return flow;
    }
  
    protected StartEvent createStartEvent() {
	    StartEvent startEvent = new StartEvent();
	    startEvent.setId("start");
	    return startEvent;
    }
  
    protected EndEvent createEndEvent() {
	    EndEvent endEvent = new EndEvent();
	    endEvent.setId("end");
	    return endEvent;
    }
    public ServiceTask createServiceTask(String taskid, String taskName, String impl, String serviceName, String serviceProvider, Object[]  params) {
		ServiceTask serviceTask = new ServiceTask();
	    serviceTask.setImplementation(impl);
	    serviceTask.setImplementationType("class");
	    serviceTask.setId(taskid);
	    serviceTask.setName(taskName);
	    
	    List<FieldExtension> fes = new ArrayList<>();
	    fes.add(createFieldExtension("serviceName", serviceName));
	    fes.add(createFieldExtension("serviceProvider", serviceProvider));
	    fes.add(createFieldExtension("serviceParameter", generateJsonString(params)));
	    serviceTask.setFieldExtensions(fes);
	 
	    return serviceTask;
	}
	
	
	
	private FieldExtension createFieldExtension(String fname, String fexpression) {
 	 FieldExtension snf = new FieldExtension();
	    snf.setFieldName(fname);
	    snf.setExpression(fexpression);
	    return snf;
	}
	
	private String generateJsonString(Object[] params) {
		GSONTools gson = new GSONTools(new DefaultTypeFactory());
		String to = gson.serialize(new ArrayList<Object>(Arrays.asList(params)));
		return to;
	}
}
