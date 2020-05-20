package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IFile;
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

	@SuppressWarnings("unchecked")
	@Override
	public String getValue() {
		
		// FIXME: This is a hack, fix this when API is clear
		Property value = Property.createAsFacade((Map<String, Object>) getProxy().getModelPropertyValue(Property.VALUE));
		return (String) value.get();
	}

	@Override
	public String getMimeType() {
		return (String) getProxy().getModelPropertyValue(Blob.MIMETYPE);
	}

}
