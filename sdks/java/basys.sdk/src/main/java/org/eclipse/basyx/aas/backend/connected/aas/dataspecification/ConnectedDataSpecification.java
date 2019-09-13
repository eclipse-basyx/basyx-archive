package org.eclipse.basyx.aas.backend.connected.aas.dataspecification;

import org.eclipse.basyx.aas.api.metamodel.aas.dataspecification.IDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.dataspecification.DataSpecification;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * "Connected" implementation of IDataSpecification
 * 
 * @author rajashek
 *
 */
public class ConnectedDataSpecification extends ConnectedElement implements IDataSpecification {

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
}
