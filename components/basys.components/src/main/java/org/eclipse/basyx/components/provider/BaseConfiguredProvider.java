package org.eclipse.basyx.components.provider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.aas.backend.provider.VirtualPathModelProvider;
import org.eclipse.basyx.aas.metamodel.facades.SubmodelFacade;
import org.eclipse.basyx.aas.metamodel.facades.SubmodelFacadeCustomSemantics;
import org.eclipse.basyx.aas.metamodel.facades.SubmodelFacadeIRDISemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.haskind.Kind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.qualifiable.Qualifier;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;

/**
 * Base class for providers that receiver their configuration through a configuration properties object
 * 
 * @author kuhn
 *
 */

public class BaseConfiguredProvider extends VirtualPathModelProvider {

	/**
	 * This is a sub model
	 */
	protected SubModel submodelData = null;

	/**
	 * Constructor
	 */
	public BaseConfiguredProvider(Map<Object, Object> cfgValues) {
		// Invoke base constructor
		// super(new VABHashmapProvider(new HashMap<String, Object>()));
		super();

		// Create sub model
		submodelData = createSubModel(cfgValues);

		// Load predefined elements from sub model
		try {
			setModelPropertyValue("", submodelData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Split a comma delimited string
	 * 
	 * @param input
	 *            String input
	 */
	protected Collection<String> splitString(String input) {
		// Return value
		HashSet<String> result = new HashSet<>();

		// Split string into segments
		for (String inputStr : input.split(","))
			result.add(inputStr.trim());

		// Return result
		return result;
	}

	/**
	 * Get list of configured properties
	 * 
	 * @param cfgValues
	 *            Provider configuration
	 */
	protected Collection<String> getConfiguredProperties(Map<Object, Object> cfgValues) {
		// Split property string
		return splitString((String) cfgValues.get("dataElements"));
	}

	/**
	 * Output a hash map
	 */
	@SuppressWarnings({ "rawtypes" })
	protected void printHashMap(Map map, int indent) {
		// Process map
		for (Object key : map.keySet()) {
			// Process map element
			if (map.get(key) instanceof Map) {
				// Output key
				for (int i = 0; i < indent; i++)
					System.out.print(" ");
				System.out.println("  " + key);
				// Output hash map
				printHashMap((Map) map.get(key), indent + 2);
			} else {
				// Output element
				for (int i = 0; i < indent; i++)
					System.out.print(" ");
				System.out.println("  " + key + " = " + map.get(key));
			}
		}
	}

	/**
	 * Split a key based on path '/' separators
	 */
	protected String[] splitPath(String path) {
		return path.split("/");
	}

	/**
	 * Create BaSys sub model based on configuration data
	 * 
	 * @param cfgValues
	 *            Provider configuration
	 */
	protected SubModel createSubModel(Map<Object, Object> cfgValues) {
		// Create sub model
		SubModel submodel = null;

		// Try to load and convert configuration values. Keep value null if any error occurs
		String basyx_submodelSemantics = null;
		try {
			basyx_submodelSemantics = cfgValues.get("basyx.submodelSemantics").toString().toLowerCase();
		} catch (Exception e) {
		}
		String basyx_idType = null;
		try {
			basyx_idType = cfgValues.get("basyx.idType").toString().toLowerCase();
		} catch (Exception e) {
		}
		String basyx_id = null;
		try {
			basyx_id = cfgValues.get("basyx.id").toString();
		} catch (Exception e) {
		}
		String basyx_idShort = null;
		try {
			basyx_idShort = cfgValues.get("basyx.idShort").toString();
		} catch (Exception e) {
		}
		String basyx_category = null;
		try {
			basyx_category = cfgValues.get("basyx.category").toString();
		} catch (Exception e) {
		}
		String basyx_description = null;
		try {
			basyx_description = cfgValues.get("basyx.description").toString();
		} catch (Exception e) {
		}
		String basyx_qualifier = null;
		try {
			basyx_qualifier = cfgValues.get("basyx.qualifier").toString();
		} catch (Exception e) {
		}
		String basyx_qualifierType = null;
		try {
			basyx_qualifierType = cfgValues.get("basyx.qualifierType").toString();
		} catch (Exception e) {
		}
		String basyx_version = null;
		try {
			basyx_version = cfgValues.get("basyx.version").toString();
		} catch (Exception e) {
		}
		String basyx_revision = null;
		try {
			basyx_revision = cfgValues.get("basyx.revision").toString();
		} catch (Exception e) {
		}

		// Process ID Type - default value is internal
		String idType = IdentifierType.Custom;
		// - Compare to known values
		if (basyx_idType == null)
			basyx_idType = "IdentifierType.Custom";
		if (basyx_idType.equals("IdentifierType.IRDI"))
			idType = IdentifierType.IRDI;
		if (basyx_idType.equals("IdentifierType.URI"))
			idType = IdentifierType.URI;
		if (basyx_idType.equals("IdentifierType.Custom"))
			idType = IdentifierType.Custom;

		// Try to load properties
		// Check type of sub model template to use
		if (basyx_submodelSemantics == null)
			basyx_submodelSemantics = "custom";
		if (basyx_submodelSemantics.equals("irdi")) {
			// Create sub model from template
			SubmodelFacade template = new SubmodelFacadeIRDISemantics(basyx_submodelSemantics, idType, basyx_id,
					basyx_idShort, basyx_category, basyx_description,
					new Qualifier(basyx_qualifierType, basyx_qualifier, null), null, Kind.Instance, basyx_version,
					basyx_revision);
			// Get sub model data
			submodel = template.getSubModel();
		}
		;
		if (basyx_submodelSemantics.equals("custom")) {
			// Create sub model from template
			SubmodelFacade template = new SubmodelFacadeCustomSemantics(basyx_submodelSemantics, idType, basyx_id,
					basyx_idShort, basyx_category, basyx_description,
					new Qualifier(basyx_qualifierType, basyx_qualifier, null), new HasDataSpecification(),
					Kind.Instance, basyx_version, basyx_revision);
			// Get sub model data
			submodel = template.getSubModel();
		}

		// If no sub model was created, create an empty one
		if (submodel == null)
			submodel = new SubModel();

		// Return sub model data
		return submodel;
	}

	/**
	 * Create a property with given name
	 * 
	 * @param propertyName
	 *            Property name
	 * @param propertyValue
	 *            Property value
	 * @param cfgValues
	 *            Provider configuration
	 */
	protected Property createSubmodelElement(String propertyName, Object propertyValue, Map<Object, Object> cfgValues) {

		// Get property type
		String propertyType = cfgValues.get(propertyName + ".type").toString();

		// Dispatch to requested create function
		if (propertyType.equals("Property"))
			return createProperty(propertyName, propertyValue, cfgValues);

		// Do not return anything
		return null;
	}

	/**
	 * Create a single valued property with given name
	 * 
	 * @param propertyName
	 *            Property name
	 * @param propertyValue
	 *            Property value
	 * @param cfgValues
	 *            Provider configuration
	 */
	protected Property createProperty(String propertyName, Object propertyValue, Map<Object, Object> cfgValues) {

		// Try to get property meta data
		String property_semanticsInternal = null;
		try {
			property_semanticsInternal = cfgValues.get(propertyName + ".semanticsInternal").toString();
		} catch (Exception e) {
		}
		String property_qualifier = null;
		try {
			property_qualifier = cfgValues.get(propertyName + ".qualifier").toString();
		} catch (Exception e) {
		} // might need to rename to constraints
		String property_qualifierType = null;
		try {
			property_qualifierType = cfgValues.get(propertyName + ".qualifierType").toString();
		} catch (Exception e) {
		}
		String property_description = null;
		try {
			property_description = cfgValues.get(propertyName + ".description").toString();
		} catch (Exception e) {
		}

		// Create and return single valued property
		Property prop = new Property(propertyValue, new Referable(propertyName, "", property_description),
				property_semanticsInternal, new Qualifier(property_qualifierType, property_qualifier, null));
		return prop;
	}
}
