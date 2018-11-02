package org.eclipse.basyx.components.cfgprovider;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.sdk.provider.hashmap.VABHashmapProvider;
import org.eclipse.basyx.sdk.provider.hashmap.aas.Submodel;
import org.eclipse.basyx.sdk.provider.hashmap.aas.property.PropertySingleValued;
import org.eclipse.basyx.sdk.provider.hashmap.aas.qualifier.Identification;
import org.eclipse.basyx.sdk.templates.aas.SubmodelTemplate;
import org.eclipse.basyx.sdk.templates.aas.SubmodelTemplateIRDISemantics;
import org.eclipse.basyx.sdk.templates.aas.SubmodelTemplateInternalSemantics;



/**
 * Asset administration shell sub model provider that exports a properties file
 * 
 * @author kuhn
 *
 */
public class CFGSubModelProvider extends VABHashmapProvider {

	
	/**
	 * This is a sub model
	 */
	protected Submodel submodelData = null;

		
	
	
	/**
	 * Constructor
	 */
	public CFGSubModelProvider(Map<Object, Object> cfgValues) {
		// Call base constructor
		super();

		
		// Create sub model
		submodelData = createSubModel(cfgValues);

		// Load predefined elements from sub model
		elements.putAll(submodelData);

		// Add properties
		for (String key: getConfiguredProperties(cfgValues)) {
			// Create example properties
			submodelData.getProperties().put(key.toString(), createPropertySingleValues(key, cfgValues.get(key), cfgValues));
			
			// Debug output
			System.out.println("Adding property: "+key.toString()+" = "+cfgValues.get(key));
		}
		
		// Print configuration values
		System.out.println("CFG exported");
	}

	
	
	/**
	 * Split a comma delimited string
	 * 
	 * @param input    String input
	 */
	protected Collection<String> splitString(String input) {
		// Return value
		HashSet<String> result = new HashSet<>();
		
		// Split string into segments
		for (String inputStr: input.split(",")) result.add(inputStr.trim());
		
		// Return result
		return result;
	}
	
	
	
	/**
	 * Get list of configured properties
	 * 
	 * @param cfgValues    Provider configuration
	 */
	protected Collection<String> getConfiguredProperties(Map<Object, Object> cfgValues) {
		// Split property string
		return splitString((String) cfgValues.get("properties"));
	}

	
	
	/**
	 * Create BaSys sub model based on configuration data
	 * 
	 * @param cfgValues    Provider configuration
	 */
	protected Submodel createSubModel(Map<Object, Object> cfgValues) {
		// Create sub model
		Submodel submodel = null;

		// Try to load and convert configuration values. Keep value null if any error occurs
		String basyx_submodelSemantics = null; try {basyx_submodelSemantics = (String) cfgValues.get("basyx.submodelSemantics").toString().toLowerCase();} catch (Exception e) {}
		String basyx_semantics         = null; try {basyx_semantics         = (String) cfgValues.get("basyx.semantics");} catch (Exception e) {}
		String basyx_idType            = null; try {basyx_idType            = (String) cfgValues.get("basyx.idType").toString().toLowerCase();} catch (Exception e) {}
		String basyx_id                = null; try {basyx_id                = (String) cfgValues.get("basyx.id").toString();} catch (Exception e) {}
		String basyx_idShort           = null; try {basyx_idShort           = (String) cfgValues.get("basyx.idShort").toString();} catch (Exception e) {}
		String basyx_category          = null; try {basyx_category          = (String) cfgValues.get("basyx.category").toString();} catch (Exception e) {}
		String basyx_description       = null; try {basyx_description       = (String) cfgValues.get("basyx.description").toString();} catch (Exception e) {}
		String basyx_qualifier         = null; try {basyx_qualifier         = (String) cfgValues.get("basyx.qualifier").toString();} catch (Exception e) {}
		String basyx_version           = null; try {basyx_version           = (String) cfgValues.get("basyx.version").toString();} catch (Exception e) {}
		String basyx_revision          = null; try {basyx_revision          = (String) cfgValues.get("basyx.revision").toString();} catch (Exception e) {}

		// Process ID Type - default value is internal
		int idType = Identification.Internal;
		// - Compare to known values
		if (basyx_idType.equals("Identification.IRDI"))     idType = Identification.IRDI;
		if (basyx_idType.equals("Identification.URI"))      idType = Identification.URI;
		if (basyx_idType.equals("Identification.Internal")) idType = Identification.Internal;
		
		
		// Try to load properties
		// Check type of sub model template to use
		if (basyx_submodelSemantics.equals("irdi")) {
			// Create sub model from template
			SubmodelTemplate template = new SubmodelTemplateIRDISemantics(basyx_semantics, idType, basyx_id, basyx_idShort, basyx_category, basyx_description, basyx_qualifier, basyx_version, basyx_revision);
			
			// Get sub model data
			submodel = template.getSubModel();
		};
		if (basyx_submodelSemantics.equals("internal")) {
			// Create sub model from template
			SubmodelTemplate template = new SubmodelTemplateInternalSemantics(basyx_semantics, idType, basyx_id, basyx_idShort, basyx_category, basyx_description, basyx_qualifier, basyx_version, basyx_revision);			
			
			// Get sub model data
			submodel = template.getSubModel();
		}
		
		// If no sub model was created, create an empty one
		if (submodel == null) submodel = new Submodel();
		
		// Return sub model data
		return submodel;
	}
	
	
	
	/**
	 * Create a single valued property with given name
	 * 
	 * @param propertyName  Property name
	 * @param propertyValue Property value
	 * @param cfgValues     Provider configuration
	 */
	protected PropertySingleValued createPropertySingleValues(String propertyName, Object propertyValue, Map<Object, Object> cfgValues) {
		
		// Try to get property meta data
		String property_semanticsInternal = null; try {property_semanticsInternal = (String) cfgValues.get(propertyName+".semanticsInternal").toString();} catch (Exception e) {}
		String property_category          = null; try {property_category          = (String) cfgValues.get(propertyName+".category").toString();} catch (Exception e) {}
		String property_description       = null; try {property_description       = (String) cfgValues.get(propertyName+".description").toString();} catch (Exception e) {}
		String property_qualifier         = null; try {property_qualifier         = (String) cfgValues.get(propertyName+".qualifier").toString();} catch (Exception e) {}

		
		// Create and return single valued property
		return new PropertySingleValued(propertyValue, property_semanticsInternal, propertyName, property_category, property_description, property_qualifier);
	}
}

