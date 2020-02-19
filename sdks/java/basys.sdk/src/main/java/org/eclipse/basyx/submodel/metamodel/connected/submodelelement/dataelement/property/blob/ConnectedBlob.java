package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.property.blob;

import java.util.List;

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
		return convert((List<Integer>) getProxy().getModelPropertyValue(Property.VALUE));
	}

	@Override
	public String getMimeType() {
		return (String) getProxy().getModelPropertyValue(Blob.MIMETYPE);
	}

	/**
	 * Converts a list of integers to a byte array. <br />
	 * Needed since the VAB does only support lists
	 * 
	 * @param array
	 * @return
	 */
	private byte[] convert(List<Integer> array) {
		byte[] res = new byte[array.size()];
		for (int i = 0; i < array.size(); i++) {
			res[i] = array.get(i).byteValue();
		}
		return res;
	}
}

