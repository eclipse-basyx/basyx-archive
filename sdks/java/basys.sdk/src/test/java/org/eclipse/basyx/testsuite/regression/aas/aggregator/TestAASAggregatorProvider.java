package org.eclipse.basyx.testsuite.regression.aas.aggregator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.aggregator.api.IAASAggregator;
import org.eclipse.basyx.aas.aggregator.proxy.AASAggregatorProxy;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.junit.Test;

/**
 * Test for the AASAggregationProvider
 * 
 * @author conradi, schnicke
 *
 */
public class TestAASAggregatorProvider extends AASAggregatorSuite {

	@Override
	protected IAASAggregator getAggregator() {
		return new AASAggregatorProxy(new AASAggregatorProvider(new AASAggregator()));
	}

	/**
	 * Requests like /aasList/${aasId}/aas need to be fed through correctly. This
	 * behaviour is tested here.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testFeedThrough() {
		AASAggregator aggregator = new AASAggregator();
		aggregator.createAAS(aas1);
		AASAggregatorProvider provider = new AASAggregatorProvider(aggregator);

		// Test feedthrough of GET
		String aasPath = "/aasList/" + VABPathTools.encodePathElement(aas1.getIdentification().getId()) + "/aas";
		AssetAdministrationShell retrievedAAS = retrieveAAS(provider, aasPath);
		assertEquals(aas1.getIdentification(), retrievedAAS.getIdentification());

		// Test feedthrough of CREATE
		SubModel sm = new SubModel();
		sm.setIdentification(IdentifierType.CUSTOM, "smId");
		sm.setIdShort("smIdShort");
		Operation op = new Operation((o) -> {
			return true;
		});
		op.setIdShort("op");
		sm.addSubModelElement(op);

		Property prop = new Property(5);
		prop.setIdShort("prop");
		sm.addSubModelElement(prop);

		provider.createValue(aasPath + "/submodels", sm);

		// Check if it was created
		String smPath = aasPath + "/submodels/smIdShort";
		SubModel retrievedSm = SubModel.createAsFacade((Map<String, Object>) provider.getModelPropertyValue(smPath));
		assertEquals(sm.getIdShort(), retrievedSm.getIdShort());

		// Test feedthrough of SET
		String propValuePath = VABPathTools.concatenatePaths(smPath, SubmodelElementProvider.PROPERTIES, prop.getIdShort(), "value");
		int expectedPropValue = 20;
		provider.setModelPropertyValue(propValuePath, expectedPropValue);

		Map<String, Object> value = (Map<String, Object>) provider.getModelPropertyValue(propValuePath);
		assertEquals(expectedPropValue, value.get(Property.VALUE));

		// Test feedthrough of INVOKE
		assertTrue((boolean) provider.invokeOperation(smPath + "/operations/op"));
		
		// Test feedthrough of DELETE
		provider.deleteValue(smPath);

		// Ensure only the submodel has been deleted
		retrievedAAS = retrieveAAS(provider, aasPath);
		assertEquals(aas1.getIdentification(), retrievedAAS.getIdentification());

		try {
			provider.getModelPropertyValue(smPath);
			fail();
		} catch (ResourceNotFoundException e) {
			// Expected
		}

	}

	/**
	 * Retrieves the AAS given residing in the passed path on the passed provider
	 * 
	 * @param provider
	 * @param aasPath
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private AssetAdministrationShell retrieveAAS(AASAggregatorProvider provider, String aasPath) {
		return AssetAdministrationShell.createAsFacade((Map<String, Object>) provider.getModelPropertyValue(aasPath));
	}
}
