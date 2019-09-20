package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property.file;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.file.IFile;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.ConnectedDataElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.SingleProperty;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.blob.Blob;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;
/**
 * "Connected" implementation of IFile
 * @author rajashek
 *
 */
public class ConnectedFile extends ConnectedDataElement implements IFile {
	public ConnectedFile(VABElementProxy proxy) {
		super(proxy);		
	}
	
	@Override
	public void setValue(String value) {
		getProxy().setModelPropertyValue(SingleProperty.VALUE, value);
		
	}

	@Override
	public String getValue() {
		return (String) getProxy().getModelPropertyValue(SingleProperty.VALUE);
	}

	@Override
	public void setMimeType(String mimeType) {
		getProxy().setModelPropertyValue(Blob.MIMETYPE, mimeType);
		
	}

	@Override
	public String getMimeType() {
		return (String) getProxy().getModelPropertyValue(Blob.MIMETYPE);
	}

}
