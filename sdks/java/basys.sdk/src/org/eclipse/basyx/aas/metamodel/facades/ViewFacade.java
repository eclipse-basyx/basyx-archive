package org.eclipse.basyx.aas.metamodel.facades;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.basyx.aas.api.metamodel.aas.parts.IView;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.View;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasDataSpecification;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasSemantics;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Referable;

/**
 * Facade providing access to a map containing the View  structure
 * @author rajashek
 *
 */

public class ViewFacade implements IView {

	
	private Map<String, Object> map;
	public ViewFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setContainedElement(Set<IReference> references) {
		map.put(View.CONTAINEDELEMENT, references);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IReference> getContainedElement() {
		return (Set<IReference>)map.get(View.CONTAINEDELEMENT);
	}

	@Override
	public IReference getSemanticId() {
		return (IReference)map.get(HasSemantics.SEMANTICID);
	}

	@Override
	public void setSemanticID(IReference ref) {
		map.put(HasSemantics.SEMANTICID, ref);
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HashSet<IReference> getDataSpecificationReferences() {
		return (HashSet<IReference>) map.get(HasDataSpecification.HASDATASPECIFICATION);
	}

	@Override
	public void setDataSpecificationReferences(HashSet<IReference> ref) {
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
	public IReference  getParent() {
		return (IReference)map.get(Referable.PARENT);
	}

	@Override
	public void setIdshort(String idShort) {
		map.put(Referable.IDSHORT, idShort);
		
	}

	@Override
	public void setCategory(String category) {
		map.put(Referable.CATEGORY, category);
		
	}

	@Override
	public void setDescription(String description) {
		map.put(Referable.DESCRIPTION, description);
		
	}

	@Override
	public void setParent(IReference obj) {
		map.put(Referable.PARENT, obj);
		
	}

}
