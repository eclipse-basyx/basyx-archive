package org.eclipse.basyx.submodel.metamodel.map.helper;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;

/**
 * Contains helper methods of element container
 * @author haque
 *
 */
public class ElementContainerHelper {
	
	public static ISubmodelElement getElementById(Map<String, ISubmodelElement> elements, String id) {
		if (elements != null && elements.containsKey(id)) {
			return elements.get(id);
		}
		throw new ResourceNotFoundException("Submodel Element with id: " + id + " does not exist");
	}
	
	public static void removeElementById(Map<String, ISubmodelElement> elements, String id) {
		if (elements != null && elements.containsKey(id)) {
			elements.remove(id);
			return;
		}
		throw new ResourceNotFoundException("Submodel Element with id: " + id + " does not exist");
	}
}
