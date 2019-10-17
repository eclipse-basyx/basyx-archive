package org.eclipse.basyx.submodel.metamodel.connected.submodelelement;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.qualifiable.IConstraint;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasDataSpecificationFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.HasSemanticsFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.haskind.HasKindFacade;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.qualifiable.QualifiableFacade;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.property.SingleProperty;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of SubmodelElementCollection
 * 
 * @author rajashek
 *
 */
public class ConnectedSubmodelElementCollection extends ConnectedSubmodelElement implements ISubmodelElementCollection {
	public ConnectedSubmodelElementCollection(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return new HasDataSpecificationFacade(getElem()).getDataSpecificationReferences();
	}

	@Override
	public String getIdShort() {
		return new ReferableFacade(getElem()).getIdShort();
	}

	@Override
	public String getCategory() {
		return new ReferableFacade(getElem()).getCategory();
	}

	@Override
	public String getDescription() {
		return new ReferableFacade(getElem()).getDescription();
	}

	@Override
	public IReference getParent() {
		return new ReferableFacade(getElem()).getParent();
	}

	@Override
	public Set<IConstraint> getQualifier() {
		return new QualifiableFacade(getElem()).getQualifier();
	}

	@Override
	public IReference getSemanticId() {
		return new HasSemanticsFacade(getElem()).getSemanticId();
	}

	@Override
	public String getHasKindReference() {
		return new HasKindFacade(getElem()).getHasKindReference();
	}

	@Override
	public void setValue(List<Object> value) {
		getProxy().setModelPropertyValue(SingleProperty.VALUE, value);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getValue() {
		return (List<Object>) getProxy().getModelPropertyValue(SingleProperty.VALUE);
	}

	@Override
	public void setOrdered(boolean value) {
		getProxy().setModelPropertyValue(SubmodelElementCollection.ORDERED, value);
	}

	@Override
	public boolean isOrdered() {
		return (boolean) getElem().getPath(SubmodelElementCollection.ORDERED);
	}

	@Override
	public void setAllowDuplicates(boolean value) {
		getProxy().setModelPropertyValue(SubmodelElementCollection.ALLOWDUPLICATES, value);

	}

	@Override
	public boolean isAllowDuplicates() {
		return (boolean) getElem().getPath(SubmodelElementCollection.ALLOWDUPLICATES);
	}

	@Override
	public void setElements(Map<String, ISubmodelElement> value) {
		getProxy().setModelPropertyValue(SubModel.SUBMODELELEMENT, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, ISubmodelElement> getElements() {
		return (Map<String, ISubmodelElement>) getProxy().getModelPropertyValue(SubModel.SUBMODELELEMENT);
	}
}
