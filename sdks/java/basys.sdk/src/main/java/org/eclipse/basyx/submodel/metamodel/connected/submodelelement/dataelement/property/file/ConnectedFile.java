package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property.file;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IFile;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedDataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
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
	public String getValue() {
		return (String) getProxy().getModelPropertyValue(Property.VALUE);
	}

	@Override
	public String getMimeType() {
		return (String) getProxy().getModelPropertyValue(Blob.MIMETYPE);
	}

}
