package org.eclipse.basyx.aas.metamodel.facades;

import java.util.Map;

import org.eclipse.basyx.aas.api.exception.FeatureNotImplementedException;
import org.eclipse.basyx.aas.api.metamodel.aas.reference.IReference;
import org.eclipse.basyx.aas.api.metamodel.aas.submodelelement.IReferenceElement;
import org.eclipse.basyx.aas.api.resources.PropertyType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.submodelelement.property.Property;
/**
 * Facade providing access to a map containing the ReferenceElement structure
 * @author rajashek
 *
 */
public class ReferenceElementFacade implements IReferenceElement {
	private Map<String, Object> map;
	public ReferenceElementFacade(Map<String, Object> map) {
		super();
		this.map = map;
	}

	@Override
	public void setValue(IReference ref) {
		map.put(Property.VALUE, ref);
		
	}

	@Override
	public IReference getValue() {
		return (IReference)map.get(Property.VALUE);
	}

	@Override
	public PropertyType getPropertyType() {
		throw new FeatureNotImplementedException();

	}

	@Override
	public void setValue(Object obj) {
		throw new FeatureNotImplementedException();

		
	}

	@Override
	public void setValueId(Object obj) {
		throw new FeatureNotImplementedException();

		
	}

	@Override
	public Object getValueId() {
		throw new FeatureNotImplementedException();

	}

	@Override
	public String getId() {
		throw new FeatureNotImplementedException();

	}

	@Override
	public void setId(String id) {
		throw new FeatureNotImplementedException();

		
	}


	
}
