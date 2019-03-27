package org.eclipse.basyx.aas.api.metamodel.aas.submodelelement;

import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IConceptDictionary;
import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IHasDataSpecification;
import org.eclipse.basyx.aas.api.metamodel.aas.qualifier.IIdentifiable;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.security.ISecurity;
import org.eclipse.basyx.aas.api.resources.IElement;

/**
 * Interface for AssetAdministrationShell
 * @author rajashek
 *
*/

public interface IAssetAdministrationShell extends IIdentifiable,IHasDataSpecification,IElement {
	
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
