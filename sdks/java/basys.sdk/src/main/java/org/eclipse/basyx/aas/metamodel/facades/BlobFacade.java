package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.blob.IBlob;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.MimeType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.blob.Blob;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.blob.BlobType;

/**
 * Facade providing access to a map containing the Blob structure
 * @author rajashek
 *
 */
public class BlobFacade implements IBlob {
	private Map<String, Object> map;

	public BlobFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setValue(BlobType value) {
		map.put(Property.VALUE, value);
		
	}

	@Override
	public BlobType getValue() {
		return (BlobType)map.get(Property.VALUE);
	}

	@Override
	public void setMimeType(MimeType mimeType) {
		map.put(Blob.MIMETYPE, mimeType);
		
	}

	@Override
	public MimeType getMimeType() {
		return (MimeType)map.get(Blob.MIMETYPE);
	}
}
