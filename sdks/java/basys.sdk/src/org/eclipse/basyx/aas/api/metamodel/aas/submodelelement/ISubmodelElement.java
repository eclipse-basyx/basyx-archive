package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IReferable;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.haskind.IHasKind;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IQualifiable;
import org.eclipse.basyx.aas.api.resources.IElement;

public interface ISubmodelElement extends IElement,IHasDataSpecification,IReferable,IQualifiable,IHasSemantics,IHasKind {

}
