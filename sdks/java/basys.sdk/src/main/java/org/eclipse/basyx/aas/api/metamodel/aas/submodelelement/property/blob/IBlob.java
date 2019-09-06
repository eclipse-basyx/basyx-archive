package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.blob;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.MimeType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.blob.BlobType;

/**
 * Interface for Blob
 * @author rajashek
 *
*/
public interface IBlob extends IDataElement {
	
	public void setValue(BlobType value);
	
	public BlobType getValue();
	
	public void setMimeType(MimeType mimeType);
	
	public MimeType getMimeType();
	
	
}
