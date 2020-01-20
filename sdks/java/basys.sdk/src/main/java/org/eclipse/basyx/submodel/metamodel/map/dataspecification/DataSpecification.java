package org.eclipse.basyx.submodel.metamodel.map.dataspecification;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecification;
import org.eclipse.basyx.submodel.metamodel.api.dataspecification.IDataSpecificationContent;
import org.eclipse.basyx.submodel.metamodel.facade.dataspecification.DataSpecificationIEC61360Facade;

/**
 * DataSpecification as defined in DAAS document <br />
 *
 * @author schnicke
 *
 */
public class DataSpecification extends HashMap<String, Object> implements IDataSpecification {
	private static final long serialVersionUID = 1L;

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
		return new DataSpecificationIEC61360Facade((Map<String, Object>) get(CONTENT));
	}
}
