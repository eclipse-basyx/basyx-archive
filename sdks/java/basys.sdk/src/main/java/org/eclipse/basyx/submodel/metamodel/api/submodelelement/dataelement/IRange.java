package org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement;

import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;

/**
 * A range data element is a data element that defines a range with min and max.
 * 
 * @author schnicke
 *
 */
public interface IRange extends IDataElement {
	/**
	 * Returns data type of the min und max
	 * 
	 * @return
	 */
	PropertyValueTypeDef getValueType();

	/**
	 * Returns the minimum value of the range. <br />
	 * <br />
	 * If the min value is missing then the value is assumed to be negative
	 * infinite.
	 * 
	 * @return
	 */
	Object getMin();

	/**
	 * The maximum value of the range. <br />
	 * <br />
	 * If the max value is missing then the value is assumed to be positive
	 * infinite.
	 */
	Object getMax();
}
