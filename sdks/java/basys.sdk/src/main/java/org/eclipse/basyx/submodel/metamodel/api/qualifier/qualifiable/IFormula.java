package org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable;

import java.util.Collection;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
/**
 * Interface for Formula
 * @author rajashek
 *
*/
public interface IFormula extends IConstraint {
	public Collection<IReference> getDependsOn();
}
