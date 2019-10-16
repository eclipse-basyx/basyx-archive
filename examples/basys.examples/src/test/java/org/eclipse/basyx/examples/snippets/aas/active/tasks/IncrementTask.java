package org.eclipse.basyx.examples.snippets.aas.active.tasks;

import org.eclipse.basyx.tools.aas.active.VABModelTask;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Task for incrementing a given property in each execution step
 * 
 * @author espen
 *
 */
public class IncrementTask implements VABModelTask {
	private String path;

	public IncrementTask(String path) {
		this.path = path;
	}

	@Override
	public void execute(IModelProvider model) throws Exception {
		int current = (int) model.getModelPropertyValue(path);
		model.setModelPropertyValue(path, current + 1);
	}
}
