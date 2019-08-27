package org.eclipse.basyx.components.devicemanager;

import org.eclipse.basyx.aas.api.modelurn.ModelUrn;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.identifier.IdentifierType;
import org.eclipse.basyx.components.service.BaseBaSyxService;
import org.eclipse.basyx.vab.core.tools.VABPathTools;



/**
 * Base class for device managers
 * 
 * Device managers assume HTTP connection to BaSys infrastructure
 * 
 * Device managers manage devices that are not BaSys conforming by themselves. They:
 * - Register devices with the registry
 * - Receive data from devices and update sub models if necessary
 * - Receive change requests from sub models and update devices
 * 
 * @author kuhn
 *
 */
public abstract class DeviceManagerComponent extends BaseBaSyxService {	
	
	
	/**
	 * Store VAB object ID of default AAS server
	 */
	protected String aasServerObjID = null;
	
	
	/**
	 * Store HTTP URL of AAS server
	 */
	protected String aasServerURL = null;
	
	
	/**
	 * Path prefix on AAS server
	 */
	protected String aasServerPrefix = null;




	/**
	 * Constructor
	 */
	public DeviceManagerComponent() {
		// Do nothing
	}
	
	
	
	
	/**
	 * Set AAS server VAB object ID
	 */
	protected void setAASServerObjectID(String objID) {
		aasServerObjID = objID;
	}
	
	
	/**
	 * Get AAS server VAB object ID
	 */
	protected String getAASServerObjectID() {
		return aasServerObjID;
	}

	
	/**
	 * Set AAS server URL
	 */
	protected void setAASServerURL(String srvUrl) {
		aasServerURL = srvUrl;
	}
	
	
	/**
	 * Get AAS server URL
	 */
	protected String getAASServerURL() {
		return aasServerURL;
	}

	
	/**
	 * Set AAS server URL
	 */
	protected void setAASServerPathPrefix(String srvPathPfx) {
		aasServerPrefix = srvPathPfx;
	}
	
	
	/**
	 * Get AAS server URL
	 */
	protected String getAASServerPathPrefix() {
		return aasServerPrefix;
	}

	
	
	/**
	 * Get AAS descriptor for managed device
	 */
	protected abstract AASDescriptor getAASDescriptor();
	

	
	
	/**
	 * Create an AAS descriptor for the AAS with given URI. 
	 * 
	 * @param aasURN URN of asset administration shell that will be described by descriptor
	 * 
	 * @return AAS descriptor endpoint points to default AAS server location and contains default prefix path
	 */
	protected AASDescriptor createAASDescriptorURI(ModelUrn aasURN) {
		// Create and return AAS descriptor
		return new AASDescriptor(aasURN.getURN(), IdentifierType.URI, VABPathTools.concatenatePaths(getAASServerURL(), getAASServerPathPrefix(), aasURN.getEncodedURN()));
	}

	
	/**
	 * Create an AAS descriptor for the AAS with given URI and path
	 * 
	 * @param aasURN URN of asset administration shell that will be described by descriptor
	 * @param path   Path that the AAS descriptor endpoint should point to
	 * 
	 * @return AAS descriptor endpoint points to given path
	 */
	protected AASDescriptor createAASDescriptorURI(ModelUrn aasURN, String path) {
		// Create and return AAS descriptor
		return new AASDescriptor(aasURN.getURN(), IdentifierType.URI, path);
	}

	
	/**
	 * Add sub model descriptor to AAS descriptor
	 * 
	 * @param aasDescriptor AAS descriptor of AAS that sub model belongs to
	 * @param subModelURN   URN of sub model that will be described by descriptor
	 * 
	 * @return Sub model descriptor endpoint points to default AAS server location and contains default prefix path
	 */
	protected SubmodelDescriptor addSubModelDescriptorURI(AASDescriptor aasDescriptor, ModelUrn subModelURN) {
		// Create sub model descriptor
		SubmodelDescriptor submodelDescriptor = new SubmodelDescriptor(subModelURN.getURN(), IdentifierType.URI, VABPathTools.concatenatePaths(getAASServerURL(), getAASServerPathPrefix(), subModelURN.getEncodedURN()));
		
		// Add sub model descriptor to AAS descriptor
		aasDescriptor.addSubmodelDescriptor(submodelDescriptor);

		// Return sub model descriptor
		return submodelDescriptor;
	}


	/**
	 * Add sub model descriptor to AAS descriptor
	 * 
	 * @param aasDescriptor AAS descriptor of AAS that sub model belongs to
	 * @param subModelURN   URN of sub model that will be described by descriptor
	 * @param path          Path that the sub model descriptor endpoint should point to
	 * 
	 * @return Sub model descriptor endpoint points to default AAS server location and contains default prefix path
	 */
	protected SubmodelDescriptor addSubModelDescriptorURI(AASDescriptor aasDescriptor, ModelUrn subModelURN, String path) {
		// Create sub model descriptor
		SubmodelDescriptor submodelDescriptor = new SubmodelDescriptor(subModelURN.getURN(), IdentifierType.URI, path);
		
		// Add sub model descriptor to AAS descriptor
		aasDescriptor.addSubmodelDescriptor(submodelDescriptor);

		// Return sub model descriptor
		return submodelDescriptor;
	}
}

