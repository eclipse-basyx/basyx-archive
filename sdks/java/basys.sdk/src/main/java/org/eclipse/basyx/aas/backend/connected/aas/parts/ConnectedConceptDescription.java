package org.eclipse.basyx.aas.backend.connected.aas.parts;

import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDescription;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.backend.connected.ConnectedElement;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedHasDataSpecificationFacade;
import org.eclipse.basyx.aas.backend.connected.facades.ConnectedIdentifiableFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.ConceptDescription;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IConceptDescription
 * @author rajashek
 *
 */
public class ConnectedConceptDescription extends ConnectedElement implements IConceptDescription {

	
	public ConnectedConceptDescription(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new ConnectedHasDataSpecificationFacade(getPath(),getProxy()).setDataSpecificationReferences(ref);
		
	}
	
	@Override
	public IAdministrativeInformation getAdministration() {
		return new ConnectedIdentifiableFacade(getPath(),getProxy()).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new ConnectedIdentifiableFacade(getPath(),getProxy()).getIdentification();
	}

	@Override
	public void setAdministration(String version, String revision) {
		 new ConnectedIdentifiableFacade(getPath(),getProxy()).setAdministration(version, revision);
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		 new ConnectedIdentifiableFacade(getPath(),getProxy()).setIdentification(idType, id);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<String> getisCaseOf() {
		return (HashSet<String>)getProxy().readElementValue(constructPath(ConceptDescription.ISCASEOF));
	}

	@Override
	public void setIscaseOf(HashSet<String> ref) {
		getProxy().updateElementValue(constructPath(ConceptDescription.ISCASEOF), ref);
		
	}
	@Override
	public String getIdshort() {
		return (String) getProxy().readElementValue(constructPath(Referable.IDSHORT));
	}

	@Override
	public String getCategory() {
		return (String) getProxy().readElementValue(constructPath(Referable.CATEGORY));
	}

	@Override
	public String getDescription() {
		return (String) getProxy().readElementValue(constructPath(Referable.DESCRIPTION));
	}

	@Override
	public IReference  getParent() {
		return (IReference)getProxy().readElementValue(constructPath(Referable.PARENT));
	}

	@Override
	public void setIdshort(String idShort) {
		getProxy().updateElementValue(constructPath(Referable.IDSHORT), idShort);
		
	}

	@Override
	public void setCategory(String category) {
		getProxy().updateElementValue(constructPath(Referable.CATEGORY), category);
		
	}

	@Override
	public void setDescription(String description) {
		getProxy().updateElementValue(constructPath(Referable.DESCRIPTION), description);
		
	}

	@Override
	public void setParent(IReference  obj) {
		getProxy().updateElementValue(constructPath(Referable.PARENT), obj);
		
	}

	
}
