package org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement;

/**
 * A BLOB is a data element that represents a file that is contained with its
 * source code in the value attribute.
 * 
 * @author rajashek, schnicke
 *
 */
public interface IBlob extends IDataElement {
	/**
	 * Gets the value of the BLOB instance of a blob data element.
	 * 
	 * @return
	 */
	public byte[] getValue();
	
	/**
	 * Gets the mime type of the content of the BLOB. The mime type states which
	 * file extension the file has. <br />
	 * Valid values are e.g. “application/json”, “application/xls”, ”image/jpg”.
	 * <br />
	 * The allowed values are defined as in RFC2046.
	 * 
	 * @return
	 */
	public String getMimeType();
}
