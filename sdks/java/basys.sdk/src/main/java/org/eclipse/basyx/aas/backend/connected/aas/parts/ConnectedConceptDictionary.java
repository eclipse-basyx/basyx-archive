package org.eclipse.basyx.aas.backend.connected.aas.parts;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDictionary;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedReferableFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.parts.ConceptDictionary;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IConceptDictionary
 * @author rajashek
 *
 */
public class ConnectedConceptDictionary extends ConnectedElement implements IConceptDictionary {
	public ConnectedConceptDictionary(VABElementProxy proxy) {
		super(proxy);		
	}

	@Override
	public String getIdshort() {
		return new ConnectedReferableFacade(getProxy()).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ConnectedReferableFacade(getProxy()).getCategory();
	}

	@Override
	public String getDescription() {
		return new ConnectedReferableFacade(getProxy()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ConnectedReferableFacade(getProxy()).getParent();
	}

	@Override
	public void setIdshort(String idShort) {
		 new ConnectedReferableFacade(getProxy()).setIdshort(idShort);
		
	}

	@Override
	public void setCategory(String category) {
		 new ConnectedReferableFacade(getProxy()).setCategory(category);
		
	}

	@Override
	public void setDescription(String description) {
		 new ConnectedReferableFacade(getProxy()).setDescription(description);
		
	}

	@Override
	public void setParent(IReference obj) {
		 new ConnectedReferableFacade(getProxy()).setParent(obj);
		
	}

	@SuppressWarnings("unchecked")
	@Override

	public HashSet<IReference> getConceptDescription() {
		return (HashSet<IReference>)getProxy().getModelPropertyValue(ConceptDictionary.CONCEPTDESCRIPTION);
	}

	@Override
	public void setConceptDescription(HashSet<String> ref) {
		getProxy().setModelPropertyValue(ConceptDictionary.CONCEPTDESCRIPTION, ref);
		
	}
}
