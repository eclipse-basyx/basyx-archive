package org.eclipse.basyx.aas.metamodel.hashmap.aas.dataspecification;

import java.util.HashMap;

import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.reference.Reference;

/**
 * DataSpecification class
 * 
 * @author elsheikh
 *
 */
public class DataSpecification extends HashMap<String, Object> {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public DataSpecification() {
		// Add Identifiable class
		putAll(new Identifiable());

		// Default values
		put("preferredName", null);
		put("shortName", null);
		put("unit", null);
		put("unitId", null);
		put("sourceOfDefinition", null);
		put("symbol", null);
		put("dataType", null);
		put("definition", null);
		put("valueFormat", null);
		put("valueList", null);
		put("code", null);
	}

	public DataSpecification(String preferredName, String shortName, String uni, Reference unitId, String sourceOfDefinition, String symbol, String dataType, String definition, String valueFormat/* , Valuelist valueList, Code code */) {
		// Add Identifiable class
		putAll(new Identifiable());

		// Default values
		put("preferredName", preferredName);
		put("shortName", shortName);
		put("unit", uni);
		put("unitId", null);
		put("sourceOfDefinition", sourceOfDefinition);
		put("symbol", symbol);
		put("dataType", dataType);
		put("definition", definition);
		put("valueFormat", valueFormat);
		put("valueList", null);
		put("code", null);
	}
}
