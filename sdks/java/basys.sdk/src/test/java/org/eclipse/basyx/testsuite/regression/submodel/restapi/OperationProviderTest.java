/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.basyx.testsuite.regression.submodel.restapi;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.haskind.ModelingKind;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperationVariable;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.OperationVariable;
import org.eclipse.basyx.submodel.restapi.OperationProvider;
import org.eclipse.basyx.submodel.restapi.operation.InvocationRequest;
import org.eclipse.basyx.submodel.restapi.operation.InvocationResponse;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests for the OperationProvider
 * 
 * @author conradi
 *
 */
public class OperationProviderTest {

	private static final String OPID_IN = "opIn";
	private static final String OPID_OUT = "opOut";
	
	private static OperationProvider opProviderIn;
	private static OperationProvider opProviderOut;
	
	@BeforeClass
	public static void setup() {
		
		Collection<OperationVariable> in = new ArrayList<>();
		Collection<OperationVariable> out = new ArrayList<>();
		
		Property inProp1 = new Property("testIn", 0);
		inProp1.setModelingKind(ModelingKind.TEMPLATE);
		Property outProp = new Property("testOut", 0);
		outProp.setModelingKind(ModelingKind.TEMPLATE);
		
		in.add(new OperationVariable(inProp1));
		out.add(new OperationVariable(outProp));
		
		Operation inOperation = new Operation(OPID_IN);
		inOperation.setInputVariables(in);
		
		inOperation.setInvokable((Function<Object[], Object>) v -> {
				// Do nothing, just return
				// This is a function with return type "void" 
				return null;
		});
		
		opProviderIn = new OperationProvider(new VABLambdaProvider(inOperation));
		
		Operation outOperation = new Operation(OPID_OUT);
		outOperation.setOutputVariables(out);
		
		outOperation.setInvokable((Function<Object[], Object>) v -> {
			return v[0];
		});
		
		opProviderOut = new OperationProvider(new VABLambdaProvider(outOperation));
		
	}
	
	/**
	 * Tests an Operation call with non wrapped parameters
	 * Operation has no return value
	 */
	@Test
	public void testNonWrappedInputWithoutOutput() {
		opProviderIn.invokeOperation("invoke", 1);
	}
	
	/**
	 * Tests an Operation call with an InvocationRequest
	 * Operation has no return value
	 */
	@Test
	public void testInvocationRequestInputWithoutOutput() {	
		InvocationRequest request = getInvocationRequest();
		opProviderIn.invokeOperation("invoke", request);
	}
	
	/**
	 * Tests an Operation call with non wrapped parameters
	 * Operation returns the given parameter
	 */
	@Test
	public void testNonWrappedInputWithOutput() {
		assertEquals(5, opProviderOut.invokeOperation("invoke", 5));
	}
	
	/**
	 * Tests an Operation call with an InvocationRequest
	 * Operation returns the given parameter
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testInvocationRequestInputWithOutput() {

		InvocationRequest request = getInvocationRequest();
		
		InvocationResponse response = InvocationResponse.createAsFacade((Map<String, Object>) opProviderOut.invokeOperation("invoke", request));
		Collection<IOperationVariable> outResponse = response.getOutputArguments();
		assertEquals(1, outResponse.size());
		
		Property prop = (Property) outResponse.iterator().next().getValue();
		
		assertEquals(5, prop.getValue());
	}
	
	private InvocationRequest getInvocationRequest() {
		Collection<IOperationVariable> in = new ArrayList<>();
		Collection<IOperationVariable> inout = new ArrayList<>();
		
		Property inProp = new Property("testIn", 5);
		in.add(new OperationVariable(inProp));
		
		return new InvocationRequest("1", inout, in, 100);
	}
}
