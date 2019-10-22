package org.eclipse.basyx.aas.metamodel.facade.parts;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.map.parts.View;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.qualifier.ReferableFacade;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceFacade;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceHelper;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasDataSpecification;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.HasSemantics;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;

/**
 * Facade providing access to a map containing the View structure
 * 
 * @author rajashek
 *
 */

public class ViewFacade implements IView {

	private Map<String, Object> map;

	public ViewFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	public void setContainedElement(Set<IReference> references) {
		map.put(View.CONTAINEDELEMENT, references);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getContainedElement() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(View.CONTAINEDELEMENT);
		return ReferenceHelper.transform(set);
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getSemanticId() {
		return new ReferenceFacade((Map<String, Object>) map.get(HasSemantics.SEMANTICID));
	}

	public void setSemanticID(IReference ref) {
		map.put(HasSemantics.SEMANTICID, ref);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getDataSpecificationReferences() {
		Set<Map<String, Object>> set = (Set<Map<String, Object>>) map.get(HasDataSpecification.HASDATASPECIFICATION);
		return ReferenceHelper.transform(set);
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
	}

	@Override
	public String getIdShort() {
		return (String) map.get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) map.get(Referable.CATEGORY);
	}

	@Override
	public Description getDescription() {
		return new ReferableFacade(map).getDescription();
	}

	@SuppressWarnings("unchecked")
	@Override
	public IReference getParent() {
		return new ReferenceFacade((Map<String, Object>) map.get(Referable.PARENT));
	}

	public void setIdShort(String idShort) {
		map.put(Referable.IDSHORT, idShort);
	}

	public void setCategory(String category) {
		map.put(Referable.CATEGORY, category);
	}

	public void setDescription(String description) {
		map.put(Referable.DESCRIPTION, description);
	}

	public void setParent(IReference obj) {
		map.put(Referable.PARENT, obj);
	}

}
