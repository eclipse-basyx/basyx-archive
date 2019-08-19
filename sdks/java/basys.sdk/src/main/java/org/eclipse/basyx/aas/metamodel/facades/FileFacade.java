package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.file.IFile;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.MimeType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.blob.Blob;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.file.PathType;
/**
 * Facade providing access to a map containing the File structure
 * @author rajashek
 *
 */
public class FileFacade implements IFile {
	private Map<String, Object> map;

	public FileFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setValue(PathType value) {
		map.put(Property.VALUE, value);
		
	}

	@Override
	public PathType getValue() {
		return (PathType)map.get(Property.VALUE);
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
