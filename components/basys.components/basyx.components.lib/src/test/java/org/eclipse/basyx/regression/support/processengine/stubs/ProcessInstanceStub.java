package org.eclipse.basyx.regression.support.processengine.stubs;

import org.camunda.bpm.engine.impl.pvm.runtime.ExecutionImpl;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.FlowElement;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

/**
 * A process instance stub which is BPMNEngineStub for test purpose
 * 
 * @author zhangzai
 *
 */
public class ProcessInstanceStub extends ExecutionImpl {

	private static final long serialVersionUID = 1L;
	/**
	 * Id of the process instance
	 */
	private String processInstanceId = "process-instance-stub-01";

	/**
	 * process definition id
	 */
	private String getProcessDefinitionId = "process-definition-stub-01";

	/**
	 * Bpmn model instance
	 */
	private BpmnModelInstance modelInstance;

	/**
	 * Get process instance Id
	 */
	@Override
	public String getProcessInstanceId() {
		return processInstanceId;
	}

	/**
	 * Get Process definition Id
	 */
	@Override
	public String getProcessDefinitionId() {
		return getProcessDefinitionId;
	}

	/**
	 * get Bpmn Model instance
	 */
	@Override
	public BpmnModelInstance getBpmnModelInstance() {
		modelInstance = new BPMNModelFactory().create(getProcessDefinitionId);
		return modelInstance;
	}

	@Override
	public String getProcessBusinessKey() {
		return "Process Business Key 01";
	}

	@Override
	public String getCurrentActivityName() {
		return "test-activity-01";
	  }

	/**
	 * Unused functions
	 */
	@Override
	public FlowElement getBpmnModelElementInstance() {
		ModelElementInstance element = modelInstance.getModelElementById("t1");
		return (FlowElement) element;
	}

}
