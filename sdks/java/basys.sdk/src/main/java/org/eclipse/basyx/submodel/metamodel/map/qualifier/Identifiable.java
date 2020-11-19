package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

/**
 * Identifiable class
 * 
 * @author kuhn, schnicke
 *
 */
public class Identifiable extends Referable implements IIdentifiable {
	public static final String ADMINISTRATION = "administration";

	public static final String IDENTIFICATION = "identification";

	/**
	 * Default constructor
	 */
	public Identifiable() {
		// Add qualifier
		putAll(new Referable());

		// Administrative information of an element. (AdministrativeInformation)
		put(ADMINISTRATION, new AdministrativeInformation());
		// The globally unique identification of an element. (Identificator)
		put(IDENTIFICATION, new Identifier());
	}
	
	/**
	 * Constructor with mandatory attribute
	 * @param idShort
	 * @param identification
	 */
	public Identifiable(String idShort, IIdentifier identification) {
		super(idShort);
		setIdentification(identification.getIdType(), identification.getId());
		setAdministration(new AdministrativeInformation());
	}

	/**
	 * Constructor that accepts values for most relevant properties
	 */
	public Identifiable(String version, String revision, String idShort, String category, LangStrings description, IdentifierType idType, String id) {
		// Add qualifier
		putAll(new Referable(idShort, category, description));

		// Create administrative information of an element. (AdministrativeInformation)
		put(ADMINISTRATION, new AdministrativeInformation(version, revision));
		// The globally unique identification of an element. (Identificator)
		put(IDENTIFICATION, new Identifier(idType, id));
	}

	/**
	 * Creates a Identifiable object from a map
	 * 
	 * @param obj
	 *            a Identifiable object as raw map
	 * @return a Identifiable object, that behaves like a facade for the given map
	 */
	public static Identifiable createAsFacade(Map<String, Object> map, KeyElements type) {
		if (map == null) {
			return null;
		}

		Identifiable ret = new Identifiable();
		ret.setMap(map);
		ret.setElementType(type);
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public IAdministrativeInformation getAdministration() {
		return AdministrativeInformation.createAsFacade((Map<String, Object>) get(Identifiable.ADMINISTRATION));
	}

	@SuppressWarnings("unchecked")
	@Override
	public IIdentifier getIdentification() {
		return Identifier.createAsFacade((Map<String, Object>) get(Identifiable.IDENTIFICATION));
	}

	public void setAdministration(AdministrativeInformation info) {
		put(Identifiable.ADMINISTRATION, info);
	}

	public void setIdentification(IdentifierType idType, String id) {
		put(Identifiable.IDENTIFICATION, new Identifier(idType, id));
	}

	@Override
	protected String getId() {
		return getIdentification().getId();
	}

	@Override
	protected KeyType getKeyType() {
		// KeyType and IdentifierType are virtually the same, so convert them here
		return KeyType.fromString(getIdentification().getIdType().toString());
	}

	@Override
	protected boolean isLocal() {
		return true;
	}

}
