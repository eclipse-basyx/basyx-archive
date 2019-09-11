package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.blob;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IDataElement;

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
