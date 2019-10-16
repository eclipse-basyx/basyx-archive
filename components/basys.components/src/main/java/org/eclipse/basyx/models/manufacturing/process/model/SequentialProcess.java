package org.eclipse.basyx.models.manufacturing.process.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.models.manufacturing.process.model.device.Device;
import org.eclipse.basyx.models.manufacturing.process.model.device.QueueDevice;
import org.eclipse.basyx.models.manufacturing.process.model.device.SequentialDevice;




/**
 * Process status sub model that implements a sequential process model. The sequential process consists
 * of devices separated by queues. Every device processes at maximum one product at a time. The queue 
 * size is one, therefore, only one product may wait between devices. At the beginning and end of the 
 * process, a queue is installed.
 * 
 * This process remains unchanged after it has been started.
 * 
 * @author kuhn
 *
 */
public class SequentialProcess extends BaSysProcessModel {

	
	/**
	 * Version number of serialized instances
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	/**
	 * Input queue device
	 */
	protected QueueDevice inputQueueDevice = new QueueDevice(new ModelUrn("basyx-process-input"));
	
	
	/**
	 * Output queue device
	 */
	protected QueueDevice outputQueueDevice = new QueueDevice(new ModelUrn("basyx-process-output"));
	
	
	/**
	 * Last added process node in process
	 */
	protected SequentialProcessNode lastAddedProcessNode = null;
	
	
	/**
	 * Map that stores all devices
	 */
	protected Map<ModelUrn, SequentialProcessNode> urnToDevice = new HashMap<>();

	
	/**
	 * Process nodes for process input devices
	 */
	protected SequentialProcessNode firstProcessNode = new SequentialProcessNode(inputQueueDevice);

	
	/**
	 * Process nodes for process output devices
	 */
	protected SequentialProcessNode lastProcessNode  = new SequentialProcessNode(outputQueueDevice);

	
	
	
	
	
	/**
	 * Constructor
	 */
	public SequentialProcess(ModelUrn id) {
		// Invoke base constructor
		super(id);
		
		
		// Create process nodes for process input and output devices
		firstProcessNode = new SequentialProcessNode(inputQueueDevice);
		lastProcessNode  = new SequentialProcessNode(outputQueueDevice);
		
		// Add device nodes
		urnToDevice.put(firstProcessNode.getDevice().getDeviceID(), firstProcessNode);
		urnToDevice.put(lastProcessNode.getDevice().getDeviceID(),  lastProcessNode);
		
		// Initially connect input and output process nodes
		firstProcessNode.setNextNode(lastProcessNode);
		lastProcessNode.setPrevNode(firstProcessNode);
		
		
		/*
		// Element factory supports creating properties and operations
		MetaModelElementFactory fac = new MetaModelElementFactory();

		// Register operations
		// - Get queued products that are not in manufacturing
		getOperations().put("getQueuedProductIDs", fac.createOperation(new Operation(), (Function<Object[], Object>) (v) -> {
			return (int) v[0] - (int) v[1];
		}));
		// - Get products that are being processed
		// - Get finished products
		getOperations().put("getFinishedProductIDs", fac.createOperation(new Operation(), (Function<Object[], Object>) (v) -> {
			return (int) v[0] - (int) v[1];
		}));
		// - Add a device to end of process
		getOperations().put("addDeviceToProcess", fac.createOperation(new Operation(), (Function<Object[], Object>) (v) -> {
			// Add device to end of process
			addDeviceToProcess((String) v[0]);
			// - No result
			return null;
		}));
		// - Add device after given device
		// - Remove a device from process
		// - Finish a process step for a device
		getOperations().put("finishProcessStepForDevice", fac.createOperation(new Operation(), (Function<Object[], Object>) (v) -> {
			// - Finish process step for device
			finishProcessStepOnDevice((String) v[0]);
			// - No result
			return null;
		}));
		// - Get current product on device
		getOperations().put("getProductOnDevice", fac.createOperation(new Operation(), (Function<Object[], Object>) (v) -> {
			// Get current product o device
			return getCurrentProductOnDevice((String) v[0]);
		}));
		// - Add product to queue
		getOperations().put("addProductToQueue", fac.createOperation(new Operation(), (Function<Object[], Object>) (v) -> {
			// Add product ID to queue
			addProductToManufacturingQueue((String) v[0]);
			// - No result
			return null;
		}));
		// - Remove product from manufacturing queue
		getOperations().put("removeProductFromQueue", fac.createOperation(new Operation(), (Function<Object[], Object>) (v) -> {
			// Add product ID to queue
			removeProductFromManufacturingQueue((String) v[0]);
			// - No result
			return null;
		}));
		*/
	}


	
	/**
	 * Get ID of device before given device
	 */
	protected ModelUrn getPreviousDeviceID(ModelUrn devID) {
		// Get device process node
		SequentialProcessNode devNode = urnToDevice.get(devID);
		
		// Get previous device
		return devNode.getPrevNode().getDevice().getDeviceID();
	}

	
	/**
	 * Get ID of device after given device
	 */
	protected ModelUrn getNextDeviceID(ModelUrn devID) {
		// Get device process node
		SequentialProcessNode devNode = urnToDevice.get(devID);
		
		// Get previous device
		return devNode.getNextNode().getDevice().getDeviceID();
	}
	
	
	/**
	 * Start process step on given device
	 */
	@Override
	public void startProcessStepOnDevice(ModelUrn devID) {
		// Get previous device ID
		ModelUrn prevDeviceID = getPreviousDeviceID(devID);
		
		// Fetch product/work piece from previous buffer
		// - Previous device is buffer because we did create it as buffer
		SequentialProcessNode bufferNode = urnToDevice.get(prevDeviceID);
		QueueDevice           bufferDev  = (QueueDevice) bufferNode.getDevice();
		// - Fetch product/work piece from buffer
		ModelUrn workPieceID = bufferDev.fetchProduct();
		
		// Only add product to device if product id is not null
		if (workPieceID == null) return;
		
		// Add work piece to device
		urnToDevice.get(devID).getDevice().startProcessing(workPieceID);
	}
	
	
	/**
	 * Finish process step on given device
	 * 
	 * This implementation moves product forward to next device
	 */
	@Override
	public void finishProcessStepOnDevice(ModelUrn devID) {
		// Get next device ID
		ModelUrn nextDeviceID = getNextDeviceID(devID);
		
		// Take product from device
		SequentialProcessNode devNode     = urnToDevice.get(devID);
		ModelUrn              workPieceID = devNode.getDevice().endProcessing();

		// Only add product to device if product id is not null
		if (workPieceID == null) return;

		// Add work piece to buffer after device
		SequentialProcessNode bufferNode = urnToDevice.get(nextDeviceID);
		QueueDevice           bufferDev  = (QueueDevice) bufferNode.getDevice();
		bufferDev.pushProduct(workPieceID);
	}
	
	
	/**
	 * Get URN ID of products on device
	 */
	@Override
	public Collection<ModelUrn> getCurrentProductsOnDevice(ModelUrn devID) {
		// Get queue that represents device
		Device device = urnToDevice.get(devID).getDevice();
		
		// Return first element in queue, but do not remove it
		return device.getProductsOnDevice();
	}
	
	
	/**
	 * Add a product to the manufacturing queue
	 */
	@Override
	public void addProductToManufacturingQueue(ModelUrn productID) {
		// Add product ID to manufacturing queue
		inputQueueDevice.pushProduct(productID);
	}

	
	/**
	 * Remove a product from the manufacturing queue
	 */
	@Override
	public ModelUrn removeProductFromManufacturingQueue() {
		// Remove product from process
		return outputQueueDevice.fetchProduct();
	}
	
	
	/**
	 * Get finished products
	 */
	@Override
	public Collection<ModelUrn> getFinishedProducts() {
		return outputQueueDevice.getProductsOnDevice();
	}	
	
	
	/**
	 * Get waiting products (i.e. those products in the process that wait for being processed)
	 */
	@Override
	public Collection<ModelUrn> getWaitingProducts() {
		return inputQueueDevice.getProductsOnDevice();
	}

	
	/**
	 * Add a device to the process
	 */
	@Override
	public void addDeviceToProcess(ModelUrn deviceID) {
		// Create device twin in process
		Device manufacturingDevice   = new SequentialDevice(deviceID);
		// - Create process node for new devices
		SequentialProcessNode processNode = new SequentialProcessNode(manufacturingDevice);
		// - Add device mapping
		urnToDevice.put(deviceID, processNode);

		// Handle first device in a different way
		if (lastAddedProcessNode == null) {
			// Connect process nodes
			processNode.setPrevNode(firstProcessNode);
			processNode.setNextNode(lastProcessNode);
			firstProcessNode.setNextNode(processNode);
			lastProcessNode.setPrevNode(processNode);

			// Update last added process node to this device
			lastAddedProcessNode = processNode;

			// Done adding the device
			return;
		}
		
		// Add a device after last manufacturing device, also add buffer space between devices
		// - Create process node for buffer devices. Queue size 1 ensures that only one product at a time
		//   may wait between devices.
		SequentialProcessNode bufferProcessNode = new SequentialProcessNode(new QueueDevice(deviceID.append("-buffer"), 1));
		// - Add device mapping
		urnToDevice.put(deviceID.append("-buffer"), bufferProcessNode);

		// Connect process nodes
		bufferProcessNode.setPrevNode(lastAddedProcessNode);
		bufferProcessNode.setNextNode(processNode);
		processNode.setPrevNode(bufferProcessNode);
		processNode.setNextNode(lastProcessNode);
		lastAddedProcessNode.setNextNode(bufferProcessNode);
		lastProcessNode.setPrevNode(processNode);

		// Update last added process node to this device
		lastAddedProcessNode = processNode;
	}
}


