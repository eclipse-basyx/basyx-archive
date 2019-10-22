package org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable;

import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
/**
 * Interface for Formula
 * @author rajashek
 *
*/
public interface IFormula extends IConstraint {
	public  Set<IReference> getDependsOn();
}
