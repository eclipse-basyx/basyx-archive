package org.eclipse.basyx.models.manufacturing.process.model;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.junit.Test;



/**
 * Implement a test suite for the sequential process model
 * 
 * @author kuhn
 *
 */
public class TestSequentialProcessModel {

	
	
	/**
	 * Output device status to console
	 */
	protected void outputDeviceState(BaSysProcessModel process, int processInstance) {
		// Get product on first device (expected: null)
		Collection<ModelUrn> productIds = process.getCurrentProductsOnDevice(new ModelUrn("dev-000"));
		System.out.println("ProductID - "+processInstance+" | dev-000::"+productIds.size());
		try {System.out.println("ProductID - "+processInstance+" | dev-000::"+productIds.iterator().next());} catch (NoSuchElementException e) {}

		// Get product on second device
		productIds = process.getCurrentProductsOnDevice(new ModelUrn("dev-001"));
		System.out.println("ProductID - "+processInstance+" | dev-001::"+productIds.size());
		try {System.out.println("ProductID - "+processInstance+" | dev-001::"+productIds.iterator().next());} catch (NoSuchElementException e) {}
		
		// Get product on first device
		productIds = process.getCurrentProductsOnDevice(new ModelUrn("dev-002"));
		System.out.println("ProductID - "+processInstance+" | dev-002::"+productIds.size());
		try {System.out.println("ProductID - "+processInstance+" | dev-002::"+productIds.iterator().next());} catch (NoSuchElementException e) {}

		// Get product on first device
		productIds = process.getCurrentProductsOnDevice(new ModelUrn("dev-003"));
		System.out.println("ProductID - "+processInstance+" | dev-003::"+productIds.size());
		try {System.out.println("ProductID - "+processInstance+" | dev-003::"+productIds.iterator().next());} catch (NoSuchElementException e) {}
	}
	
	
	/**
	 * Run the manufacturing process
	 */
	protected void runProcess(BaSysProcessModel process, int processInstance) {
		// Store product IDs
		Collection<ModelUrn> productIds = null;

		// Start manufacturing on first device
		process.startProcessStepOnDevice(new ModelUrn("dev-000"));
		// - Start manufacturing on other devices
		process.startProcessStepOnDevice(new ModelUrn("dev-003"));
		process.startProcessStepOnDevice(new ModelUrn("dev-001"));
		process.startProcessStepOnDevice(new ModelUrn("dev-002"));
		
		// Output state of all devices
		outputDeviceState(process, processInstance);
		
		// End first manufacturing step
		process.finishProcessStepOnDevice(new ModelUrn("dev-002"));
		process.finishProcessStepOnDevice(new ModelUrn("dev-000"));
		process.finishProcessStepOnDevice(new ModelUrn("dev-001"));
		process.finishProcessStepOnDevice(new ModelUrn("dev-003"));

		// Get product on first device (expected: )
		productIds = process.getCurrentProductsOnDevice(new ModelUrn("dev-000"));
		System.out.println("ProductID - "+(processInstance+1)+" | dev-000::"+productIds.size());
	}
	
	
	
	/**
	 * Run test case
	 */
	@Test
	public void runTest() {
		// Create and setup process model
		SequentialProcess process = new SequentialProcess(new ModelUrn("proc-000"));
		// - Setup process model with 4 devices
		process.addDeviceToProcess(new ModelUrn("dev-000"));
		process.addDeviceToProcess(new ModelUrn("dev-001"));
		process.addDeviceToProcess(new ModelUrn("dev-002"));
		process.addDeviceToProcess(new ModelUrn("dev-003"));

		// Check waiting products
		System.out.println("Waiting products: "+process.getWaitingProducts().size());

		// Add waiting products to process
		process.addProductToManufacturingQueue(new ModelUrn("prod-000"));
		process.addProductToManufacturingQueue(new ModelUrn("prod-001"));
		process.addProductToManufacturingQueue(new ModelUrn("prod-002"));
		process.addProductToManufacturingQueue(new ModelUrn("prod-003"));
		process.addProductToManufacturingQueue(new ModelUrn("prod-004"));
		
		// Check waiting products
		System.out.println("Waiting products: "+process.getWaitingProducts().size());

		// Output device state
		outputDeviceState(process, 0);

		// Run manufacturing process
		for (int i=1; i<=19; i+=2) runProcess(process, i);

		// Check waiting products
		System.out.println("Waiting products: "+process.getWaitingProducts().size());
		
		// Check finished products
		System.out.println("Finished products: "+process.getFinishedProducts().size());
	}
}
