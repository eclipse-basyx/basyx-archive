package org.eclipse.basyx.testsuite.support.backend.http.tools.stubs.vab.elements;

import java.util.function.Function;

import org.eclipse.basyx.aas.api.exception.ServerException;
import org.eclipse.basyx.sdk.provider.hashmap.VABHashmapProvider;
import org.eclipse.basyx.sdk.provider.hashmap.aas.Submodel;
import org.eclipse.basyx.sdk.provider.hashmap.aas.property.PropertySingleValued;





/**
 * A simple VAB submodel element that explains the development of conforming submodels using the VAB hashmap provider
 * 
 * @author kuhn
 *
 */
public class SimpleAASSubmodel extends VABHashmapProvider {	
	
	
	/**
	 * This is a sub model
	 */
	protected Submodel submodelData = null;
	
	
	/**
	 * Constructor
	 */
	public SimpleAASSubmodel() {
		// Create sub model
		submodelData = new Submodel();

		// Load predefined elements from submodel
		elements.putAll(submodelData);

		
		
		// Create example properties
		submodelData.getProperties().put("prop1", new PropertySingleValued(123));
		
		// Create example operations
		submodelData.getOperations().put("operation1", (Function<Object[], Object>) (v) -> {return submodelData.getProperties().get("prop1");});
		submodelData.getOperations().put("operation2", (Function<Object[], Object>) (p) -> {return submodelData.getProperties().get(p[0]);});
		
		// Create example operations 
		// - Contained operation that throws native JAVA exception
		submodelData.getOperations().put("operationEx1", (Function<Object[], Object>) (elId) -> {throw new NullPointerException();});
		// - Contained operation that throws VAB exception
		submodelData.getOperations().put("operationEx2", (Function<Object[], Object>) (elId) -> {throw new ServerException("ExType", "Exception description");});
	}
}
