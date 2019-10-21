package org.eclipse.basyx.submodel.metamodel.map.qualifier;

import java.util.HashMap;



/**
 * AAS description class
 * 
 * @author kuhn
 *
 */
public class Description extends HashMap<String, Object> {

		
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	public static final String LANGUAGE = "language";
	public static final String TEXT = "text";
	

	/**
	 * Constructor
	 */
	public Description() {
		// Add qualifiers
		put(LANGUAGE, "");
		put(TEXT, "");
	}
	
	public Description(String lang,String text) {
		// Add qualifiers
		put(LANGUAGE, lang);
		put(TEXT, text);
	}

	public String getLanguage() {
		return (String) get(LANGUAGE);
	}

	public String getText() {
		return (String) get(TEXT);
	}
}
