package org.eclipse.basyx.submodel.metamodel.api.submodelelement;

import org.eclipse.basyx.submodel.metamodel.api.IElement;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IReferable;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.IHasKind;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IQualifiable;

public interface ISubmodelElement extends IElement,IHasDataSpecification,IReferable,IQualifiable,IHasSemantics,IHasKind {

}
