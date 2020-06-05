package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement;

import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IRange;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.Range;
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
	public String getValueType() {
		return (String) getElem().getPath(Range.VALUETYPE);
	}

	@Override
	public Object getMin() {
		return getElem().getPath(Range.MIN);
	}

	@Override
	public Object getMax() {
		return getElem().getPath(Range.MAX);
	}

	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.RANGE;
	}
}