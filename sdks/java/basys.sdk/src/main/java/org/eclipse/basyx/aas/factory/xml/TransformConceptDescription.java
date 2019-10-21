package org.eclipse.basyx.aas.factory.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDescription;
import org.eclipse.basyx.submodel.factory.xml.TransformHasDataSpecification;
import org.eclipse.basyx.submodel.factory.xml.TransformIdentifiable;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Identifiable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Returns ConceptDescription Object for the Map with <aas:conceptDescription>
 * 
 * @author rajashek
 *
 */
public class TransformConceptDescription {
	
	private static Logger logger = LoggerFactory.getLogger(TransformConceptDescription.class);

	/**
	 * The function accepts the Map of conceptDescription and returns object of
	 * ConceptDescription
	 */
	@SuppressWarnings("unchecked")
	public static ConceptDescription transformConceptDescription(Map<String, Object> object) {
		ConceptDescription obj = new ConceptDescription();
		Identifiable transformIdentifier = TransformIdentifiable.transformIdentifier(object);
		obj.putAll(transformIdentifier);
		Object embeddedDataSpecObj = object.get("aas:embeddedDataSpecification");
		// We need to check if embeddedDataSpecObj is instance of MAP object or
		// Collection and process them accordingly
		if (embeddedDataSpecObj instanceof Collection<?>) {
			ArrayList<Object> embeddedDataSpecArrayList = (ArrayList<Object>) embeddedDataSpecObj;
			for (Object object1 : embeddedDataSpecArrayList) {
				Map<String, Object> embeddedDataSpecMapObj = (Map<String, Object>) object1;
				obj.putAll(TransformHasDataSpecification.transformHasDataSpecification(embeddedDataSpecMapObj));
			}
		} else {
			Map<String, Object> embeddedDataSpecMapObj = (Map<String, Object>) embeddedDataSpecObj;
			obj.putAll(TransformHasDataSpecification.transformHasDataSpecification(embeddedDataSpecMapObj));
		}

		return obj;
	}

	/**
	 * Helper Function to get the respective object from the root object created by
	 * xmlparser
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getconceptDescriptionFromRootObj(Map<String, Object> rootObj) {
		try {
			return (Map<String, Object>) ((Map<String, Object>) rootObj.get("aas:aasenv"))
					.get("aas:conceptDescriptions");
		} catch (Exception e) {
			logger.error("Exception in getconceptDescriptionFromRootObj", e);
		}
		return null;
	}

}
