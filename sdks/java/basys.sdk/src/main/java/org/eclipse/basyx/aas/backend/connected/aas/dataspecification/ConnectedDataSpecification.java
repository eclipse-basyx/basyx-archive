package org.eclipse.basyx.aas.backend.connected.aas.dataspecification;

import org.eclipse.basyx.aas.api.metamodel.aas.dataspecification.IDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.dataspecification.DataSpecification;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IDataSpecification
 * @author rajashek
 *
 */
public class ConnectedDataSpecification extends ConnectedElement implements IDataSpecification  {
	
	public ConnectedDataSpecification(String path, VABElementProxy proxy) {
		super(path, proxy);
		
	}

	@Override
	public String getPreferredName() {
		return  (String) getProxy().getModelPropertyValue(constructPath(DataSpecification.PREFERREDNAME));
	}

	@Override
	public String getShortName() {
		return (String) getProxy().getModelPropertyValue(constructPath(DataSpecification.SHORTNAME));
	}

	@Override
	public String getUnit() {
		return (String) getProxy().getModelPropertyValue(constructPath(DataSpecification.UNIT));
	}

	@Override
	public IReference getUnitId() {
		return (IReference) getProxy().getModelPropertyValue(constructPath(DataSpecification.UNITID));
	}

	@Override
	public String getSourceOfDefinition() {
		return (String) getProxy().getModelPropertyValue(constructPath(DataSpecification.SOURCEOFDEFINITION));
	}

	@Override
	public String getSymbol() {
		return (String) getProxy().getModelPropertyValue(constructPath(DataSpecification.SYMBOL));
	}

	@Override
	public String getDataType() {
		return (String) getProxy().getModelPropertyValue(constructPath(DataSpecification.DATATYPE));
	}

	@Override
	public String getDefinition() {
		return (String) getProxy().getModelPropertyValue(constructPath(DataSpecification.DEFINITION));
	}

	@Override
	public String getValueFormat() {
		return (String) getProxy().getModelPropertyValue(constructPath(DataSpecification.VALUEFORMAT));
	}

	@Override
	public String getValueList() {
		return (String) getProxy().getModelPropertyValue(constructPath(DataSpecification.VALUELIST));
	}

	@Override
	public String getCode() {
		return (String) getProxy().getModelPropertyValue(constructPath(DataSpecification.CODE));
	}

	@Override
	public void setPreferredName(String preferredName) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.PREFERREDNAME), preferredName);
		
	}

	@Override
	public void setShortName(String shortName) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.SHORTNAME), shortName);
		
	}

	@Override
	public void setUnit(String uni) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.UNIT), uni);
		
	}

	@Override
	public void setUnitId(IReference unitId) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.UNITID), unitId);
		
	}

	@Override
	public void setSourceOfDefinition(String sourceOfDefinition) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.SOURCEOFDEFINITION), sourceOfDefinition);
		
	}

	@Override
	public void setSymbol(String symbol) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.SYMBOL), symbol);
		
	}

	@Override
	public void setDataType(String dataType) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.DATATYPE), dataType);
		
	}

	@Override
	public void setDefinition(String definition) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.DEFINITION), definition);
		
	}

	@Override
	public void setValueFormat(String valueFormat) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.VALUEFORMAT), valueFormat);
		
	}

	@Override
	public void setValueList(Object obj) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.VALUELIST), obj);
		
	}

	@Override
	public void setCode(Object obj) {
		getProxy().setModelPropertyValue(constructPath(DataSpecification.CODE), obj);
		
	}


}
