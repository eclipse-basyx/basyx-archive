package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.AdministrativeInformationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;

/**
 * AdministrativeInformation class
 * 
 * @author kuhn
 *
 */
public class AdministrativeInformation extends HashMap<String, Object> implements IAdministrativeInformation {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	public static final String VERSION = "version";

	public static final String REVISION = "revision";

	/**
	 * Constructor
	 */
	public AdministrativeInformation() {
		// Add qualifier
		putAll(new HasDataSpecification());

		// Default values
		put(VERSION, "");
		put(REVISION, "");
	}

	/**
	 * Constructor
	 */
	public AdministrativeInformation(String version, String revision) {
		// Add qualifier
		putAll(new HasDataSpecification());

		// Default values
		put(VERSION, version);
		put(REVISION, revision);
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
	}

	public void setVersion(String version) {
		new AdministrativeInformationFacade(this).setVersion(version);
	}

	@Override
	public String getVersion() {
		return new AdministrativeInformationFacade(this).getVersion();
	}

	public void setRevision(String revision) {
		new AdministrativeInformationFacade(this).setRevision(revision);
	}

	@Override
	public String getRevision() {
		return new AdministrativeInformationFacade(this).getRevision();
	}

}
