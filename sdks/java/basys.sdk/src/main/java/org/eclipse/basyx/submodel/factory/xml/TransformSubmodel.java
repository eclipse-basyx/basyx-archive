package org.eclipse.basyx.submodel.factory.xml;

import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.haskind.HasKind;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Returns SubModel Object for the Map with <aas:submodels>
 * 
 * @author rajashek
 *
 */
public class TransformSubmodel {
	
	private static Logger logger = LoggerFactory.getLogger(TransformSubmodel.class);

	/**
	 * The function accepts the Map object of submodel tag and returns the object of
	 * class SubModel
	 */
	public static SubModel transformSubmodel(Map<String, Object> object) {

		Identifiable transformIdentifier = TransformIdentifiable.transformIdentifier(object);
		HasKind hasKindObj = TransformHasKind.transformHasKind(object);
		HasSemantics transformHasSemanticsObj = TransformHasSemantics.transformHasSemantics(object);
		HashSet<SingleProperty> transformPropertySet = TransformProperty.transformProperty(object);
		SubModel submodelobj = new SubModel();
		submodelobj.putAll(transformIdentifier);
		submodelobj.putAll(hasKindObj);
		submodelobj.putAll(transformHasSemanticsObj);
		for (SingleProperty property : transformPropertySet) {
			submodelobj.addSubModelElement(property);
		}

		return submodelobj;

	}

	/**
	 * Helper Function to get the respective object from the root object created by
	 * xmlparser
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSubmodelFromRootObj(Map<String, Object> rootObj) {
		try {
			return (Map<String, Object>) ((Map<String, Object>) rootObj.get("aas:aasenv")).get("aas:submodels");
		} catch (Exception e) {
			logger.error("Exception in getSubmodelFromRootObj", e);
		}
		return null;
	}

}
