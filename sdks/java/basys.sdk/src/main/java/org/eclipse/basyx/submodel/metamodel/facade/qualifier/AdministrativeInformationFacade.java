package org.eclipse.basyx.submodel.metamodel.facade.qualifier;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceHelper;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.AdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;

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
	public Set<IReference> getDataSpecificationReferences() {
		// Transform set of maps to set of IReference
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(HasDataSpecification.HASDATASPECIFICATION);
		return ReferenceHelper.transform(set);
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
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
