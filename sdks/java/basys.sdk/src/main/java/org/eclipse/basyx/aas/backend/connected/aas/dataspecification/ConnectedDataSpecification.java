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
		return  (String) getProxy().readElementValue(constructPath(DataSpecification.PREFERREDNAME));
	}

	@Override
	public String getShortName() {
		return (String) getProxy().readElementValue(constructPath(DataSpecification.SHORTNAME));
	}

	@Override
	public String getUnit() {
		return (String) getProxy().readElementValue(constructPath(DataSpecification.UNIT));
	}

	@Override
	public IReference getUnitId() {
		return (IReference) getProxy().readElementValue(constructPath(DataSpecification.UNITID));
	}

	@Override
	public String getSourceOfDefinition() {
		return (String) getProxy().readElementValue(constructPath(DataSpecification.SOURCEOFDEFINITION));
	}

	@Override
	public String getSymbol() {
		return (String) getProxy().readElementValue(constructPath(DataSpecification.SYMBOL));
	}

	@Override
	public String getDataType() {
		return (String) getProxy().readElementValue(constructPath(DataSpecification.DATATYPE));
	}

	@Override
	public String getDefinition() {
		return (String) getProxy().readElementValue(constructPath(DataSpecification.DEFINITION));
	}

	@Override
	public String getValueFormat() {
		return (String) getProxy().readElementValue(constructPath(DataSpecification.VALUEFORMAT));
	}

	@Override
	public String getValueList() {
		return (String) getProxy().readElementValue(constructPath(DataSpecification.VALUELIST));
	}

	@Override
	public String getCode() {
		return (String) getProxy().readElementValue(constructPath(DataSpecification.CODE));
	}

	@Override
	public void setPreferredName(String preferredName) {
		getProxy().updateElementValue(constructPath(DataSpecification.PREFERREDNAME), preferredName);
		
	}

	@Override
	public void setShortName(String shortName) {
		getProxy().updateElementValue(constructPath(DataSpecification.SHORTNAME), shortName);
		
	}

	@Override
	public void setUnit(String uni) {
		getProxy().updateElementValue(constructPath(DataSpecification.UNIT), uni);
		
	}

	@Override
	public void setUnitId(IReference unitId) {
		getProxy().updateElementValue(constructPath(DataSpecification.UNITID), unitId);
		
	}

	@Override
	public void setSourceOfDefinition(String sourceOfDefinition) {
		getProxy().updateElementValue(constructPath(DataSpecification.SOURCEOFDEFINITION), sourceOfDefinition);
		
	}

	@Override
	public void setSymbol(String symbol) {
		getProxy().updateElementValue(constructPath(DataSpecification.SYMBOL), symbol);
		
	}

	@Override
	public void setDataType(String dataType) {
		getProxy().updateElementValue(constructPath(DataSpecification.DATATYPE), dataType);
		
	}

	@Override
	public void setDefinition(String definition) {
		getProxy().updateElementValue(constructPath(DataSpecification.DEFINITION), definition);
		
	}

	@Override
	public void setValueFormat(String valueFormat) {
		getProxy().updateElementValue(constructPath(DataSpecification.VALUEFORMAT), valueFormat);
		
	}

	@Override
	public void setValueList(Object obj) {
		getProxy().updateElementValue(constructPath(DataSpecification.VALUELIST), obj);
		
	}

	@Override
	public void setCode(Object obj) {
		getProxy().updateElementValue(constructPath(DataSpecification.CODE), obj);
		
	}


}
