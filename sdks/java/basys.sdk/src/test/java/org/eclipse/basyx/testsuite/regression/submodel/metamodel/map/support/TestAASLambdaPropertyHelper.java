package org.eclipse.basyx.testsuite.regression.submodel.metamodel.map.support;

import static org.junit.Assert.assertEquals;

import org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement.ConnectedProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.AASLambdaPropertyHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.junit.Test;

/**
 * Ensures correct behaviour of {@link AASLambdaPropertyHelper}
 * 
 * @author schnicke
 *
 */
public class TestAASLambdaPropertyHelper {

	// Used as data container for lambda property
	private double testValue;

	@Test
	public void testSettingLambdaValue() throws Exception {
		// Build property
		Property temperature = new Property();
		AASLambdaPropertyHelper.setLambdaValue(temperature, () -> testValue, v -> {
			testValue = (double) v;
		});
		
		// Wrap in provider
		SubmodelElementProvider provider = new SubmodelElementProvider(new VABLambdaProvider(temperature));
		ConnectedProperty connectedProperty = new ConnectedProperty(new VABElementProxy("", provider));

		// Check correct property type
		PropertyValueTypeDef expectedType = PropertyValueTypeDef.Double;
		assertEquals(expectedType, connectedProperty.getValueType());
		
		// Check value is correctly retrievable by property
		testValue = 10;
		assertEquals(testValue, connectedProperty.get());
		
		// Check value is correctly written by property
		double expectedValue = 2.1;
		connectedProperty.set(expectedValue);
		assertEquals(expectedValue, connectedProperty.get());
		assertEquals(expectedValue, testValue, 0);
	}
}
