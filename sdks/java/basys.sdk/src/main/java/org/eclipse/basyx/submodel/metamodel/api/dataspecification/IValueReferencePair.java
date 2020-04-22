package org.eclipse.basyx.submodel.metamodel.api.dataspecification;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * A value reference pair within a value list within a value list of the DataSpecificationIEC61360.
 * Each value has a global unique id defining its semantic.
 * 
 * @author espen
 *
 */
public interface IValueReferencePair {
	public String getValue();

	public IReference getValueId();
}
