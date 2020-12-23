package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IEmbeddedDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * AdministrativeInformation class
 * 
 * @author kuhn
 *
 */
public class AdministrativeInformation extends VABModelMap<Object> implements IAdministrativeInformation {
	public static final String VERSION = "version";

	public static final String REVISION = "revision";

	/**
	 * Constructor
	 */
	public AdministrativeInformation() {
		// Add qualifier
		putAll(new HasDataSpecification());

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

	/**
	 * Creates a AdministrativeInformation object from a map
	 * 
	 * @param obj
	 *            a AdministrativeInformation object as raw map
	 * @return a AdministrativeInformation object, that behaves like a facade for
	 *         the given map
	 */
	public static AdministrativeInformation createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		AdministrativeInformation ret = new AdministrativeInformation();
		ret.setMap(map);
		return ret;
	}

	@Override
	public Collection<IReference> getDataSpecificationReferences() {
		return HasDataSpecification.createAsFacade(this).getDataSpecificationReferences();
	}

	public void setDataSpecificationReferences(Collection<IReference> ref) {
		HasDataSpecification.createAsFacade(this).setDataSpecificationReferences(ref);
	}

	@Override
	public Collection<IEmbeddedDataSpecification> getEmbeddedDataSpecifications() {
		return HasDataSpecification.createAsFacade(this).getEmbeddedDataSpecifications();
	}

	public void setEmbeddedDataSpecifications(Collection<IEmbeddedDataSpecification> embeddedDataSpecifications) {
		HasDataSpecification.createAsFacade(this).setEmbeddedDataSpecifications(embeddedDataSpecifications);
	}

	public void setVersion(String version) {
		put(AdministrativeInformation.VERSION, version);
	}

	@Override
	public String getVersion() {
		return (String) get(AdministrativeInformation.VERSION);
	}

	public void setRevision(String revision) {
		put(AdministrativeInformation.REVISION, revision);
	}

	@Override
	public String getRevision() {
		return (String) get(AdministrativeInformation.REVISION);
	}
}
