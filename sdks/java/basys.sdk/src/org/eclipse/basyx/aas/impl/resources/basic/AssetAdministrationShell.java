package org.eclipse.basyx.aas.impl.resources.basic;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.basic.IElement;
import org.eclipse.basyx.aas.api.resources.basic.ISubModel;



/**
 * Technology independent Asset Administration Shell (AAS) model implementation
 * 
 * @author schoeffler, ziesche
 *
 */
public class AssetAdministrationShell extends BaseElement implements IAssetAdministrationShell {
	
	
	/**
	 * Store contained sub models
	 */
	public Map<String, ISubModel> subModels = new HashMap<>();
	
	
	
	/**
	 * Store contained asset administration shells
	 * - FIXME: What is the use case here?
	 */
	protected Map<String, AssetAdministrationShell> assetAdministrationShells = new HashMap<>();
	
	
	/**
	 * Store asset type for the AAS
	 */
	protected String assetTypeDefinition;

	
	/**
	 * Store printable name of AAS
	 */
	protected String displayName;
	
	
	/**
	 * Store asset ID
	 */
	protected String assetId;
	
	
	
	
	
	/**
	 * Constructor
	 */
	public AssetAdministrationShell() {
		super();
	}
	

	/**
	 * Add a sub model to this AAS
	 */
	@Override
	public synchronized void addSubModel(ISubModel subModel) {
		// - FIXME: Muss hier nicht die ID stehen?
		if (subModel.getName() == null || subModel.getName().isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.subModels.put(subModel.getId(), subModel);
		subModel.setParent(this);
	}
	
	
	/**
	 * Return all registered sub models of this AAS
	 */
	@Override
	public Map<String, ISubModel> getSubModels() {
		return this.subModels;
	}
	
	
	/**
	 * Add a contained Asset Administration shell to this AAS
	 * 
	 * @param aas the nested AAS to be added
	 */
	public synchronized void addAssetAdministrationShell(AssetAdministrationShell aas) {
		this.assetAdministrationShells.put(aas.name, aas);
		aas.setParent(this);
	}

	
	/**
	 * Get contained Asset Administration shells
	 * 
	 * @return All contained asset administration shells
	 */
	public Map<String, AssetAdministrationShell> getAssetAdministrationShells() {
		return this.assetAdministrationShells;
	}

	
	/**
	 * Get asset type definition
	 * 
	 * @return Asset type
	 */
	public String getAssetTypeDefinition() {
		return assetTypeDefinition;
	}

	
	/**
	 * Set asset type definition
	 * 
	 * @param assetTypeDefinition New aset type definiton value
	 */
	public void setAssetTypeDefinition(String assetTypeDefinition) {
		this.assetTypeDefinition = assetTypeDefinition;
	}

	
	/**
	 * Get display name
	 * 
	 * @return Display name
	 */
	public String getDisplayName() {
		return displayName;
	}

	
	/**
	 * Set display name
	 * 
	 * @param displayName New display name
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	
	/**
	 * Return the asset ID, a unique identifier that identifies an asset
	 * 
	 * @return Asset ID
	 */
	public String getAssetId() {
		return assetId;
	}

	
	/**
	 * Change the asset ID, a unique identifier that identifies an asset
	 * - FIXME: Should this really be permitted?
	 * 
	 * @param assetId updated Asset ID value
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	
	/**
	 * Return a contained element
	 */
	@Override
	public IElement getElement(String name) {
		return subModels.get(name);
	}


	/**
	 * Return all contained elements
	 */	
	@Override @SuppressWarnings({ "unchecked", "rawtypes" }) 
	public Map<String, IElement> getElements() {
		return (Map) subModels;
	}
}

