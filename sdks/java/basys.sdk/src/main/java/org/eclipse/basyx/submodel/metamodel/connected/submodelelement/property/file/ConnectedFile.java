package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.property.file;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.file.IFile;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.ConnectedDataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.blob.Blob;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
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
