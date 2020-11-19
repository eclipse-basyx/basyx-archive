package org.eclipse.basyx.submodel.metamodel.api.submodelelement;

import org.eclipse.basyx.submodel.metamodel.api.IElement;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IReferable;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.IHasKind;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IQualifiable;

/**
 * A submodel element is an element suitable for the description and
 * differentiation of assets.
 * 
 * @author schnicke
 *
 */
public interface ISubmodelElement extends IElement, IHasDataSpecification, IReferable, IQualifiable, IHasSemantics, IHasKind {
	public String getModelType();
	
	public Object getValue();
	
	public void setValue(Object value);
}
