package org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IQualifier;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasSemanticsFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.qualifiable.QualifierFacade;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;

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

	public static final String QUALIFIER = "qualifier";

	public static final String QUALIFIERTYPE = "qualifierType";

	public static final String QUALIFIERVALUE = "qualifierValue";

	public static final String QUALIFIERVALUEID = "qualifierValueId";

	public static final String MODELTYPE = "Qualifier";

	/**
	 * Constructor
	 */
	public Qualifier() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

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

	public void setQualifierType(String obj) {
		new QualifierFacade(this).setQualifierType(obj);

	}

	@Override
	public String getQualifierType() {
		return new QualifierFacade(this).getQualifierType();
	}

	public void setQualifierValue(Object obj) {
		new QualifierFacade(this).setQualifierValue(obj);
	}

	@Override
	public Object getQualifierValue() {
		return new QualifierFacade(this).getQualifierValue();
	}

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

	public void setSemanticID(IReference ref) {
		new HasSemanticsFacade(this).setSemanticID(ref);
	}
}
