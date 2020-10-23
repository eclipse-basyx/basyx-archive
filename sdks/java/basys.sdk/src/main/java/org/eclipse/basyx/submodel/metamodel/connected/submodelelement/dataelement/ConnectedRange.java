package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IRange;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDefHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.range.Range;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.range.RangeValue;
import org.eclipse.basyx.submodel.restapi.MultiSubmodelElementProvider;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of IRange
 * @author conradi
 *
 */
public class ConnectedRange extends ConnectedDataElement implements IRange {

	public ConnectedRange(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	public PropertyValueTypeDef getValueType() {
		return PropertyValueTypeDefHelper.readTypeDef(getElem().getPath(Range.VALUETYPE));
	}

	@Override
	public Object getMin() {
		Object min = getElem().getPath(Range.MIN);
		return PropertyValueTypeDefHelper.getJavaObject(min, getValueType());
	}

	@Override
	public Object getMax() {
		Object max = getElem().getPath(Range.MAX);
		return PropertyValueTypeDefHelper.getJavaObject(max, getValueType());
	}

	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.RANGE;
	}

	@Override
	public RangeValue getValue() {
		return new RangeValue(getMin(), getMax());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setValue(Object value) {
		if(RangeValue.isRangeValue(value)) {
			RangeValue rangeValue = RangeValue.createAsFacade((Map<String, Object>) value);
			Object minRaw = rangeValue.getMin();
			Object maxRaw = rangeValue.getMax();

			RangeValue prepared = new RangeValue(
					PropertyValueTypeDefHelper.prepareForSerialization(minRaw),
					PropertyValueTypeDefHelper.prepareForSerialization(maxRaw)
				);
					
			
			getProxy().setModelPropertyValue(MultiSubmodelElementProvider.VALUE, prepared);
		} else {
			throw new IllegalArgumentException("Given object " + value + " is not a RangeValue");
		}
	}
}