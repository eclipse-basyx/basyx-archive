package org.eclipse.basyx.aas.impl.metamodel.facades;

import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.AdministrativeInformation;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.qualifier.HasDataSpecification;

/**
 * Facade providing access to a map containing the AdministrativeInformation
 * structure
 * 
 * @author rajashek
 *
 */
public class AdministrativeInformationFacade implements IAdministrativeInformation {
	private Map<String, Object> map;

	public AdministrativeInformationFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	@SuppressWarnings("unchecked")

	public HashSet<IReference> getDataSpecificationReferences() {
		return (HashSet<IReference>) map.get(HasDataSpecification.HASDATASPECIFICATION);
	}

	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
	}

	public void setVersion(String version) {
		map.put(AdministrativeInformation.VERSION, version);
	}

	@Override
	public String getVersion() {
		return (String) map.get(AdministrativeInformation.VERSION);
	}

	public void setRevision(String revision) {
		map.put(AdministrativeInformation.REVISION, revision);
	}

	@Override
	public String getRevision() {
		return (String) map.get(AdministrativeInformation.REVISION);
	}

}
