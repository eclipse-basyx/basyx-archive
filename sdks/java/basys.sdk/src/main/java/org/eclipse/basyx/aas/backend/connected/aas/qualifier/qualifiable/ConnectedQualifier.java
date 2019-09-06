package org.eclipse.basyx.aas.backend.connected.aas.qualifier.qualifiable;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IQualifier;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.qualifiable.Qualifier;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IQualifier
 * @author rajashek
 *
 */
public class ConnectedQualifier extends ConnectedElement implements IQualifier {
	public ConnectedQualifier(VABElementProxy proxy) {
		super(proxy);		
	}
	
	@Override
	public void setQualifierType(String obj) {
		getProxy().setModelPropertyValue(Qualifier.QUALIFIERTYPE, obj);
		
	}

	@Override
	public String getQualifierType() {
		return (String)getProxy().getModelPropertyValue(Qualifier.QUALIFIERTYPE);
	}

	@Override
	public void setQualifierValue(Object obj) {
		getProxy().setModelPropertyValue(Qualifier.QUALIFIERVALUE, obj);
		
	}

	@Override
	public Object getQualifierValue() {
		return getProxy().getModelPropertyValue(Qualifier.QUALIFIERVALUE);
	}

	@Override
	public void setQualifierValueId(IReference obj) {
		getProxy().setModelPropertyValue(Qualifier.QUALIFIERVALUEID, obj);
		
	}

	@Override
	public IReference getQualifierValueId() {
		return (IReference)getProxy().getModelPropertyValue(Qualifier.QUALIFIERVALUEID);
	}

	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemanticsFacade(getProxy()).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		 new ConnectedHasSemanticsFacade(getProxy()).setSemanticID(ref);
		
	}

}
