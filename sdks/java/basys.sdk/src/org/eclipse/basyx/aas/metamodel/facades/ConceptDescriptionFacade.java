package org.eclipse.basyx.aas.metamodel.facades;

import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDescription;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.Identifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.ConceptDescription;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;


/**
 * Facade providing access to a map containing the ConceptDescription structure
 * 
 * @author rajashek
 *
 */

public class ConceptDescriptionFacade implements IConceptDescription {
	
	private Map<String, Object> map;
	
	public ConceptDescriptionFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return (HashSet<IReference>) map.get(HasDataSpecification.HASDATASPECIFICATION);
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
		
	}
	
	@Override
	public AdministrativeInformation getAdministration() {
		return (AdministrativeInformation)map.get(Identifiable.ADMINISTRATION);
	}

	@Override
	public Identifier getIdentification() {
		return (Identifier)map.get(Identifiable.IDENTIFICATION);
	}

	@Override
	public void setAdministration(String version, String revision) {
		map.put(Identifiable.ADMINISTRATION, new AdministrativeInformation(version, revision));
		
	}

	@Override
	public void setIdentification(String idType, String id) {
		map.put(Identifiable.IDENTIFICATION, new Identifier(idType, id));
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public HashSet<String> getisCaseOf() {
		return (HashSet<String>)map.get(ConceptDescription.ISCASEOF);
	}

	@Override
	public void setIscaseOf(HashSet<String> ref) {
		map.put(ConceptDescription.ISCASEOF, ref);
		
	}

	

}
