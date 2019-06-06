package org.eclipse.basyx.aas.backend.connected.aas.parts;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDictionary;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedReferableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.ConceptDictionary;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IConceptDictionary
 * @author rajashek
 *
 */
public class ConnectedConceptDictionary extends ConnectedElement implements IConceptDictionary {
	public ConnectedConceptDictionary(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public String getIdshort() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getIdshort();
	}

	@Override
	public String getCategory() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getCategory();
	}

	@Override
	public String getDescription() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ConnectedReferableFacade(getPath(),getProxy()).getParent();
	}

	@Override
	public void setIdshort(String idShort) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setIdshort(idShort);
		
	}

	@Override
	public void setCategory(String category) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setCategory(category);
		
	}

	@Override
	public void setDescription(String description) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setDescription(description);
		
	}

	@Override
	public void setParent(IReference obj) {
		 new ConnectedReferableFacade(getPath(),getProxy()).setParent(obj);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<String> getConceptDescription() {
		return (HashSet<String>)getProxy().readElementValue(constructPath(ConceptDictionary.CONCEPTDESCRIPTION));
	}

	@Override
	public void setConceptDescription(HashSet<String> ref) {
		getProxy().updateElementValue(constructPath(ConceptDictionary.CONCEPTDESCRIPTION), ref);
		
	}
}
