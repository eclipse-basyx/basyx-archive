package org.eclipse.basyx.tools.aas.active;

import org.eclipse.basyx.vab.core.IModelProvider;

public interface VABModelTask {
	public void execute(IModelProvider model) throws Exception;
}
