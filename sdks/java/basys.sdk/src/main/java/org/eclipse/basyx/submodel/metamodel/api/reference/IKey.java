package org.eclipse.basyx.submodel.metamodel.api.reference;

/**
 * Provides access to values of Key elements as provided by DAAS document
 * 
 * @author rajashek
 *
 */
public interface IKey {

	/**
	 * 
	 * @return See
	 *         {@link org.eclipse.basyx.submodel.metamodel.map.reference.enums.KeyElements
	 *         KeyElements} and its children for possible values
	 */
	public String getType();

	public boolean isLocal();

	public String getValue();

	public String getidType();
}
