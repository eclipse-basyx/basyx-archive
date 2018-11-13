package org.eclipse.basyx.aas.impl.resources.basic;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.api.resources.ISubModel;

/**
 * Technology independent Asset Administration Shell (AAS) model implementation
 * 
 * @author schoeffler, ziesche
 *
 */
public class _AssetAdministrationShell extends BaseElement implements IAssetAdministrationShell {

	/**
	 * Store contained sub models
	 */
	public Map<String, ISubModel> submodels = new HashMap<>();

	/**
	 * Add a sub model to this AAS
	 */
	@Override
	public synchronized void addSubModel(ISubModel subModel) {
		if (subModel.getId() == null || subModel.getId().isEmpty()) {
			throw new IllegalArgumentException();
		}
		this.submodels.put(subModel.getId(), subModel);
		subModel.setParent(this);
	}

	/**
	 * Return all registered sub models of this AAS
	 */
	@Override
	public Map<String, ISubModel> getSubModels() {
		return this.submodels;
	}

	/**
	 * Return a contained element
	 */
	@Override
	public IElement getElement(String name) {
		return submodels.get(name);
	}

	/**
	 * Return all contained elements
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, IElement> getElements() {
		return (Map) submodels;
	}
}
