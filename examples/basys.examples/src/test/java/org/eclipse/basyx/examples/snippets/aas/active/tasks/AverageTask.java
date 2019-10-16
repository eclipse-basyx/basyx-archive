package org.eclipse.basyx.examples.snippets.aas.active.tasks;

import org.eclipse.basyx.tools.aas.active.VABModelTask;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Task for computing the average value from a source property
 * 
 * @author espen
 *
 */
public class AverageTask implements VABModelTask {
	private float factor;
	private String sourcePath;
	private String targetPath;
	private double lastAverage;

	public AverageTask(float factor, String sourcePath, String targetPath) {
		this.factor = factor;
		this.sourcePath = sourcePath;
		this.targetPath = targetPath;
	}

	@Override
	public void execute(IModelProvider model) throws Exception {
		double nextValue = (double) model.getModelPropertyValue(sourcePath);
		double average = lastAverage;
		if ( average == 0 ) {
			average = nextValue;
		} else {
			average = average * (1 - factor) + nextValue * factor;
		}
		model.setModelPropertyValue(targetPath, average);
		lastAverage = average;
	}
}
