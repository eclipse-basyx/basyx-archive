package org.eclipse.basyx.aas.api.metamodel.aas.reference;

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
	 *         {@link org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.reference.enums.KeyElements
	 *         KeyElements} and its children for possible values
	 */
	public String getType();

	public boolean isLocal();

	public String getValue();

	public String getidType();
	
	public void setType(String type);

	public void setLocal( boolean local);

	public void setValue(String value);

	public void setIdType( String idType);
}
