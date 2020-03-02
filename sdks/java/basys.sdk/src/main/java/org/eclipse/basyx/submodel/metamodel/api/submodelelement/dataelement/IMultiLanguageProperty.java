package org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;

/**
 * A property that has a multi language value.
 * 
 * @author schnicke
 *
 */
public interface IMultiLanguageProperty extends IDataElement {

	/**
	 * Gets the value of the property instance
	 * 
	 * @return
	 */
	LangStrings getValue();

	/**
	 * Gets the reference to the global unique id of a coded value.
	 * 
	 * @return
	 */
	IReference getValueId();
}
