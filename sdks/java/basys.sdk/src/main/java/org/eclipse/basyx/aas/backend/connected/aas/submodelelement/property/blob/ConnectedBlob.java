package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property.blob;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.blob.IBlob;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.ConnectedDataElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.MimeType;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.blob.Blob;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.blob.BlobType;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IBlob
 * @author rajashek
 *
 */
public class ConnectedBlob extends ConnectedDataElement implements IBlob {
	public ConnectedBlob(VABElementProxy proxy) {
		super(proxy);		
	}
	
	@Override
	public void setValue(BlobType value) {
		getProxy().setModelPropertyValue(Property.VALUE, value);
		
	}

	@Override
	public BlobType getValue() {
		return (BlobType)getProxy().getModelPropertyValue(Property.VALUE);
	}

	@Override
	public void setMimeType(MimeType mimeType) {
		getProxy().setModelPropertyValue(Blob.MIMETYPE, mimeType);
		
	}

	@Override
	public MimeType getMimeType() {
		return (MimeType)getProxy().getModelPropertyValue(Blob.MIMETYPE);
	}
}

