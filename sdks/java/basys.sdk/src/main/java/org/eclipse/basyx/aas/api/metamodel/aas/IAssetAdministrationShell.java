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

public interface IAssetAdministrationShell extends IElement, IIdentifiable,IHasDataSpecification {
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

	public void setSecurity(ISecurity security);

	public ISecurity getSecurity();

	public void setDerivedFrom(IReference derivedFrom);

	public IReference getDerivedFrom();

	public void setAsset(IReference asset);

	public IReference getAsset();

	public void setSubModel(Set<IReference> submodels);

	public Set<IReference> getSubModel();

	public void setViews(Set<IView> views);

	public Set<IView> getViews();

	public void setConceptDictionary(Set<IConceptDictionary> dictionaries);

	public Set<IConceptDictionary> getConceptDictionary();
}
