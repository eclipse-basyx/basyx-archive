package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IBlob;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * A blob element as defined in DAAS document <br/>
 * 
 * @author pschorn, schnicke
 *
 */
public class Blob extends DataElement implements IBlob {
	public static final String MIMETYPE="mimeType";
	public static final String MODELTYPE = "blob";
	
	/**
	 * Creates an empty Blob object
	 */
	public Blob() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}

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
		// Add model type
		putAll(new ModelType(MODELTYPE));

		setValue(value);
		setMimeType(mimeType);
	}
	
	/**
	 * Creates a Blob object from a map
	 * 
	 * @param obj a Blob object as raw map
	 * @return a Blob object, that behaves like a facade for the given map
	 */
	public static Blob createAsFacade(Map<String, Object> obj) {
		Blob facade = new Blob();
		facade.setMap(obj);
		return facade;
	}
	
	/**
	 * Returns true if the given submodel element map is recognized as a blob
	 */
	public static boolean isBlob(Map<String, Object> map) {
		String modelType = ModelType.createAsFacade(map).getName();
		// Either model type is set or the element type specific attributes are contained (fallback)
		// Note: Fallback is ambiguous - File has exactly the same attributes
		// => would need value parsing in order to be able to differentiate
		return MODELTYPE.equals(modelType)
				|| (modelType == null && (map.containsKey(Property.VALUE) && map.containsKey(MIMETYPE)));
	}

	public void setValue(byte[] value) {
		put(Property.VALUE, new String(value));
		
	}

	@Override
	public byte[] getValue() {
		return ((String) get(Property.VALUE)).getBytes();
	}

	public void setMimeType(String mimeType) {
		put(Blob.MIMETYPE, mimeType);
		
	}

	@Override
	public String getMimeType() {
		return (String) get(Blob.MIMETYPE);
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.BLOB;
	}
}
