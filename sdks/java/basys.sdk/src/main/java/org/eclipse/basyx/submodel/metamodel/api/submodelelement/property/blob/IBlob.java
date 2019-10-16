package org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.blob;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.IDataElement;

/**
 * Interface for Blob
 * @author rajashek
 *
*/
public interface IBlob extends IDataElement {
	
	public void setValue(byte[] value);
	
	public byte[] getValue();
	
	public void setMimeType(String mimeType);
	
	public String getMimeType();
	
	
}
