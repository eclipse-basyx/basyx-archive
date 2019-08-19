package org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier;

import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.facades.AdministrativeInformationFacade;
import org.eclipse.basyx.aas.metamodel.facades.HasDataSpecificationFacade;

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
	
	public static final String VERSION="version";
	
	public static final String REVISION="revision";

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
	public HashSet<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(this).getDataSpecificationReferences();
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		new HasDataSpecificationFacade(this).setDataSpecificationReferences(ref);
		
	}

	@Override
	public void setVersion(String version) {
		new AdministrativeInformationFacade(this).setVersion(version);
		
	}

	@Override
	public String getVersion() {
	return new AdministrativeInformationFacade(this).getVersion();
	}

	@Override
	public void setRevision(String revision) {
		new AdministrativeInformationFacade(this).setRevision(revision);
		
	}

	@Override
	public String getRevision() {
		return new AdministrativeInformationFacade(this).getRevision();
	}


}
