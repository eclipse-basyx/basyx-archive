package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.file;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.MimeType;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.file.PathType;


/**
 * Interface for File 
 * @author rajashek
 *
*/
public interface IFile extends IDataElement {
	public void setValue(PathType value);
	
	public PathType getValue();
	
	public void setMimeType(MimeType mimeType);
	
	public MimeType getMimeType();

}
