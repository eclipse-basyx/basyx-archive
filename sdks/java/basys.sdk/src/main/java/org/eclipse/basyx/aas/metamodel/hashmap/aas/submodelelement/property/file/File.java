package org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.file;

import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.property.file.IFile;
import org.eclipse.basyx.aas.metamodel.facades.FileFacade;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.DataElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.MimeType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;

/**
 * A blob property as defined in DAAS document <br/>
 * 
 * @author pschorn, schnicke
 *
 */
public class File extends DataElement implements IFile{
	private static final long serialVersionUID = 1L;
	
	public static final String MIMETYPE="mimeType";


	/**
	 * Creates a file data element. It has to have a mimeType <br/>
	 * An absolute path is used in the case that the file exists independently of
	 * the AAS. A relative path, relative to the package root should be used if the
	 * file is part of the serialized package of the AAS.
	 * 
	 * @param value
	 *            path and name of the referenced file (without file extension); can
	 *            be absolute or relative
	 * @param mimeType
	 *            states which file extension the file has; Valid values are defined
	 *            in <i>RFC2046</i>, e.g. <i>image/jpg</i>
	 */
	public File(PathType value, MimeType mimeType) {
		super();

		// Save value
		put(Property.VALUE, value);
		put(MIMETYPE, mimeType);
	}


	@Override
	public void setValue(PathType value) {
		new FileFacade(this).setValue(value);
		
	}

	@Override
	public PathType getValue() {
		return new FileFacade(this).getValue();
	}

	@Override
	public void setMimeType(MimeType mimeType) {
		new FileFacade(this).setMimeType(mimeType);
		
	}

	@Override
	public MimeType getMimeType() {
		return new FileFacade(this).getMimeType();
	}
}
