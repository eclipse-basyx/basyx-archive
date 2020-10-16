package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IFile;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
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
		
		// FIXME: This is a hack, fix this when API is clear
		return (String) getProxy().getModelPropertyValue(Property.VALUE);
	}

	@Override
	public String getMimeType() {
		return (String) getElem().get(File.MIMETYPE);
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.FILE;
	}

}
