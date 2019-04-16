package org.eclipse.basyx.aas.backend.connected;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.identifier.IIdentifier;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDictionary;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IAdministrativeInformation;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.vab.core.VABConnectionManager;
import org.eclipse.basyx.vab.core.proxy.VABElementProxy;

/**
 * Connected implementation of the AAS <br />
 * Allows access to a remote AAS
 * 
 * @author schnicke
 *
 */
public class ConnectedAssetAdministrationShell extends ConnectedElement implements IAssetAdministrationShell {

	VABConnectionManager manager;

	/**
	 * Constructor creating a ConnectedAAS pointing to the AAS represented by proxy
	 * and path
	 * 
	 * @param path
	 * @param proxy
	 * @param manager
	 */
	public ConnectedAssetAdministrationShell(String path, VABElementProxy proxy, VABConnectionManager manager) {
		super(path, proxy);
		this.manager = manager;
	}

	/**
	 * Copy constructor, allowing to create a ConnectedAAS pointing to the same AAS
	 * as <i>shell</i>
	 * 
	 * @param shell
	 */
	public ConnectedAssetAdministrationShell(ConnectedAssetAdministrationShell shell) {
		super(shell.getPath(), shell.getProxy());
		this.manager = shell.manager;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubModel> getSubModels() {
		Set<Map<?, ?>> refs = (Set<Map<?, ?>>) getProxy().readElementValue(constructPath("submodel"));
		Map<String, ISubModel> ret = new HashMap<>();
		for (Map<?, ?> key : refs) {
			String id = (String) ((Map<?, ?>) ((List<?>) key.get("key")).get(0)).get("value");
			VABElementProxy elem = manager.connectToVABElement(id);
			ISubModel sm = new ConnectedSubModel("/aas/submodels/" + id, elem);
			ret.put(id, sm);
		}

		return ret;
	}

	@Override
	public void addSubModel(ISubModel subModel) {
		throw new RuntimeException("Currently not implemented");
	}

	protected VABConnectionManager getManager() {
		return manager;
	}

	@Override
	public IAdministrativeInformation getAdministration() {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public IIdentifier getIdentification() {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public void setAdministration(String version, String revision) {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public void setIdentification(String idType, String id) {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public List<String> getSubModelIds() {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public void setSecurity(ISecurity security) {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public ISecurity getSecurity() {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public void setDerivedFrom(IReference derivedFrom) {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public IReference getDerivedFrom() {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public void setAsset(IReference asset) {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public IReference getAsset() {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public void setSubModel(Set<IReference> submodels) {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public Set<IReference> getSubModel() {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public void setViews(Set<IView> views) {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public Set<IView> getViews() {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public void setConceptDictionary(Set<IConceptDictionary> dictionaries) {
		throw new RuntimeException("Currently not implemented");
	}

	@Override
	public Set<IConceptDictionary> getConceptDictionary() {
		throw new RuntimeException("Currently not implemented");
	}

}
