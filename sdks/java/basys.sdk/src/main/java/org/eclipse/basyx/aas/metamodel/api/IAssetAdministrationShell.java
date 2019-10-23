package org.eclipse.basyx.aas.metamodel.api;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.parts.IAsset;
import org.eclipse.basyx.aas.metamodel.api.parts.IConceptDictionary;
import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.api.security.ISecurity;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.submodel.metamodel.api.IElement;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IHasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.qualifier.IIdentifiable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;

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
	public void addSubModel(SubmodelDescriptor subModel);

	public ISecurity getSecurity();

	public IReference getDerivedFrom();

	public IAsset getAsset();

	public void setSubModels(Set<SubmodelDescriptor> submodels);

	public Set<SubmodelDescriptor> getSubModelDescriptors();

	public Set<IView> getViews();

	public Set<IConceptDictionary> getConceptDictionary();
}
