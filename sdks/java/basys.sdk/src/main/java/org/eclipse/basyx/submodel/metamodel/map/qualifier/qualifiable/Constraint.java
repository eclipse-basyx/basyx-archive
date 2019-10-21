package org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable;

import java.util.HashMap;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;

public abstract class Constraint extends HashMap<String, Object> implements IConstraint {
	private static final long serialVersionUID = 1L;
	public static final String MODELTYPE = "Constraint";

	public Constraint() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

	}
}
