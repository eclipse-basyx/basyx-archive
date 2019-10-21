package org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.file;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.property.file.IFile;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.DataElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.blob.Blob;

/**
 * A blob property as defined in DAAS document <br/>
 * 
 * @author pschorn, schnicke
 *
 */
public class File extends DataElement implements IFile{
	private static final long serialVersionUID = 1L;
	
	public static final String MIMETYPE="mimeType";
	public static final String MODELTYPE = "File";


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
	public File(String value, String mimeType) {
		// Add model type
		putAll(new ModelType(MODELTYPE));

		// Save value
		put(SingleProperty.VALUE, value);
		put(MIMETYPE, mimeType);
	}


	@Override
	public void setValue(String value) {
		put(SingleProperty.VALUE, value);
		
	}

	@Override
	public String getValue() {
		return (String) get(SingleProperty.VALUE);
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
