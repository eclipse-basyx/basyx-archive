package org.eclipse.basyx.sandbox.models.manufacturing.process.product;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;





/**
 * A product quality sub model example
 * 
 * - Every product has a quality sub model that tracks the product quality
 * 
 * @author kuhn
 *
 */
public class ProductQualitySubmodel extends SubModel {
	/**
	 * List of production quality data
	 */
	protected List<String> qualityData = new LinkedList<String>();

	
	
	/**
	 * Constructor
	 */
	public ProductQualitySubmodel(String id) {
		// Store unique product id
		this.setIdShort(id);
		
		// Add quality data property
		getProperties().put("qualityData", new Property(qualityData));
		
		// Add access operations for quality data
		// - Add a quality data entry
		getOperations().put("addQualityData", new Operation((Function<Object[], Object>) v -> {
			// Add quality data line to quality data list
			qualityData.add((String) v[0]);
			
			// Return no value
			return null;
		}));
	}
}

