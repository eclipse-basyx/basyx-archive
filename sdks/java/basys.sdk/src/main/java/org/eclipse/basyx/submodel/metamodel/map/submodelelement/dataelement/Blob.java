package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement;

import java.util.ArrayList;
import java.util.List;
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
	public static final String MODELTYPE = "Blob";
	
	/**
	 * Creates an empty Blob object
	 */
	public Blob() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}
	
	/**
	 * Constructor accepting only mandatory attribute
	 * @param idShort
	 * @param mimeType
	 */
	public Blob(String idShort, String mimeType) {
		super(idShort);
		putAll(new ModelType(MODELTYPE));
		setMimeType(mimeType);
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

	@Override
	public void setValue(Object value) {
		if(value instanceof byte[]) {
			List<Byte> list = new ArrayList<>();
			
			byte[] bytes = (byte[]) value;
			
			for (int i = 0; i < bytes.length; i++) {
				list.add(bytes[i]);
			}
			
			put(Property.VALUE, list);
		}
		else {
			throw new IllegalArgumentException("Given Object is not a byte[]");
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] getValue() {
		if(!containsKey(Property.VALUE)) {
			return null;
		}
		List<Number> list = (List<Number>) get(Property.VALUE);
		byte[] ret = new byte[list.size()];
		
		for(int i = 0; i < list.size(); i++) {
			ret[i] = list.get(i).byteValue();
		}
		
		return ret;
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
