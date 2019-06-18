package org.eclipse.basyx.aas.backend.connected.aas.qualifier.qualifiable;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.qualifiable.IQualifier;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasSemanticsFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifier;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IQualifier
 * @author rajashek
 *
 */
public class ConnectedQualifier extends ConnectedElement implements IQualifier {
	public ConnectedQualifier(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public void setQualifierType(String obj) {
		getProxy().updateElementValue(constructPath(Qualifier.QUALIFIERTYPE), obj);
		
	}

	@Override
	public String getQualifierType() {
		return (String)getProxy().readElementValue(constructPath(Qualifier.QUALIFIERTYPE));
	}

	@Override
	public void setQualifierValue(Object obj) {
		getProxy().updateElementValue(constructPath(Qualifier.QUALIFIERVALUE), obj);
		
	}

	@Override
	public Object getQualifierValue() {
		return getProxy().readElementValue(constructPath(Qualifier.QUALIFIERVALUE));
	}

	@Override
	public void setQualifierValueId(IReference obj) {
		getProxy().updateElementValue(constructPath(Qualifier.QUALIFIERVALUEID), obj);
		
	}

	@Override
	public IReference getQualifierValueId() {
		return (IReference)getProxy().readElementValue(constructPath(Qualifier.QUALIFIERVALUEID));
	}

	@Override
	public IReference getSemanticId() {
		return new ConnectedHasSemanticsFacade(getPath(),getProxy()).getSemanticId();
	}

	@Override
	public void setSemanticID(IReference ref) {
		 new ConnectedHasSemanticsFacade(getPath(),getProxy()).setSemanticID(ref);
		
	}

}
