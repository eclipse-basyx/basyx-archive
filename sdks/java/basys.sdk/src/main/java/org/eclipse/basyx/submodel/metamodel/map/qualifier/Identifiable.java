package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.IdentifiableFacade;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;

/**
 * Identifiable class
 * 
 * @author kuhn, schnicke
 *
 */
public class Identifiable extends Referable implements IIdentifiable {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

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
	 * Constructor that accepts values for most relevant properties
	 */
	public Identifiable(String version, String revision, String idShort, String category, String description, String idType, String id) {
		// Add qualifier
		putAll(new Referable(idShort, category, description));

		// Create administrative information of an element. (AdministrativeInformation)
		put(ADMINISTRATION, new AdministrativeInformation(version, revision));
		// The globally unique identification of an element. (Identificator)
		put(IDENTIFICATION, new Identifier(idType, id));
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		return new IdentifiableFacade(this).getAdministration();
	}

	@Override
	public IIdentifier getIdentification() {
		return new IdentifiableFacade(this).getIdentification();
	}

	public void setAdministration(String version, String revision) {
		new IdentifiableFacade(this).setAdministration(version, revision);
	}

	public void setIdentification(String idType, String id) {
		new IdentifiableFacade(this).setIdentification(idType, id);
	}

}
