package org.eclipse.basyx.aas.impl.reference;

import org.eclipse.basyx.aas.api.exception.UnknownElementTypeException;
import org.eclipse.basyx.aas.api.reference.IElementReference;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.impl.tools.BaSysID;
import org.eclipse.basyx.aas.impl.tools.ElementTools;
import org.eclipse.basyx.vab.core.ref.VABElementRef;

/**
 * URN reference to a remote property. This reference also contains RTTI
 * information
 * 
 * The element reference format is as following: -
 * urn:<legalBody>:<SubUnit>:<Submodel>:<version>:<revision>:<elementID>#<instance>
 * 
 * @author kuhn
 *
 */
public class VABURNElementRef extends VABElementRef implements IElementReference {

	/**
	 * Store legal body part of reference
	 */
	protected String legalBody = null;

	/**
	 * Store sub unit part of reference
	 */
	protected String subUnit = null;

	/**
	 * Store sub model part of reference
	 */
	protected String subModel = null;

	/**
	 * Store version part of reference
	 */
	protected String version = null;

	/**
	 * Store revision part of reference
	 */
	protected String revision = null;

	/**
	 * Store element path part of reference. This path is the logic path to the
	 * property that a user will use.
	 */
	protected String elementPath = null;

	/**
	 * Store instance information
	 */
	protected String instance = null;

	protected boolean isCollection = false;

	protected boolean isMap = false;

	protected boolean isPropertyContainer = false;

	/**
	 * Default constructor
	 */
	public VABURNElementRef() {
		// Do nothing
	}

	/**
	 * Constructor that creates a reference to an IElement
	 */
	public VABURNElementRef(IElement element) {
		// Serialize element reference
		// - AAS
		if (element instanceof IAssetAdministrationShell) {
			legalBody = element.getId();
		}
		// - Sub model
		else if (element instanceof ISubModel) {
			legalBody = ElementTools.getAASID(element);
			subUnit = element.getId();
			// }
			// // - Sub model property, event or operation
			// // - FIXME: Interface types
			// else if (element instanceof IProperty) {
			// legalBody = ElementTools.getAASID(element);
			// subUnit = ElementTools.getSubmodelID(element);
			// elementPath = ElementTools.getFullPathToProperty(element);
			// } else if (element instanceof _Operation) {
			// legalBody = ElementTools.getAASID(element);
			// subUnit = ElementTools.getSubmodelID(element);
			// elementPath = ElementTools.getFullPathToProperty(element);
			// } else if (element instanceof _Event) {
			// legalBody = ElementTools.getAASID(element);
			// subUnit = ElementTools.getSubmodelID(element);
			// elementPath = ElementTools.getFullPathToProperty(element);
		} else {
			// Element type is not known...
			throw new UnknownElementTypeException();
		}

		// Set server property path
		if (getPath() == null) {
			setPath(elementPath);
		}
	}

	/**
	 * Constructor that references an AAS
	 */
	public VABURNElementRef(String AASID) {
		// Store IDs
		legalBody = AASID;
	}

	/**
	 * Constructor that references a sub model in an AAS
	 */
	public VABURNElementRef(String AASID, String SubModelID) {
		// Store IDs
		legalBody = AASID;
		subUnit = SubModelID;
	}

	/**
	 * Constructor that references a sub model property in an AAS
	 */
	public VABURNElementRef(String AASID, String SubModelID, String propPath) {
		// Store IDs
		legalBody = AASID;
		subUnit = SubModelID;
		elementPath = propPath;
		setPath(elementPath);
	}

	/**
	 * Get AAS ID
	 */
	@Override
	public String getAASID() {
		// Return AAS IS
		return legalBody;
	}

	/**
	 * Get sub model ID
	 */
	@Override
	public String getSubModelID() {
		// Return sub model ID
		return subUnit;
	}

	/**
	 * Get path to property
	 */
	@Override
	public String getPathToProperty() {
		// Return path to property
		return elementPath;
	}

	/**
	 * Get server path to property that should be used when accessing the property
	 */
	@Override
	public String getServerPathToProperty() {
		return getPath();
	}

	/**
	 * Indicate if reference points to an AAS
	 */
	@Override
	public boolean isAASReference() {
		// An AAS reference only defines the unique AAS ID
		if ((legalBody != null) && ((subUnit == null) || (subUnit.length() == 0))
				&& ((elementPath == null) || (elementPath.length() == 0)))
			return true;

		// No AAS reference
		return false;
	}

	/**
	 * Indicate if reference points to an AAS sub model
	 */
	@Override
	public boolean isSubModelReference() {
		// A sub model reference only defines the unique sub model ID and optionally the
		// AAS ID
		if ((subUnit != null) && ((elementPath == null) || (elementPath.length() == 0)))
			return true;

		// No sub model reference
		return false;
	}

	/**
	 * Indicate if reference points to a property of an AAS sub model
	 */
	@Override
	public boolean isPropertyReference() {
		// A property reference defines the unique sub model ID, optionally the AAS ID,
		// and the property path
		if ((subUnit != null) && (elementPath != null))
			return true;

		// No sub model reference
		return false;
	}

	/**
	 * Return the unique ID that identifies an element
	 * 
	 * @return unique ID
	 */
	@Override
	public String getId() {
		// If sub model is null or empty, ID as AAS ID
		if ((subUnit == null) || (subUnit.length() == 0))
			return legalBody;

		// If path is null or empty, ID is sub model ID
		if ((elementPath == null) || (elementPath.length() == 0))
			return subUnit;

		// ID is in the property path
		// check if it is a nested property
		if (elementPath.contains(".")) {
			String[] splitted = elementPath.split("\\.");
			return splitted[splitted.length - 1];
		} else {
			return this.elementPath;
		}
	}

	/**
	 * Add scope to aasID
	 */
	@Override
	public void addScope(String scope) {
		// Only process valid scopes
		if (scope == null)
			return;

		// When aasID is null or empty set aasID to scope and return
		if (legalBody == null) {
			legalBody = scope;
			return;
		}
		if (legalBody.length() == 0) {
			legalBody = scope;
			return;
		}

		// Check if scope is already added
		if (legalBody.endsWith(scope))
			return;

		// Add scope
		this.legalBody = BaSysID.instance.addScope(legalBody, scope);
	}

	/**
	 * Set server path
	 */
	@Override
	public void setServerpath(String serverPath) {
		setPath(serverPath);
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
	 * Set element type for collections and maps
	 */
	public void setKind(String kind) {
		System.out.println("Set kind for property " + this.getId() + " to " + kind);

		if (kind.equals("collection")) {
			this.isCollection = true;
		} else if (kind.equals("map")) {
			this.isMap = true;
		} else if (kind.equals("container")) {
			this.isPropertyContainer = true;
		}
	}

	@Override
	public boolean isPropertyContainer() {
		return this.isPropertyContainer;
	}
}
