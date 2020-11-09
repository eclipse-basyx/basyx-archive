package org.eclipse.basyx.examples.scenarios.cloudedgedeployment;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * A helper Class used to build the necessary objects. 
 * 
 * @author conradi
 *
 */
public class ComponentBuilder {

	public static final String EDGESM_ID_SHORT = "curr_temp";
	public static final String EDGESM_ID = "current_oven_temperature";
	public static final String EDGESM_ENDPOINT = "http://localhost:8082/oven/current_temp/submodel";
	
	public static final String DOCUSM_ID_SHORT = "oven_doc";
	public static final String DOCUSM_ID = "oven_documentation_sm";
	public static final String DOCUSM_ENDPOINT = "http://localhost:8081/cloud/shells/aasId/aas/submodels/oven_doc/submodel";
	
	public static final String AAS_ID_SHORT = "aasIdShort";
	public static final String AAS_ID = "aasId";
	public static final String AAS_ENDPOINT = "http://localhost:8081/cloud/shells/aasId/aas";
	
	
	
	
	/**
	 * Creates the AAS, which gets pushed to the cloud server
	 * 
	 * @return the created AAS
	 */
	public static AssetAdministrationShell getAAS() {
		AssetAdministrationShell aas = new AssetAdministrationShell();
		aas.setIdShort(AAS_ID_SHORT);
		aas.setIdentification(IdentifierType.CUSTOM, AAS_ID);
		return aas;
	}
	
	/**
	 * Creates the Submodel, which gets pushed to the cloud server
	 * It contains static non changing information
	 * 
	 * @return the created Submodel
	 */
	public static SubModel getDocuSM() {
		SubModel sm = new SubModel();
		sm.setIdentification(IdentifierType.CUSTOM, DOCUSM_ID);
		sm.setIdShort(DOCUSM_ID_SHORT);

		Property property = new Property(1000);
		property.setIdShort("max_temp");
		sm.addSubModelElement(property);
		
		return sm;
	}
	
	/**
	 * Creates the SubmodelDescriptor for the Docu-Submodel
	 * 
	 * @return the created SubmodelDescriptor
	 */
	public static SubmodelDescriptor getDocuSMDescriptor() {
		return new SubmodelDescriptor(getDocuSM(), DOCUSM_ENDPOINT);
	}
	
	/**
	 * Creates the Submodel, which gets hosted near the machine
	 * It contains measured values that are updated frequently
	 * 
	 * @return the created Submodel
	 */
	public static SubModel createEdgeSubModel() {
		SubModel sm = new SubModel();
		sm.setIdentification(IdentifierType.CUSTOM, EDGESM_ID);
		sm.setIdShort(EDGESM_ID_SHORT);
		
		// The property in this Submodel contains the currently measured temperature of the oven
		// It is represented by a static value in this example
		Property property = new Property(31);
		property.setIdShort("temp");
		sm.addSubModelElement(property);
		return sm;
	}
	
	/**
	 * Creates the SubmodelDescriptor for the Edge-Submodel
	 * 
	 * @return the created SubmodelDescriptor
	 */
	public static SubmodelDescriptor getEdgeSubmodelDescriptor() {
		Identifier identifier = new Identifier(IdentifierType.CUSTOM, EDGESM_ID);
		return new SubmodelDescriptor(EDGESM_ID_SHORT, identifier, EDGESM_ENDPOINT);
	}

}
