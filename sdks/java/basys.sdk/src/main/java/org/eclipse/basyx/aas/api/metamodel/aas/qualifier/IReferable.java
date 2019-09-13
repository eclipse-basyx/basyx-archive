package org.eclipse.basyx.aas.api.metamodel.aas.qualifier;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;

/**
 * Interface for Referable the function names are self explanatory
 * 
 * @author rajashek
 *
 */
public interface IReferable {
	public String getIdshort();

	public String getCategory();

	public String getDescription();

	public IReference getParent();
}
