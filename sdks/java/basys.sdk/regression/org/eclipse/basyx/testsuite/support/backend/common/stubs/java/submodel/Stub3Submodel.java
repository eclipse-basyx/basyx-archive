package org.eclipse.basyx.testsuite.support.backend.common.stubs.java.submodel;

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.impl.provider.javahandler.genericsm.GenericHandlerOperation;
import org.eclipse.basyx.aas.impl.provider.javahandler.genericsm.GenericHandlerSubmodel;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.ParameterType;


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
		addProperty("testProp1", DataType.INTEGER, new Integer(1));
		addProperty("testProp2", DataType.INTEGER, new Integer(2));
		addOperation("testOp1", new ArrayList<ParameterType>(Arrays.asList(new ParameterType(0, DataType.INTEGER, "par0"), new ParameterType(0, DataType.INTEGER, "par1"))), DataType.INTEGER, new GenericHandlerOperation() {

			@Override
			public Object apply(ISubModel instance, Object[] parameter) {
				// Implement operation behavior
				return ((int) parameter[0]) * ((int) parameter[1]);
			}
	
		});
	}
}

