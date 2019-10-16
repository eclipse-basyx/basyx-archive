package org.eclipse.basyx.aas.metamodel.api.dataspecification;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

/**
 * Interface for DataSpecification
 *
 * @author rajashek
 *
 */
public interface IDataSpecification {
	public String getPreferredName();
	public String getShortName();
	public String getUnit();
	public IReference getUnitId();
	public String getSourceOfDefinition();
	public String getSymbol();
	public String getDataType();
	public String getDefinition();
	public String getValueFormat();
	public String getValueList();
	public String getCode();
}
