package org.eclipse.basyx.aas.api.metamodel.aas.dataspecification;

import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;

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
	public  IReference getUnitId();
	public String getSourceOfDefinition();
	public String getSymbol();
	public String getDataType();
	public String getDefinition();
	public String getValueFormat();
	public String getValueList();
	public String getCode();

	public void setPreferredName(String preferredName);
	public void setShortName(String shortName);
	public void setUnit( String uni);
	public void setUnitId( IReference unitId);
	public void setSourceOfDefinition(String sourceOfDefinition);
	public void setSymbol(String symbol);
	public void setDataType(String dataType);
	public void setDefinition(String definition);
	public void setValueFormat(String valueFormat);
	public void setValueList(Object obj);
	public void setCode(Object obj);
	

}
