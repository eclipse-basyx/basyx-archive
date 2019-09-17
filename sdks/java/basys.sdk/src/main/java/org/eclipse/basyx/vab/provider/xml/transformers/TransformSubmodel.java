package org.eclipse.basyx.vab.provider.xml.transformers;

import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.haskind.HasKind;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.Property;

/**
 * Returns SubModel Object for the Map with <aas:submodels>
 * 
 * @author rajashek
 *
 */
public class TransformSubmodel {

	/**
	 * The function accepts the Map object of submodel tag and returns the object of
	 * class SubModel
	 */
	public static SubModel transformSubmodel(Map<String, Object> object) {

		Identifiable transformIdentifier = TransformIdentifiable.transformIdentifier(object);
		HasKind hasKindObj = TransformHasKind.transformHasKind(object);
		HasSemantics transformHasSemanticsObj = TransformHasSemantics.transformHasSemantics(object);
		HashSet<Property> transformPropertySet = TransformProperty.transformProperty(object);
		SubModel submodelobj = new SubModel();
		submodelobj.putAll(transformIdentifier);
		submodelobj.putAll(hasKindObj);
		submodelobj.putAll(transformHasSemanticsObj);
		for (Property property : transformPropertySet) {
			submodelobj.addSubModelElement(property);
		}
		System.out.println();

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
			System.out.println("Error with Maps");
		}
		return null;
	}

}
