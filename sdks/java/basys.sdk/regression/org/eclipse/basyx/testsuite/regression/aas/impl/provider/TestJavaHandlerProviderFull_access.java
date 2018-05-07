package org.eclipse.basyx.testsuite.regression.aas.impl.provider;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.ICollectionProperty;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.impl.provider.JavaHandlerProvider;
import org.eclipse.basyx.aas.impl.provider.javahandler.JavaHandler;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.aas.Stub1AAS;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel;
import org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel.Stub1Submodel.NestedPropertyType;
import org.junit.jupiter.api.Test;



/**
 * Test case for IModelProviders. It checks whether IModelProviders are able to perform basic VAB operations on AAS without
 * external references in their AAS or sub models. No serialization is evaluated.
 * 
 * @author kuhn
 *
 */
public class TestJavaHandlerProviderFull_access {


	/**
	 * Run test case
	 */
	@Test
	void test() {
		// Create model provider
		JavaHandlerProvider subModelProvider = new JavaHandlerProvider();
		// - Create AAS and sub model instances
		Stub1AAS      stub1AAS = new Stub1AAS();
		Stub1Submodel stub1SM  = new Stub1Submodel(stub1AAS);
		
		JavaHandler<Stub1AAS> aasHandler = new JavaHandler<Stub1AAS>(stub1AAS);
		aasHandler.addProperty("statusSM", null, null, null, null);

		JavaHandler<Stub1Submodel> smHandler = new JavaHandler<Stub1Submodel>(stub1SM);
		smHandler.addProperty("sampleProperty1",                  (obj) -> {return obj.sampleProperty1;},                  (obj, val) -> {obj.sampleProperty1=(int) val;},                                  null,   null);
		smHandler.addProperty("sampleProperty2",                  (obj) -> {return obj.sampleProperty2;},                  (obj, val) -> {obj.sampleProperty2=(int) val;},                                  null,   null);
		smHandler.addProperty("sampleProperty3",                  (obj) -> {return obj.sampleProperty3;},                  (obj, val) -> {obj.sampleProperty3=(NestedPropertyType) val;},                   null,   null);
		smHandler.addProperty("sampleProperty3/samplePropertyA",  (obj) -> {return obj.sampleProperty3.samplePropertyA;},  (obj, val) -> {obj.sampleProperty3.samplePropertyA=(int) val;},                  null,   null);
		smHandler.addProperty("sampleProperty3/samplePropertyB",  (obj) -> {return obj.sampleProperty3.samplePropertyB;},  (obj, val) -> {obj.sampleProperty3.samplePropertyB=(int) val;},                  null,   null);
		smHandler.addProperty("sampleProperty3/samplePropertyC",  (obj) -> {return obj.sampleProperty3.samplePropertyC;},  (obj, val) -> {obj.sampleProperty3.samplePropertyC=(NestedPropertyType) val;},   null,   null);
		smHandler.addProperty("sampleProperty4",                  (obj) -> {return obj.sampleProperty4;},                  null,                                                                            (obj, val) -> {obj.sampleProperty4.add((int) val);},                       (obj, val) -> {obj.sampleProperty4.remove((int) val);});
		smHandler.addProperty("sampleProperty5",                  (obj) -> {return obj.sampleProperty5;},                  null,                                                                            (obj, val) -> {obj.sampleProperty5.add((IElement) val);},                  (obj, val) -> {obj.sampleProperty5.remove((IElement) val);});
		//smHandler.addProperty("sampleProperty6",                  (obj) -> {return obj.sampleProperty6;},                  null,                                                                            (obj, val) -> {obj.sampleProperty6.put((String) val[0], (int) val[1]);},   (obj, val) -> {obj.sampleProperty6.remove((String) val);});

		smHandler.addOperation("sum",                 (obj, val) -> {return obj.sum((int) val[0], (int) val[1]);});
		smHandler.addOperation("sampleProperty3/sub", (obj, val) -> {return obj.sampleProperty3.sub((int) val[0], (int) val[1]);});
		

		// - Add models to provider
		subModelProvider.addHandler(aasHandler);
		subModelProvider.addHandler(smHandler);

		// -----------------------------------------------------------------------------------------------------------------------------
		// TEST GET VALUE
		// -----------------------------------------------------------------------------------------------------------------------------

		// Get AAS sub model property values via AAS
		//Collection<String>        modelNames   =                             subModelProvider.getAllModels();
		IAssetAdministrationShell aasStub      = (IAssetAdministrationShell) subModelProvider.getModelPropertyValue("Stub1AAS");
		ISubModel                 subModelStub = (ISubModel)                 subModelProvider.getModelPropertyValue("statusSM.Stub1AAS");
		Object                    property1N   =                             subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty1");
		Object                    property1I   =                             subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/0");
		Object                    property2N   =                             subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty2");
		Object                    property2I   =                             subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/1");
		Object                    property3N   =                             subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty3");
		Object                    property3I   =                             subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/2");
		Object                    property3AN  =                             subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty3/samplePropertyA");
		Object                    property3AI  =                             subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/3");
		Object                    property3BN  =                             subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty3/samplePropertyB");
		Object                    property3BI  =                             subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/4");

		// - Check results
		assertTrue(aasStub==stub1AAS);
		assertTrue(subModelStub==stub1SM);
		assertTrue((int) property1N==2);
		assertTrue((int) property1I==2);
		assertTrue((int) property2N==3);
		assertTrue((int) property2I==3);
		assertTrue(property3N instanceof Stub1Submodel.NestedPropertyType);
		assertTrue(property3I instanceof Stub1Submodel.NestedPropertyType);
		assertTrue((int) property3AN==4);
		assertTrue((int) property3AI==4);
		assertTrue((int) property3BN==5);
		assertTrue((int) property3BI==5);
		
		
		/*
		// - Check results
		assertTrue(modelNames.size()==2);
		assertTrue(modelNames.contains("statusSM"));
		assertTrue(modelNames.contains("Stub1AAS"));
		*/
		
		
		// Get AAS sub model property values via unique sub model ID
		ISubModel                 subMode2Stub = (ISubModel)                 subModelProvider.getModelPropertyValue("statusSM");
		Object                    property1aN  =                             subModelProvider.getModelPropertyValue("statusSM/sampleProperty1");
		Object                    property1aI  =                             subModelProvider.getModelPropertyValue("statusSM/0");
		Object                    property2aN  =                             subModelProvider.getModelPropertyValue("statusSM/sampleProperty2");
		Object                    property2aI  =                             subModelProvider.getModelPropertyValue("statusSM/1");

		// - Check results
		assertTrue(subMode2Stub==stub1SM);
		assertTrue((int) property1aN==2);
		assertTrue((int) property1aI==2);
		assertTrue((int) property2aN==3);
		assertTrue((int) property2aI==3);
		
		// -----------------------------------------------------------------------------------------------------------------------------
		// TEST SET VALUE
		// -----------------------------------------------------------------------------------------------------------------------------
		
		// Set AAS submodel property values
		subModelProvider.setModelPropertyValue("statusSM.Stub1AAS/sampleProperty1", 5);
		
		// Test if property value has been set
		property1N = subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty1");
		assertTrue((int) property1N==5);
		
		// Set AAS submodel property values over unique ID
		subModelProvider.setModelPropertyValue("statusSM.Stub1AAS/0", 2);
		
		// Test if property value has been set
		property1N = subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/0");
		assertTrue((int) property1N==2);
		
		// -----------------------------------------------------------------------------------------------------------------------------
		// TEST CREATE VALUE
		// -----------------------------------------------------------------------------------------------------------------------------
				
		// Add collection property value
		subModelProvider.createValue("statusSM.Stub1AAS/sampleProperty4", 92);
		
		// Test value has been added
		Collection<Integer> property4 = (Collection<Integer>) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty4");
		assertTrue(property4.contains(92));
		
		// -----------------------------------------------------------------------------------------------------------------------------
		// TEST DELETE VALUE
		// -----------------------------------------------------------------------------------------------------------------------------
		
		// Delete value from collection
		subModelProvider.deleteValue("statusSM.Stub1AAS/sampleProperty4", 92);
		
		// Test value has been added
		property4 = (Collection<Integer>) subModelProvider.getModelPropertyValue("statusSM.Stub1AAS/sampleProperty4");
		assertFalse(property4.contains(92));
		
		// -----------------------------------------------------------------------------------------------------------------------------
		// TEST INVOKE OPERATION
		// -----------------------------------------------------------------------------------------------------------------------------
		
		// Invoke operation
		Integer result = (Integer) subModelProvider.invokeOperation("statusSM.Stub1AAS/sum", new Integer[] {2,3});
		
		// Test if result is five
		assertTrue(result == 5);
		
		// -----------------------------------------------------------------------------------------------------------------------------
		// TEST GET CONTAINED ELEMENTS
		// -----------------------------------------------------------------------------------------------------------------------------		
		
		// Get element references from object provider
		Map<String, IElementReference> stub1AASModels = subModelProvider.getContainedElements("Stub1AAS"); 
		Map<String, IElementReference> submodelElements = subModelProvider.getContainedElements("statusSM.Stub1AAS");
				
		// Print contained elements
		for (Entry<String, IElementReference> entry : stub1AASModels.entrySet()) {System.out.println(entry.getKey() + " -> "+ entry.getValue());}
		for (Entry<String, IElementReference> entry : submodelElements.entrySet()) {System.out.println(entry.getKey() + " -> "+ entry.getValue());}
	}
}


