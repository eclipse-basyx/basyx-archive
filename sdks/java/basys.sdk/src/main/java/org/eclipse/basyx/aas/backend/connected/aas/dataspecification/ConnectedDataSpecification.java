package org.eclipse.basyx.aas.backend.connected.aas.dataspecification;

import org.eclipse.basyx.aas.api.metamodel.aas.dataspecification.IDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.dataspecification.DataSpecification;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IDataSpecification
 * @author rajashek
 *
 */
public class ConnectedDataSpecification extends ConnectedElement implements IDataSpecification  {
	
	public ConnectedDataSpecification(VABElementProxy proxy) {
		super(proxy);
		
	}

	@Override
	public String getPreferredName() {
		return (String) getProxy().getModelPropertyValue(DataSpecification.PREFERREDNAME);
	}

	@Override
	public String getShortName() {
		return (String) getProxy().getModelPropertyValue(DataSpecification.SHORTNAME);
	}

	@Override
	public String getUnit() {
		return (String) getProxy().getModelPropertyValue(DataSpecification.UNIT);
	}

	@Override
	public IReference getUnitId() {
		return (IReference) getProxy().getModelPropertyValue(DataSpecification.UNITID);
	}

	@Override
	public String getSourceOfDefinition() {
		return (String) getProxy().getModelPropertyValue(DataSpecification.SOURCEOFDEFINITION);
	}

	@Override
	public String getSymbol() {
		return (String) getProxy().getModelPropertyValue(DataSpecification.SYMBOL);
	}

	@Override
	public String getDataType() {
		return (String) getProxy().getModelPropertyValue(DataSpecification.DATATYPE);
	}

	@Override
	public String getDefinition() {
		return (String) getProxy().getModelPropertyValue(DataSpecification.DEFINITION);
	}

	@Override
	public String getValueFormat() {
		return (String) getProxy().getModelPropertyValue(DataSpecification.VALUEFORMAT);
	}

	@Override
	public String getValueList() {
		return (String) getProxy().getModelPropertyValue(DataSpecification.VALUELIST);
	}

	@Override
	public String getCode() {
		return (String) getProxy().getModelPropertyValue(DataSpecification.CODE);
	}

	@Override
	public void setPreferredName(String preferredName) {
		getProxy().setModelPropertyValue(DataSpecification.PREFERREDNAME, preferredName);
		
	}

	@Override
	public void setShortName(String shortName) {
		getProxy().setModelPropertyValue(DataSpecification.SHORTNAME, shortName);
		
	}

	@Override
	public void setUnit(String uni) {
		getProxy().setModelPropertyValue(DataSpecification.UNIT, uni);
		
	}

	@Override
	public void setUnitId(IReference unitId) {
		getProxy().setModelPropertyValue(DataSpecification.UNITID, unitId);
		
	}

	@Override
	public void setSourceOfDefinition(String sourceOfDefinition) {
		getProxy().setModelPropertyValue(DataSpecification.SOURCEOFDEFINITION, sourceOfDefinition);
		
	}

	@Override
	public void setSymbol(String symbol) {
		getProxy().setModelPropertyValue(DataSpecification.SYMBOL, symbol);
		
	}

	@Override
	public void setDataType(String dataType) {
		getProxy().setModelPropertyValue(DataSpecification.DATATYPE, dataType);
		
	}

	@Override
	public void setDefinition(String definition) {
		getProxy().setModelPropertyValue(DataSpecification.DEFINITION, definition);
		
	}

	@Override
	public void setValueFormat(String valueFormat) {
		getProxy().setModelPropertyValue(DataSpecification.VALUEFORMAT, valueFormat);
		
	}

	@Override
	public void setValueList(Object obj) {
		getProxy().setModelPropertyValue(DataSpecification.VALUELIST, obj);
		
	}

	@Override
	public void setCode(Object obj) {
		getProxy().setModelPropertyValue(DataSpecification.CODE, obj);
		
	}


}
