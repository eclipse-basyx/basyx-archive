package org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.property.file;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IDataElement;


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
	public String getValue();
	
	/**
	 * Gets mime type of the content of the file.
	 * 
	 * @return
	 */
	public String getMimeType();

}
