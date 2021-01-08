package org.eclipse.basyx.submodel.metamodel.connected.submodelelement.dataelement;

import java.util.Collection;
import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IMultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;

/**
 * "Connected" implementation of IMultiLanguageProperty
 * @author conradi
 *
 */
public class ConnectedMultiLanguageProperty extends ConnectedDataElement implements IMultiLanguageProperty {

	public ConnectedMultiLanguageProperty(VABElementProxy proxy) {
		super(proxy);
	}

	@Override
	@SuppressWarnings("unchecked")
	public LangStrings getValue() {
		return LangStrings.createAsFacade((Collection<Map<String, Object>>) getElem().getPath(MultiLanguageProperty.VALUE));
	}

	@Override
	@SuppressWarnings("unchecked")
	public IReference getValueId() {
		return Reference.createAsFacade((Map<String, Object>) getElem().getPath(MultiLanguageProperty.VALUEID));
	}

	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.MULTILANGUAGEPROPERTY;
	}

	@Override
	public MultiLanguageProperty getLocalCopy() {
		return MultiLanguageProperty.createAsFacade(getElem()).getLocalCopy();
	}
}
