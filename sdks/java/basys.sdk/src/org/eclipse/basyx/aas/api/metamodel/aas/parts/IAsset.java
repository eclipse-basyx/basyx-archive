package org.eclipse.basyx.aas.api.metamodel.aas.parts;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.haskind.IHasKind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * Interface for class Identifier
 * 
 * @author rajashek
 *
 */

public interface IAsset extends IHasDataSpecification,IHasKind,IIdentifiable {
	
	/**
	 * Interface for Asset
	 * the function names are self explanatory 
	 * @author rajashek
	 *
*/

	public Reference getAssetIdentificationModel();
	
	public void setAssetIdentificationModel(Reference submodel);
	

}
