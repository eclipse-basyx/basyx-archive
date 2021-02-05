package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IBlob;
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

	@Override
	public String getValue() {
		Object connectedValue = getProxy().getModelPropertyValue(Property.VALUE);
		if (connectedValue instanceof String) {
			return (String) connectedValue;
		} else {
			return null;
		}
	}

	@Override
	public String getMimeType() {
		return (String)	getElem().get(Blob.MIMETYPE);
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.BLOB;
	}

	@Override
	public Blob getLocalCopy() {
		return Blob.createAsFacade(getElem()).getLocalCopy();
	}

	@Override
	public void setValue(String value) {
		if (value instanceof String) {
			// Assume a Base64 encoded String
			setValue((Object) value);
		} else {
			throw new IllegalArgumentException("Given Object is not a String");
		}
	}


	@Override
	public byte[] getByteArrayValue() {
		String value = getValue();
		if (value != null) {
			return Base64.getDecoder().decode(value);
		} else {
			return null;
		}
	}

	@Override
	public void setByteArrayValue(byte[] value) {
		setValue((Object) Base64.getEncoder().encodeToString(value));
	}

	@Override
	public String getASCIIString() {
		return getLocalCopy().getASCIIString();
	}

	@Override
	public void setASCIIString(String text) {
		byte[] byteArray = text.getBytes(StandardCharsets.US_ASCII);
		setByteArrayValue(byteArray);
	}
}

