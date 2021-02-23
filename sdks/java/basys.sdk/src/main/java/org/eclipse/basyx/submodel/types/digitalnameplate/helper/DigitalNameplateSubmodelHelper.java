package org.eclipse.basyx.submodel.types.digitalnameplate.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;

public class DigitalNameplateSubmodelHelper {
	
	public static List<ISubmodelElement> getSubmodelElementsByIdPrefix(String prefix, Map<String, ISubmodelElement> elemMap) {
		if (elemMap != null && elemMap.size() > 0) {
			return elemMap.values().stream().filter(s -> s.getIdShort().startsWith(prefix)).collect(Collectors.toList());
		}
		return new ArrayList<ISubmodelElement>();
	}
}
