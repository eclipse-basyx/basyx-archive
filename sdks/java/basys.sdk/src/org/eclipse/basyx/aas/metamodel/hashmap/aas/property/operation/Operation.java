package org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation;

import java.util.List;

import org.eclipse.basyx.aas.api.resources.IOperation;
import org.eclipse.basyx.aas.api.resources.ParameterType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.enums.DataObjectType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.Property;

/**
 * Operation property class
 * 
 * @author pschorn
 *
 */
public class Operation extends Property implements IOperation {

	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public Operation() {
		// Add qualifiers
		// putAll(new Property());

		super();

		putAll(new OperationParameter(null));
		putAll(new Endpoint(null));

		// Default values
		put("requests", null);
		put("requestedBy", null);

	}

	@Override
	public List<ParameterType> getParameterTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataObjectType getReturnDataType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object invoke(Object... params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
