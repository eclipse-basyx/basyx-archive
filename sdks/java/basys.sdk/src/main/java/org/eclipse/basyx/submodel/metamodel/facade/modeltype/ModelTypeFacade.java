package org.eclipse.basyx.submodel.metamodel.facade.modeltype;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;

public class ModelTypeFacade {
	@SuppressWarnings("unchecked")
	public static String getModelTypeName(Map<String, Object> map) {
		return (String) ((Map<String, Object>) map.get(ModelType.MODELTYPE)).get(ModelType.NAME);
	}
}
