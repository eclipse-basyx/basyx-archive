package org.eclipse.basyx.submodel.metamodel.facade.qualifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.qualifier.IReferable;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.facade.reference.ReferenceFacade;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
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

	@Override
	@SuppressWarnings("unchecked")
	public LangStrings getDescription() {
		LangStrings desc = new LangStrings((HashSet<HashMap<String, Object>>) map.get(Referable.DESCRIPTION));
		return desc;
	}

	@Override
	@SuppressWarnings("unchecked")
	public IReference getParent() {
		return new ReferenceFacade((Map<String, Object>) map.get(Referable.PARENT));
	}

	public void setIdShort(String idShort) {
		map.put(Referable.IDSHORT, idShort);
	}

	public void setCategory(String category) {
		map.put(Referable.CATEGORY, category);
	}

	public void setDescription(LangStrings description) {
		map.put(Referable.DESCRIPTION, description);
	}

	public void setParent(IReference obj) {
		map.put(Referable.PARENT, obj);
	}

}
