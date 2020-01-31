package org.eclipse.basyx.submodel.metamodel.map.modeltype;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * Describes the type of the used model and is used to add a model type to
 * existing AAS meta model entries. <br />
 * Needed for C# compatibility
 * 
 * @author schnicke
 *
 */
public class ModelType extends VABModelMap<Object> {
	public static final String MODELTYPE = "modelType";
	public static final String NAME = "name";

	public ModelType(String type) {
		Map<String, Object> map = new HashMap<>();
		map.put(NAME, type);
		put(MODELTYPE, map);
	}

	private ModelType() {
	}

	/**
	 * Creates a DataSpecificationIEC61360 object from a map
	 * 
	 * @param obj
	 *            a DataSpecificationIEC61360 object as raw map
	 * @return a DataSpecificationIEC61360 object, that behaves like a facade for
	 *         the given map
	 */
	public static ModelType createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		ModelType ret = new ModelType();
		ret.setMap(map);
		return ret;
	}

	@SuppressWarnings("unchecked")
	public String getName() {
		return (String) ((Map<String, Object>) get(ModelType.MODELTYPE)).get(ModelType.NAME);
	}

}
