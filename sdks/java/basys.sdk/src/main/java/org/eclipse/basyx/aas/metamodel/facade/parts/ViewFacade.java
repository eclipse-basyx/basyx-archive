package org.eclipse.basyx.aas.metamodel.facade.parts;

import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.metamodel.api.parts.IView;
import org.eclipse.basyx.aas.metamodel.map.parts.View;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
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
		return (Set<IReference>) map.get(View.CONTAINEDELEMENT);
	}

	@Override
	public IReference getSemanticId() {
		return (IReference) map.get(HasSemantics.SEMANTICID);
	}

	public void setSemanticID(IReference ref) {
		map.put(HasSemantics.SEMANTICID, ref);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getDataSpecificationReferences() {
		return (Set<IReference>) map.get(HasDataSpecification.HASDATASPECIFICATION);
	}

	public void setDataSpecificationReferences(Set<IReference> ref) {
		map.put(HasDataSpecification.HASDATASPECIFICATION, ref);
	}

	@Override
	public String getIdshort() {
		return (String) map.get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) map.get(Referable.CATEGORY);
	}

	@Override
	public String getDescription() {
		return (String) map.get(Referable.DESCRIPTION);
	}

	@Override
	public IReference getParent() {
		return (IReference) map.get(Referable.PARENT);
	}

	public void setIdshort(String idShort) {
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
