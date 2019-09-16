package org.eclipse.basyx.aas.api.metamodel.aas;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDictionary;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.vab.IElement;

/**
 * Asset Administration Shell (AAS) interface
 * 
 * @author kuhn
 *
 */

public interface IAssetAdministrationShell extends IElement, IIdentifiable, IHasDataSpecification {
	/**
	 * Return all registered sub models of this AAS
	 * 
	 * @return
	 */
	public Map<String, ISubModel> getSubModels();

	/**
	 * Add a sub model to the AAS
	 * 
	 * @param subModel
	 *            The added sub model
	 */
	public void addSubModel(ISubModel subModel);

	public ISecurity getSecurity();

	public IReference getDerivedFrom();

	public IReference getAsset();

	public void setSubModel(Set<IReference> submodels);

	public Set<IReference> getSubModel();

	public Set<IView> getViews();

	public Set<IConceptDictionary> getConceptDictionary();
}
