package org.eclipse.basyx.vab.provider.xml.transformers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.Property;

/**
 * Returns HashSet<Property> Object for the Map with <aas:submodelElements>
 * 
 * @author rajashek
 *
 */
public class TransformProperty {

	/**
	 * The function accepts the Map object submodelElement tag and returns the
	 * HashSet of property class
	 */
	@SuppressWarnings("unchecked")
	public static HashSet<Property> transformProperty(Map<String, Object> object) {
		HashSet<Property> propertySet = new HashSet<Property>();
		Map<String, Object> submodelElementsObj = (Map<String, Object>) object.get("aas:submodelElements");
		Object submodelElementObj = submodelElementsObj.get("aas:submodelElement");
		// We need to check if submodelElementObj is instance of MAP object or
		// Collection and process them accordingly
		if (submodelElementObj instanceof Collection<?>) {
			ArrayList<Object> submodelElementArrayList = (ArrayList<Object>) submodelElementObj;
			for (Object object2 : submodelElementArrayList) {
				handlesubModelelementObject(propertySet, object2);
			}
		} else {
			handlesubModelelementObject(propertySet, submodelElementObj);
		}

		return propertySet;
	}

	/**
	 * The function accepts each SubmodelementObject and populates the propertyset
	 */
	@SuppressWarnings("unchecked")
	private static void handlesubModelelementObject(HashSet<Property> propertySet, Object object2) {
		Map<String, Object> subModelElemObj = (Map<String, Object>) object2;
		Object propertObj = subModelElemObj.get("aas:property");
		// We need to check if propertObj is instance of MAP object or Collection and
		// process them accordingly
		if (propertObj instanceof Collection<?>) {
			ArrayList<Object> propertyArrayList = (ArrayList<Object>) propertObj;
			for (Object object3 : propertyArrayList) {
				populatePropertySet(propertySet, object3);
			}

		} else {
			populatePropertySet(propertySet, propertObj);
		}
	}

	/**
	 * The function accepts each property Map object and populates the propertyset
	 */
	@SuppressWarnings("unchecked")
	private static void populatePropertySet(HashSet<Property> propertySet, Object object3) {
		Map<String, Object> PropertyObj = (Map<String, Object>) object3;
		// read corresponding tags and populate the Property object
		String idShort = (String) PropertyObj.get("aas:idShort");
		String category = (String) PropertyObj.get("aas:category");
		HasSemantics transformHasSemanticsObj = TransformHasSemantics.transformHasSemantics(PropertyObj);
		String value = (String) PropertyObj.get("aas:value");
		Property prop = new Property();
		prop.put("semanticId", transformHasSemanticsObj);
		prop.setIdshort(idShort);
		prop.setCategory(category);
		prop.set(value);
		propertySet.add(prop);
	}
}
