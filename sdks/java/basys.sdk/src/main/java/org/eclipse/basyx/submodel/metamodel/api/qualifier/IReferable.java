package org.eclipse.basyx.submodel.metamodel.api.qualifier;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;

/**
 * Interface for Referable the function names are self explanatory
 * 
 * @author rajashek
 *
 */
public interface IReferable {
	public String getIdShort();

	public String getCategory();

	public Description getDescription();

	public IReference getParent();
}
