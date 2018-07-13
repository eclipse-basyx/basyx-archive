package org.eclipse.basyx.aas.impl.reference;

import org.eclipse.basyx.aas.api.exception.UnknownElementTypeException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.IProperty;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;
import org.eclipse.basyx.aas.impl.resources.basic.Event;
import org.eclipse.basyx.aas.impl.resources.basic.Operation;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.eclipse.basyx.aas.impl.tools.ElementTools;



/**
 * Reference to a remote property
 * 
 * @author kuhn
 *
 */
public class ElementRef implements IElementReference {

	
	/**
	 * Store AAS ID part of reference
	 */
	protected String aasID = null;

	/**
	 * Store sub model ID part of reference
	 */
	protected String subModelID = null;

	/**
	 * Store logic property path part of reference. This path is the logic path to the property that a user will use.
	 */
	protected String propertyPath = null;

	/**
	 * Store server property path. This path may contain optimized handles and is used internally by the SDK.
	 */
	protected String serverPropertyPath = null;
	
	/**
	 * Indicates if the referenced value is a collection
	 */
	protected boolean isCollection = false;
	
	/**
	 * Indicates if the referenced value is a map
	 */
	protected boolean isMap = false;
	
	/**
	 * Indicates read only
	 */
	protected boolean frozen = false;
	
	/**
	 * Default constructor
	 */
	public ElementRef() {
		// Do nothing
	}
	
	
	
	/**
	 * Constructor that creates a reference to an IElement
	 */
	public ElementRef(IElement element) {
		// Serialize element reference
		// - AAS
		if (element instanceof IAssetAdministrationShell) {aasID = element.getId();} 
		// - Sub model
		else if (element instanceof ISubModel) {aasID = ElementTools.getAASID(element); subModelID = element.getId();} 
		// - Sub model property, event or operation
		// - FIXME: Interface types
		else if (element instanceof IProperty) {aasID = ElementTools.getAASID(element); subModelID = ElementTools.getSubmodelID(element); propertyPath = ElementTools.getFullPathToProperty(element);}
		else if (element instanceof Operation) {aasID = ElementTools.getAASID(element); subModelID = ElementTools.getSubmodelID(element); propertyPath = ElementTools.getFullPathToProperty(element);}
		else if (element instanceof Event)     {aasID = ElementTools.getAASID(element); subModelID = ElementTools.getSubmodelID(element); propertyPath = ElementTools.getFullPathToProperty(element);}
		else {
			// Element type is not known...
			throw new UnknownElementTypeException();
		}
		
		// Set server property path
		if (serverPropertyPath == null) serverPropertyPath=propertyPath;
	}

	
	
	/**
	 * Constructor that references an AAS
	 */
	public ElementRef(String AASID) {
		// Store IDs
		aasID = AASID;
	}

	
	/**
	 * Constructor that references a sub model in an AAS
	 */
	public ElementRef(String AASID, String SubModelID) {
		// Store IDs
		aasID = AASID;
		subModelID = SubModelID;
	}

	
	/**
	 * Constructor that references a sub model property in an AAS
	 */
	public ElementRef(String AASID, String SubModelID, String propPath) {
		// Store IDs
		aasID              = AASID;
		subModelID         = SubModelID;
		propertyPath       = propPath;
		serverPropertyPath = propertyPath;
	}

	
	
	
	
	
	/**
	 * Get AAS ID
	 */
	@Override
	public String getAASID() {
		// Return AAS IS
		return aasID;
	}


	
	/**
	 * Get sub model ID
	 */
	@Override
	public String getSubModelID() {
		// Return sub model ID
		return subModelID;
	}

	
	
	/**
	 * Get path to property
	 */
	@Override
	public String getPathToProperty() {
		// Return path to property
		return propertyPath;
	}


	
	/**
	 * Get server path to property that should be used when accessing the property
	 */
	@Override
	public String getServerPathToProperty() {
		return serverPropertyPath;
	}



	/**
	 * Indicate if reference points to an AAS
	 */
	@Override
	public boolean isAASReference() {
		// An AAS reference only defines the unique AAS ID
		if ((aasID != null) && ((subModelID == null) || (subModelID.length() == 0)) && ((propertyPath == null) || (propertyPath.length() == 0))) return true;

		// No AAS reference
		return false;
	}



	/**
	 * Indicate if reference points to an AAS sub model
	 */
	@Override
	public boolean isSubModelReference() {
		// A sub model reference only defines the unique sub model ID and optionally the AAS ID
		if ((subModelID != null) && ((propertyPath == null) || (propertyPath.length() == 0))) return true;

		// No sub model reference
		return false;
	}



	/**
	 * Indicate if reference points to a property of an AAS sub model
	 */
	@Override
	public boolean isPropertyReference() {
		// A property reference defines the unique sub model ID, optionally the AAS ID, and the property path
		if ((subModelID != null) && (propertyPath != null)) return true;

		// No sub model reference
		return false;
	}
	
	
	/**
	 * Return the unique ID that identifies an element
	 * 
	 * @return unique ID
	 */
	public String getId() {
		// If sub model is null or empty, ID as AAS ID
		if ((subModelID == null) || (subModelID.length()==0)) return aasID;
		
		// If path is null or empty, ID is sub model ID
		if ((propertyPath == null) || (propertyPath.length()==0)) return subModelID;
		
		// ID is last part of path to property
		//return BaSysID.instance.getLastPathEntries(subModelID+"/"+propertyPath, 1)[0];
		
		// ID is in the property path
		return this.propertyPath;
	}
	
	
	/**
	 * Add scope to aasID FIXME how to deal with the scope
	 */
	public void addScope(String scope) {
		// Only process valid scopes
		if (scope == null) return;
		
		// When aasID is null or empty set aasID to scope and return
		if (aasID == null)       {aasID = scope; return;}
		if (aasID.length() == 0) {aasID = scope; return;}

		// Check if scope is already added
		if (aasID.endsWith(scope)) return;
		
		// Add scope
		aasID = aasID+"."+scope;
	}
	
	
	/**
	 * Set server path
	 */
	public void setServerpath(String serverPath) {
		serverPropertyPath = serverPath;
	}


	/**
	 * Indicate if it is a collection
	 */
	@Override
	public boolean isCollection() {
		return this.isCollection;
	}


	/**
	 * Indicate if it is a collection
	 */
	@Override
	public boolean isMap() {
		return this.isMap;
	}

	/**
	 * Indicate if the submodel is frozen
	 */
	@Override
	public boolean isFrozen() {
		return this.frozen;
	}
	
	/**
	 * Set frozen variable
	 */
	@Override
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	/**
	 * Set element type for collections and maps
	 */
	public void setKind(String kind) {
		System.out.println("Set kind for property " + this.getId() + " to " + kind);
		
		if (kind.equals("collection")) {
			this.isCollection = true;
		}
		
		else if (kind.equals("map")) {
			this.isMap =true;
		}
	}
}


