package org.eclipse.basyx.aas.metamodel.map.descriptor;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;




/**
 * AAS descriptor class
 * 
 * @author kuhn
 *
 */
public class SubmodelDescriptor extends ModelDescriptor {
	
	public static final String MODELTYPE = "SubmodelDescriptor";

	/**
	 * Create descriptor from existing hash map
	 */
	public SubmodelDescriptor(Map<String, Object> map) {
		super(map);
		validate(map);
	}
	
	/**
	 * Create a new aas descriptor with minimal information based on an existing
	 * submodel.
	 */
	public SubmodelDescriptor(ISubModel sm, String httpEndpoint) {
		// Create descriptor with minimal information (id and idShort)
		this(sm.getIdShort(), sm.getIdentification(), httpEndpoint);
		
		putAll(new HasSemantics(sm.getSemanticId()));
	}
	
	/**
	 * Create a new descriptor with minimal information
	 */
	public SubmodelDescriptor(String idShort, IIdentifier id, String httpEndpoint) {
		super(idShort, id, httpEndpoint);
		
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}

	@Override
	protected String getModelType() {
		return MODELTYPE;
	}
}

