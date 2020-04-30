package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property.blob;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IBlob;
import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedDataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Blob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of IBlob
 * @author rajashek
 *
 */
public class ConnectedBlob extends ConnectedDataElement implements IBlob {
	
	public ConnectedBlob(VABElementProxy proxy) {
		super(proxy);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] getValue() {
		
		// FIXME: This is a hack, fix this when API is clear
		Property value = Property.createAsFacade((Map<String, Object>) getProxy().getModelPropertyValue(Property.VALUE));
		return ((String) value.get()).getBytes();
	}

	@Override
	public String getMimeType() {
		return (String) getProxy().getModelPropertyValue(Blob.MIMETYPE);
	}
}

