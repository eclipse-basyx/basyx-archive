package org.eclipse.basyx.submodel.metamodel.map.dataspecification;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecificationContent;
import org.eclipse.basyx.vab.model.VABModelMap;

/**
 * DataSpecification as defined in DAAS document <br />
 *
 * @author schnicke
 *
 */
public class DataSpecification extends VABModelMap<Object> implements IDataSpecification {
	public static final String CONTENT = "content";

	/**
	 * Create a DataSpecification with an embedded {@link DataSpecificationContent}
	 * 
	 * @param content
	 */
	public DataSpecification(IDataSpecificationContent content) {
		put(CONTENT, content);
	}

	@Override
	public IDataSpecificationContent getContent() {
		return getContentFacade();
	}

	@SuppressWarnings("unchecked")
	private IDataSpecificationContent getContentFacade() {
		// Currently, only DataSpecificationIEC61630 is supported
		return DataSpecificationIEC61360.createAsFacade((Map<String, Object>) get(CONTENT));
	}
}
