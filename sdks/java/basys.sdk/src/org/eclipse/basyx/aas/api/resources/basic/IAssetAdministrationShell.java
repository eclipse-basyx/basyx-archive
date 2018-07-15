package org.eclipse.basyx.aas.api.resources.basic;

import java.util.Map;


/** 
 * Asset Administration Shell (AAS) interface
 *  
 * @author kuhn
 *
 */
public interface IAssetAdministrationShell extends IElementContainer {

	
	/**
	 * Return all registered sub models of this AAS 
	 * 
	 * @return
	 */
	public Map<String, ISubModel> getSubModels();
	
	

	/**
	 * Add a sub model to the AAS
	 * 
	 * @param subModel The added sub model
	 */
	public void addSubModel(ISubModel subModel);
}