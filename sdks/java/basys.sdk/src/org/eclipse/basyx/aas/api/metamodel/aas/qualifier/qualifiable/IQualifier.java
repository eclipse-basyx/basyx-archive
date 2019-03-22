package org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasSemantics;

/**
 * Interface for Qualifier
 * @author rajashek
 *
*/


public interface IQualifier extends IHasSemantics {
	
	public void setQualifierType(Object obj);
	
	public Object getQualifierType();
	
	public void setQualifierValue(Object obj);
	
	public Object getQualifierValue();
	
	public void setQualifierValueId(Object obj);
	
	public Object getQualifierValueId();

}
