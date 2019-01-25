package org.eclipse.basyx.aas.impl.tools;

import org.eclipse.basyx.aas.api.exception.ResourceNotFoundException;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.api.resources.ISubModel;

/**
 * Element utility functions
 * 
 * @author kuhn
 *
 */
public class ElementTools {

	
	/**
	 * Get AAS ID of this element
	 */
	public static String getAASID(IElement element) {
		// Temporary variables
		IElement el = element;
		
		// Look for AAS in hierarchy
		// while ((!(el instanceof IAssetAdministrationShell)) && (el != null)) el = el.getParent(); uncomment until new DAAS MetaModel is implemented
		
		// Get ID
		if (el != null) return el.getId();
		
		// Element has no AAS ID
		return "";
	}


//	/**
//	 * Get sub model ID of this element - uncomment until new DAAS MetaModel is implemented
//	 */
//	public static String getSubmodelID(IElement element) {
//		// Temporary variables
//		IElement el = element;
//		
//		// Look for AAS in hierarchy
//		while ((!(el instanceof ISubModel)) && (el != null)) el = el.getParent(); 
//		
//		// Get ID
//		if (el != null) return el.getId();
//		
//		// Indicate error
//		throw new ResourceNotFoundException(element.getId());
//	}
	
	
//	/**
//	 * Get the full path to an property - uncomment until new DAAS MetaModel is implemented
//	 */
//	public static String getFullPathToProperty(IElement element) {
//		// Return value
//		StringBuilder result = new StringBuilder();
//		
//		// Temporary variables
//		IElement el = element;
//		
//		// Create path to property
//		while ((!(el instanceof ISubModel)) && (el != null)) {
//			if (result.length() > 0) result.insert(0, "/");
//			
//			result.insert(0, el.getId());
//			
//			el = el.getParent();
//		}
//		
//		// Return path
//		return result.toString();
//	}
}

