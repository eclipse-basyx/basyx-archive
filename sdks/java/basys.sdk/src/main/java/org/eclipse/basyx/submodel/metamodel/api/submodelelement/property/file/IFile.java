package org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.file;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;


/**
 * Interface for File 
 * @author rajashek
 *
*/
public interface IFile extends IDataElement {
	public void setValue(String path);
	
	public String getValue();
	
	public void setMimeType(String mimeType);
	
	public String getMimeType();

}
