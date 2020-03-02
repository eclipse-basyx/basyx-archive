package org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IFile;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * A blob property as defined in DAAS document <br/>
 * 
 * @author pschorn, schnicke
 *
 */
public class File extends DataElement implements IFile{
	public static final String MIMETYPE="mimeType";
	public static final String MODELTYPE = "File";

	/**
	 * Creates an empty File object
	 */
	public File() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}
	
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
		put(Property.VALUE, value);
		put(MIMETYPE, mimeType);
	}
	
	/**
	 * Creates a File object from a map
	 * 
	 * @param obj a File object as raw map
	 * @return a File object, that behaves like a facade for the given map
	 */
	public static File createAsFacade(Map<String, Object> obj) {
		File facade = new File();
		facade.setMap(obj);
		return facade;
	}

	public void setValue(String value) {
		put(Property.VALUE, value);
	}

	@Override
	public String getValue() {
		return (String) get(Property.VALUE);
	}

	public void setMimeType(String mimeType) {
		put(File.MIMETYPE, mimeType);
	}

	@Override
	public String getMimeType() {
		return (String) get(File.MIMETYPE);
	}
}
