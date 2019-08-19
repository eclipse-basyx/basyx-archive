package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property.blob;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.blob.IBlob;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.ConnectedDataElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.MimeType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.blob.Blob;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.blob.BlobType;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IBlob
 * @author rajashek
 *
 */
public class ConnectedBlob extends ConnectedDataElement implements IBlob {
	public ConnectedBlob(String path, VABElementProxy proxy) {
		super(path, proxy);		
	}
	
	@Override
	public void setValue(BlobType value) {
		getProxy().updateElementValue(constructPath(Property.VALUE), value);
		
	}

	@Override
	public BlobType getValue() {
		return (BlobType)getProxy().readElementValue(constructPath(Property.VALUE));
	}

	@Override
	public void setMimeType(MimeType mimeType) {
		getProxy().updateElementValue(constructPath(Blob.MIMETYPE), mimeType);
		
	}

	@Override
	public MimeType getMimeType() {
		return (MimeType)getProxy().readElementValue(constructPath(Blob.MIMETYPE));
	}
}

