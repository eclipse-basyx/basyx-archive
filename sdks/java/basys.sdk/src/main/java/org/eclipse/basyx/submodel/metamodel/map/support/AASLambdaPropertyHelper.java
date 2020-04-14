package org.eclipse.basyx.submodel.metamodel.map.support;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;

/**
 * Supports using Properties in combination with {@link VABLambdaProvider}
 * 
 * @author schnicke
 *
 */
public class AASLambdaPropertyHelper {
	/**
	 * Sets the correct values in the passed property to use the lambda functions
	 * 
	 * @param property
	 * @param get
	 * @param set
	 * @return the passed property with updated configuration
	 */
	public static Property setLambdaValue(Property property, Supplier<Object> get, Consumer<Object> set) {
		property.set(VABLambdaProviderHelper.createSimple(get, set));
		property.setValueType(PropertyValueTypeDefHelper.getType(get.get()));
		return property;
	}
}
