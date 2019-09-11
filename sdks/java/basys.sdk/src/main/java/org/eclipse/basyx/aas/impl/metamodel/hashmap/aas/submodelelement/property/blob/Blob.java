package org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.blob;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.blob.IBlob;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.DataElement;
import org.eclipse.basyx.aas.impl.metamodel.hashmap.aas.submodelelement.property.Property;

/**
 * A blob element as defined in DAAS document <br/>
 * 
 * @author pschorn, schnicke
 *
 */
public class Blob extends DataElement implements IBlob{
	private static final long serialVersionUID = 1L;
	
	public static final String MIMETYPE="mimeType";

	/**
	 * Has to have a MimeType
	 * 
	 * @param value
	 *            the value of the BLOB instance of a blob data element
	 * @param mimeType
	 *            states which file extension the file has; Valid values are defined
	 *            in <i>RFC2046</i>, e.g. <i>image/jpg</i>
	 */
	public Blob(byte[] value, String mimeType) {
		super();

		put(Property.VALUE, value);
		put(MIMETYPE, mimeType);
	}

	@Override
	public void setValue(byte[] value) {
		put(Property.VALUE, value);
		
	}

	@Override
	public byte[] getValue() {
		return (byte[]) get(Property.VALUE);
	}

	@Override
	public void setMimeType(String mimeType) {
		put(Blob.MIMETYPE, mimeType);
		
	}

	@Override
	public String getMimeType() {
		return (String) get(Blob.MIMETYPE);
	}
}
