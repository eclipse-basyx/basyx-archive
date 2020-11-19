package org.eclipse.basyx.submodel.metamodel.facade;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;

/**
 * Helperclass for getting the /values Map from a SubModel.
 * 
 * @author conradi
 *
 */
public class SubmodelValuesHelper {

	/**
	 * Gets the Values from a SubModel
	 * 
	 * @param sm the SubModel to get the values from.
	 * @return A Map mapping idShort to the value of the SubmodelElement
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSubmodelValue(SubModel sm) {
		Map<String, ISubmodelElement> elements = sm.getSubmodelElements();
		
		return (Map<String, Object>) handleValue(elements.values());
	}
	
	
	@SuppressWarnings("unchecked")
	private static Object handleValue(Object value) {
		if(value instanceof Collection<?>) {
			return handleValueCollection((Collection<ISubmodelElement>) value);
		} else {
			// The value is not a collection -> return it as is
			return value;
		}
	}


	private static Map<String, Object> handleValueCollection(Collection<ISubmodelElement> collection) {
		Map<String, Object> ret = new HashMap<>();
		for(ISubmodelElement element: collection) {
			try {
				ret.put(element.getIdShort(), handleValue(element.getValue()));
			} catch (UnsupportedOperationException e) {
				// this Element has no value (e.g. an Operation)
				// -> just ignore it
			}
		}
		return ret;
	}
	
}
