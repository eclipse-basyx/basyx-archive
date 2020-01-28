package org.eclipse.basyx.submodel.metamodel.api.identifier;

/**
 * Used to uniquely identify an entity by using an identifier.
 * 
 * @author rajashek, schnicke
 *
 */
public interface IIdentifier {
	/**
	 * Gets the type of the Identifier, e.g. IRI, IRDI etc.
	 */
	public IdentifierType getIdType();

	/**
	 * Gets the identifier of the element. Its type is defined in idType.
	 */
	public String getId();
}
