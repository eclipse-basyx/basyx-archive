package org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement;

/**
 * A File is a data element that represents an address to a file. The value is
 * an URI that can represent an absolute or relative path.
 * 
 * @author rajashek, schnicke
 *
 */
public interface IFile extends IDataElement {
	/**
	 * Gets path and name of the referenced file (with file extension). The path can
	 * be absolute or relative.
	 * 
	 * @return
	 */
	@Override
	public String getValue();
	
	/**
	 * Sets path and name of the referenced file (with file extension). The path can
	 * be absolute or relative.
	 * 
	 * @param value
	 */
	public void setValue(String value);

	/**
	 * Gets mime type of the content of the file.
	 * 
	 * @return
	 */
	public String getMimeType();

}
