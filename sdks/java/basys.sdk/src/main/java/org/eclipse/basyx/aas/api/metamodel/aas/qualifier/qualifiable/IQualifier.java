package org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;

/**
 * Interface for Qualifier
 * @author rajashek
 *
*/


public interface IQualifier extends IHasSemantics {
	
	public void setQualifierType(String obj);
	
	public String getQualifierType();
	
	public void setQualifierValue(Object obj);
	
	public Object getQualifierValue();
	
	public void setQualifierValueId(IReference obj);
	
	public IReference getQualifierValueId();

}
