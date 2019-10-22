package org.eclipse.basyx.submodel.metamodel.map.modeltype;

import java.util.HashMap;
import java.util.Map;

/**
 * Describes the type of the used model and is used to add a model type to
 * existing AAS meta model entries. <br />
 * Needed for C# compatibility
 * 
 * @author schnicke
 *
 */
public class ModelType extends HashMap<String, Object> {
	private static final long serialVersionUID = 6097142020732632569L;

	public static final String MODELTYPE = "modelType";
	public static final String NAME = "name";

	public ModelType(String type) {
		Map<String, Object> map = new HashMap<>();
		map.put(NAME, type);
		put(MODELTYPE, map);
	}
}
