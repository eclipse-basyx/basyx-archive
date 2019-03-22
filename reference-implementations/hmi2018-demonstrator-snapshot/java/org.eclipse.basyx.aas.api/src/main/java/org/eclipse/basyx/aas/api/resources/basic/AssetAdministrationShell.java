package org.eclipse.basyx.aas.api.resources.basic;

import java.util.HashMap;
import java.util.Map;

public class AssetAdministrationShell extends BaseElement {
	
	
	protected Map<String, SubModel> subModels = new HashMap<>();
	protected Map<String, AssetAdministrationShell> assetAdministrationShells = new HashMap<>();
	protected String assetTypeDefinition;
	protected String displayName;
	protected String assetId;
	protected AssetKind assetKind;
	
	
	public AssetAdministrationShell() {
		super();
	}
	

	public AssetKind getAssetKind() {
		return assetKind;
	}
	public void setAssetKind(AssetKind kind) {
		this.assetKind = kind;
	}
	
	
	public synchronized void addSubModel(SubModel subModel) {
		if (subModel.name == null || subModel.name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.subModels.put(subModel.name, subModel);
		subModel.setParent(this);
	}
	
	public Map<String, SubModel> getSubModels() {
		return this.subModels;
	}
	
	public synchronized void addAssetAdministrationShell(AssetAdministrationShell aas) {
		this.assetAdministrationShells.put(aas.name, aas);
		aas.setParent(this);
	}
	
	public Map<String, AssetAdministrationShell> getAssetAdministrationShells() {
		return this.assetAdministrationShells;
	}

	public String getAssetTypeDefinition() {
		return assetTypeDefinition;
	}

	public void setAssetTypeDefinition(String assetTypeDefinition) {
		this.assetTypeDefinition = assetTypeDefinition;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	
	

}
