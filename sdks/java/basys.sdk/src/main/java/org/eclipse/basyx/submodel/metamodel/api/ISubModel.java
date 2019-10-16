package org.eclipse.basyx.submodel.metamodel.api;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasSemantics;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.IHasKind;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.IProperty;
import org.eclipse.basyx.submodel.metamodel.map.IVABElementContainer;

/**
 * Interface API for sub models
 * 
 * @author kuhn
 *
 */
public interface ISubModel extends IElement, IHasSemantics, IIdentifiable, IHasDataSpecification, IHasKind, IVABElementContainer {

	public void setProperties(Map<String, IProperty> properties);

	public void setOperations(Map<String, IOperation> operations);
}
