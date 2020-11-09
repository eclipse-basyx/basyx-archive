package org.eclipse.basyx.aas.metamodel.map.descriptor;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;




/**
 * AAS descriptor class
 * 
 * @author kuhn
 *
 */
public class SubmodelDescriptor extends ModelDescriptor implements IHasSemantics {
	
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
		super(idShort, id, harmonizeEndpoint(httpEndpoint));
		
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}

	@Override
	protected String getModelType() {
		return MODELTYPE;
	}

	private static String harmonizeEndpoint(String endpoint) {
		if (!endpoint.endsWith("/submodel")) {
			return endpoint + "/submodel";
		} else {
			return endpoint;
		}
	}

	@Override
	public IReference getSemanticId() {
		return HasSemantics.createAsFacade(this).getSemanticId();
	}

	public void setSemanticId(Reference ref) {
		HasSemantics.createAsFacade(this).setSemanticID(ref);
	}

}

