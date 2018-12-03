package org.eclipse.basyx.aas.metamodel.hashmap.aas.property;

import java.util.HashMap;

import org.eclipse.basyx.aas.api.resources.IProperty;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.impl.resources.basic.BaseElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Qualifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Typable;



/**
 * Abstract property class
 * 
 * @author kuhn
 *
 */
public class Property extends HashMap<String, Object> implements IProperty {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Constructor
	 */
	public Property() {
		// Add qualifiers
		// putAll(new HasTemplate()); // This is also not in the vwid document
		putAll(new Referable());
		putAll(new Qualifiable());
		putAll(new HasSemantics());
		putAll(new Typable());

		// Default values
		// put("id_carrier", null);
		// put("id_submodelDefinition", null); Note: These values are not in vwid
		// document for this class

		// Properties
		// put("properties", new HashMap<String, Object>()); // This is also not in the
		// vwid document
	}

	
	/**
	 * Constructor
	 */
	public Property(HasSemantics semantics, Referable referable, Qualifiable qualifiable, Typable typeable) {
		// Add qualifiers
		//putAll(new HasTemplate());
		putAll(referable);
		putAll(qualifiable);
		putAll(semantics);
		putAll(typeable);
	}

	
	@Override
	public DataObjectType getDataType() {
		return null;
	}

	@Override
	public PropertyType getPropertyType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		return (String) get("idShort");
	}

	@Override
	public void setId(String id) {
		put("idShort", id);
	}

	@Override
	public void setParent(BaseElement parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public BaseElement getParent() {
		// TODO Auto-generated method stub
		return null;
	}
}
