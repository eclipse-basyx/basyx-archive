package org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.blob;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.blob.IBlob;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;

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

		put(SingleProperty.VALUE, value);
		put(MIMETYPE, mimeType);
	}

	@Override
	public void setValue(byte[] value) {
		put(SingleProperty.VALUE, value);
		
	}

	@Override
	public byte[] getValue() {
		return (byte[]) get(SingleProperty.VALUE);
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