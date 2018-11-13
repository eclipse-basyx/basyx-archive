package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.api.resources.ParameterType;
import org.eclipse.basyx.aas.impl.provider.javahandler.genericsm.GenericHandlerOperation;
import org.eclipse.basyx.aas.impl.provider.javahandler.genericsm.GenericHandlerSubmodel;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;


/**
 * Test implementation of generic handler sub model
 * 
 * @author kuhn
 *
 */
public class Stub3Submodel extends GenericHandlerSubmodel {

	
	/**
	 * Constructor
	 */
	public Stub3Submodel() {
		// Call base constructor
		super(AssetKind.INSTANCE, "Stub3SM", "Stub3SM", "tests.generichandler.stub3submodel", "Stub3AAS", "Stub3AAS");
		
		
		// Add properties and operations
		addProperty("testProp1", DataObjectType.Int32, new Integer(1));
		addProperty("testProp2", DataObjectType.Int32, new Integer(2));
		addOperation("testOp1", new ArrayList<ParameterType>(Arrays.asList(new ParameterType(0, DataObjectType.Int32, "par0"), new ParameterType(0, DataObjectType.Int32, "par1"))), DataObjectType.Int32, new GenericHandlerOperation() {

			@Override
			public Object apply(ISubModel instance, Object[] parameter) {
				// Implement operation behavior
				return ((int) parameter[0]) * ((int) parameter[1]);
			}
	
		});
	}
}

