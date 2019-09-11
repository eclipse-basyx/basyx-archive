package org.eclipse.basyx.aas.backend.connected.aas.submodelelement.property.blob;

import java.util.List;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.blob.IBlob;
import org.eclipse.basyx.aas.backend.connected.aas.submodelelement.ConnectedDataElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.blob.Blob;
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
	public void setValue(byte[] value) {
		getProxy().setModelPropertyValue(Property.VALUE, value);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] getValue() {
		return convert((List<Integer>) getProxy().getModelPropertyValue(Property.VALUE));
	}

	@Override
	public void setMimeType(String mimeType) {
		getProxy().setModelPropertyValue(Blob.MIMETYPE, mimeType);
		
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

