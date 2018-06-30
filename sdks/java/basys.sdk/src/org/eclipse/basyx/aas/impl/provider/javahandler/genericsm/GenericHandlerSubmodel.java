package org.eclipse.basyx.aas.impl.provider.javahandler.genericsm;

import java.util.ArrayList;
import java.util.Vector;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.impl.provider.javahandler.JavaHandler;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Operation;
import org.eclipse.basyx.aas.impl.resources.basic.ParameterType;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;




/**
 * Implement a generic sub model class for use with the Java Handler Provider
 * 
 * @author kuhn
 *
 */
public class GenericHandlerSubmodel extends SubModel {


	/**
	 * Store AAS handler
	 */
	protected JavaHandler<IAssetAdministrationShell> aasHandler = null;


	/**
	 * Store sub model handler
	 */
	protected JavaHandler<ISubModel> smHandler = null;

	
	/**
	 * Store properties
	 */
	protected Vector<Object> propertyValues = new Vector<>();
	
	
	/**
	 * Store operations
	 */
	protected Vector<GenericHandlerOperation> operations = new Vector<>();
	
	
	
	/**
	 * Constructor
	 * 
	 * @param assKind Asset kind (type or instance)
	 * @param name    Human readable name (informative)
	 * @param id      Unique sub model ID
	 * @param type    Sub model type name
	 */
	public GenericHandlerSubmodel(AssetKind assKind, String name, String id, String type, String aasName, String aasId) {
		// Initialize this sub model
	    this.setAssetKind(assKind);
	    this.setName(name);
	    this.setId(id);
	    this.setTypeDefinition(type);
	    
	    // Initialize dummy AAS
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId(aasId);
	    aas.setName(aasName);
	    aas.addSubModel(this);
	    this.setParent(aas);
	    
	    // Create dummy AAS handler
		aasHandler = new JavaHandler<IAssetAdministrationShell>(aas);
		// -FIXME: Add/remove support
		aasHandler.addProperty("subModels", (obj) -> {return aas.getSubModels();},  null,  null,  null);

		// Create sub model handler
		smHandler = new JavaHandler<ISubModel>(this);
		// - Add core sub model properties
		// -FIXME: Add/remove support
		smHandler.addProperty("properties", (obj) -> {return this.getProperties();},  null,  null,  null);
		smHandler.addProperty("operations", (obj) -> {return this.getOperations();},  null,  null,  null);
		smHandler.addProperty("clock",      (obj) -> {return this.clock;},  (obj, val) -> {this.clock = (int) val;},  null,  null);
	}
	
	
	
	
	/**
	 * Add a regular value property
	 * 
	 * @param name         Property name
	 * @param type         Property type
	 * @param initialValue Property initial value
	 */
	public void addProperty(String name, DataType type, Object initialValue) {
		// Add property descriptor to sub model
		// - Create property descriptor
		Property newProperty = new Property();
		// - Add descriptor values
		newProperty.setName(name);
		newProperty.setDataType(type);
		newProperty.setId(name);
		// - Add property descriptor to sub model
		this.addProperty(newProperty);

		// Add property value to this provider type
		// - Get property index
		final int propertyIndex = propertyValues.size();
		// - Add property value to value vector
		propertyValues.add(initialValue);
		// - Add property handler
		smHandler.addProperty(name,  (obj) -> {return propertyValues.get(propertyIndex);},  (obj, val) -> {propertyValues.set(propertyIndex, val);},  null,   null);
	}
	
	
	/**
	 * Add an operation
	 * 
	 * @param name    Operation name
	 * @param opImpl  Operation implementation
	 */
	public void addOperation(String name, ArrayList<ParameterType> parameterTypes, DataType returnType, GenericHandlerOperation opImpl) {
		// Add property descriptor to sub model
		// - Create property descriptor
		Operation newOperation = new Operation();
		// - Add descriptor values
		newOperation.setName(name);
		newOperation.setParameterTypes(parameterTypes);
		newOperation.setReturnDataType(returnType);
		newOperation.setId(name);
		// - Add operation descriptor to sub model
		this.addOperation(newOperation);

		
		// Add operation to this handler
		// - Add operation implementation to operation vector
		operations.add(opImpl);
		// - Add operation handler
		smHandler.addOperation(name,  (obj, val) -> {return opImpl.apply(this, val);});
	}
	
	
	/**
	 * Get AAS handler
	 */
	public JavaHandler<IAssetAdministrationShell> getAASHandler() {
		return aasHandler;
	}
	
	
	/**
	 * Get sub model handler
	 */
	public JavaHandler<ISubModel> getSubModelHandler() {
		return smHandler;
	}
}
