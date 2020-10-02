package org.eclipse.basyx.examples.scenarios.staticdynamic;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * Minimal implementation of a dynamic information Submodel <br>
 * Please note that it is just for showcasing, several mandatory attributes are
 * missing.
 * 
 * @author schnicke, conradi
 *
 */
public class ExampleDynamicSubmodel extends SubModel {

	public static final String SM_ID_SHORT = "maintenance";
	public static final String SM_ID = "maintenanceInformationSubmodel";

	public static final String PROPERTY_ID_SHORT = "interval";
	public static final String PROPERTY_VALUE = "2 months";
	
	
	public ExampleDynamicSubmodel() {
		// Create Property
		Property interval = new Property();
		interval.set(PROPERTY_VALUE);
		interval.setIdShort(PROPERTY_ID_SHORT);

		// Add the property to the submodel
		addSubModelElement(interval);

		// Set the idShort
		setIdShort(SM_ID_SHORT);
		
		// Set Identification
		setIdentification(IdentifierType.CUSTOM, SM_ID);
	}
}
