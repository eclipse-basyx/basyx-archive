package org.eclipse.basyx.submodel.metamodel.facade.qualifier;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IReferable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Description;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.Referable;

/**
 * Facade providing access to a map containing the Referable structure the
 * Overridden function names are self explanatory
 * 
 * @author rajashek
 *
 */

public class ReferableFacade implements IReferable {
	private Map<String, Object> map;

	public ReferableFacade(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public String getIdShort() {
		return (String) map.get(Referable.IDSHORT);
	}

	@Override
	public String getCategory() {
		return (String) map.get(Referable.CATEGORY);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Description getDescription() {
		Description desc = new Description();
		desc.putAll((Map<String, Object>) map.get(Referable.DESCRIPTION));
		return desc;
	}

	@Override
	public IReference getParent() {
		return (IReference) map.get(Referable.PARENT);
	}

	public void setIdShort(String idShort) {
		map.put(Referable.IDSHORT, idShort);
	}

	public void setCategory(String category) {
		map.put(Referable.CATEGORY, category);
	}

	public void setDescription(Description description) {
		map.put(Referable.DESCRIPTION, description);
	}

	public void setParent(IReference obj) {
		map.put(Referable.PARENT, obj);
	}

}
