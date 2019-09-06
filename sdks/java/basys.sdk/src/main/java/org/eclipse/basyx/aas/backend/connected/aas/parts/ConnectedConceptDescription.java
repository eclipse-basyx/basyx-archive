package org.eclipse.basyx.aas.backend.connected.aas.parts;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDescription;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedIdentifiableFacade;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.parts.ConceptDescription;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IConceptDescription
 * @author rajashek
 *
 */
public class ConnectedConceptDescription extends ConnectedElement implements IConceptDescription {

	
	public ConnectedConceptDescription(VABElementProxy proxy) {
		super(proxy);
	}
	
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getProxy()).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new ConnectedHasDataSpecificationFacade(getProxy()).setDataSpecificationReferences(ref);
		
	}
	
	@Override
	public IAdministrativeInformation getAdministration() {
		return new ConnectedIdentifiableFacade(getProxy()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new ConnectedIdentifiableFacade(getProxy()).getIdentification();
	}

	@Override
	public void setAdministration(String version, String revision) {
		new ConnectedIdentifiableFacade(getProxy()).setAdministration(version, revision);
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		new ConnectedIdentifiableFacade(getProxy()).setIdentification(idType, id);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<String> getisCaseOf() {
		return (HashSet<String>)getProxy().getModelPropertyValue(ConceptDescription.ISCASEOF);
	}

	@Override
	public void setIscaseOf(HashSet<String> ref) {
		getProxy().setModelPropertyValue(ConceptDescription.ISCASEOF, ref);
		
	}
	@Override
	public String getIdshort() {
		return (String) getProxy().getModelPropertyValue(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) getProxy().getModelPropertyValue(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) getProxy().getModelPropertyValue(Referable.DESCRIPTION);
	}

	@Override
	public IReference  getParent() {
		return (IReference) getProxy().getModelPropertyValue(Referable.PARENT);
	}

	@Override
	public void setIdshort(String idShort) {
		getProxy().setModelPropertyValue(Referable.IDSHORT, idShort);
		
	}

	@Override
	public void setCategory(String category) {
		getProxy().setModelPropertyValue(Referable.CATEGORY, category);
		
	}

	@Override
	public void setDescription(String description) {
		getProxy().setModelPropertyValue(Referable.DESCRIPTION, description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		getProxy().setModelPropertyValue(Referable.PARENT, obj);
		
	}

	
}
