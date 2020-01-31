package org.eclipse.basyx.submodel.metamodel.map.qualifier.qualifiable;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.vab.model.VABModelMap;

public abstract class Constraint extends VABModelMap<Object> implements IConstraint {
	public static final String MODELTYPE = "Constraint";

	public Constraint() {
		// Add model type
		putAll(new ModelType(MODELTYPE));

	}
}
