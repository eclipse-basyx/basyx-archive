package org.eclipse.basyx.submodel.metamodel.map.dataspecification;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecificationContent;
import org.eclipse.basyx.vab.model.VABModelMap;

public class DataSpecificationContent extends VABModelMap<Object> implements IDataSpecificationContent {
	/**
	 * Creates a DataSpecificationContent object from a map
	 * 
	 * @param obj
	 *            a DataSpecificationContent object as raw map
	 * @return a DataSpecificationContent object, that behaves like a facade for the given map
	 */
	public static DataSpecificationContent createAsFacade(Map<String, Object> map) {
		if (map == null) {
			return null;
		}

		DataSpecificationContent ret = new DataSpecificationContent();
		ret.setMap(map);
		return ret;
	}
}
