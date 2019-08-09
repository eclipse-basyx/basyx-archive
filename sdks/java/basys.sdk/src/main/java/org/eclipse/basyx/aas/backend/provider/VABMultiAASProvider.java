package org.eclipse.basyx.aas.backend.provider;

import java.util.HashMap;

import org.eclipse.basyx.vab.core.IModelProvider;
import org.eclipse.basyx.vab.core.tools.VABPathTools;

/**
 * Provider, that redirects requests for different Asset Administration Shells.
 * e.g. path://aas1 refers to the AAS with id "aas1".
 * 
 * @author espen
 *
 */
public class VABMultiAASProvider implements IModelProvider {
	protected HashMap<String, VABMultiSubmodelProvider> aas_providers;

	public VABMultiAASProvider() {
		aas_providers = new HashMap<>();
	}

	/**
	 * Adds an Asset Administration Shell to this provider. The AAS will be
	 * accessible via path://*id
	 * 
	 * @param id
	 *            The id of the added Asset Administration Shell.
	 * @param modelProvider
	 *            The provider that contains the Asset Administration Shell.
	 */
	public void setAssetAdministrationShell(String id, VABMultiSubmodelProvider modelProvider) {
		aas_providers.put(id, modelProvider);
	}

	/**
	 * Removes all connected Asset Administration Shells from this provider
	 */
	public void clear() {
		aas_providers.clear();
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		String aasId = getId(path);
		if (aasId != null) {
			VABMultiSubmodelProvider provider = aas_providers.get(aasId);
			if (provider == null) {
				return null;
			}
			String subPath = getSubPath(path, aasId);
			return provider.getModelPropertyValue(subPath);
		}
		return null;
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		String aasId = getId(path);
		if (aasId != null) {
			VABMultiSubmodelProvider provider = aas_providers.get(aasId);
			if (provider == null) {
				return;
			}
			String subPath = getSubPath(path, aasId);
			provider.setModelPropertyValue(subPath, newValue);
		}
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		String aasId = getId(path);
		if (aasId != null) {
			VABMultiSubmodelProvider provider = aas_providers.get(aasId);
			if (provider == null) {
				return;
			}
			String subPath = getSubPath(path, aasId);
			provider.createValue(subPath, newEntity);
		}
	}

	@Override
	public void deleteValue(String path) throws Exception {
		String aasId = getId(path);
		if (aasId != null) {
			VABMultiSubmodelProvider provider = aas_providers.get(aasId);
			if (provider == null) {
				return;
			}
			String subPath = getSubPath(path, aasId);
			provider.deleteValue(subPath);
		}
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		String aasId = getId(path);
		if (aasId != null) {
			VABMultiSubmodelProvider provider = aas_providers.get(aasId);
			if (provider == null) {
				return;
			}
			String subPath = getSubPath(path, aasId);
			provider.deleteValue(subPath, obj);
		}
	}

	@Override
	public Object invokeOperation(String path, Object[] parameter) throws Exception {
		String aasId = getId(path);
		if (aasId != null) {
			VABMultiSubmodelProvider provider = aas_providers.get(aasId);
			if (provider == null) {
				return null;
			}
			String subPath = getSubPath(path, aasId);
			return provider.invokeOperation(subPath, parameter);
		}
		return null;
	}

	/**
	 * Returns the requested aas id from a given VAB path. E.g. returns "aas1", if
	 * the path is path://aas1/aas/
	 * 
	 * @param path
	 *            The requested VAB path
	 * @return The id of the requested Asset Administration Shell. Returns null, if
	 *         the path is invalid or does not contain an AAS id.
	 */
	private String getId(String path) {
		if (path == null || !path.startsWith("/path://")) {
			return null;
		}

		String[] elements = VABPathTools.splitPath(path);
		if (elements.length >= 3) {
			String aasId = elements[2];
			return aasId;
		} else {
			return null;
		}
	}

	/**
	 * Returns the sub path in the context of a given AAS id. E.g. returns
	 * "/aas/submodels", if the path is path://aas1/aas/submodels/
	 * 
	 * @param path
	 *            The requested VAB path
	 * @param aasId
	 *            The id of the requested Asset Administration Shell
	 * @return The remaining sub-path, when removing the id from the VAB path
	 */
	private String getSubPath(String path, String aasId) {
		String prefix = "/path://" + aasId;
		return path.substring(prefix.length());
	}
}
