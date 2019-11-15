package org.eclipse.basyx.tools.aas.active;

import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Abstract task that can be executed and works on an {@link IModelProvider}
 * 
 * @author espen, schnicke
 *
 */
public interface VABModelTask {
	/**
	 * Executes the task a single time on a passed model provider
	 * 
	 * @param model
	 *            the provider to be worked on
	 * @throws Exception
	 */
	public void execute(IModelProvider model) throws Exception;
}
