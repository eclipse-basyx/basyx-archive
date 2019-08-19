package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IQualifier;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.facades.HasSemanticsFacade;
import org.eclipse.basyx.aas.metamodel.facades.QualifierFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * Qualifier class
 * 
 * @author kuhn
 *
 */
public class Qualifier extends Constraint implements IQualifier {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String QUALIFIER ="qualifier";
	
	public static final String QUALIFIERTYPE ="qualifierType";
	
	public static final String QUALIFIERVALUE ="qualifierValue";
	
	public static final String QUALIFIERVALUEID="qualifierValueId";

	/**
	 * Constructor
	 */
	public Qualifier() {
		// Add all attributes from HasSemantics
		this.putAll(new HasSemantics());

		// Default values
		put(QUALIFIERTYPE, "");
		put(QUALIFIERVALUE, null);
		put(QUALIFIERVALUEID, null);
	}

	public Qualifier(String type, String value, Reference valueId) {
		// Add all attributes from HasSemantics
		this.putAll(new HasSemantics());

		// Default values
		put(QUALIFIERTYPE, "");
		put(QUALIFIERVALUE, null);
		put(QUALIFIERVALUEID, null);
	}

	@Override
	public void setQualifierType(String obj) {
		new QualifierFacade(this).setQualifierType(obj);
		
	}

	@Override
	public String getQualifierType() {
	return new QualifierFacade(this).getQualifierType();
	}

	@Override
	public void setQualifierValue(Object obj) {
		new QualifierFacade(this).setQualifierValue(obj);
		
	}

	@Override
	public Object getQualifierValue() {
		return new QualifierFacade(this).getQualifierValue();
	}

	@Override
	public void setQualifierValueId(IReference obj) {
		new QualifierFacade(this).setQualifierValueId(obj);
		
	}

	@Override
	public IReference getQualifierValueId() {
		return new QualifierFacade(this).getQualifierValueId();
	}
	
	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(this).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		new HasSemanticsFacade(this).setSemanticID(ref);
		
	}
}
