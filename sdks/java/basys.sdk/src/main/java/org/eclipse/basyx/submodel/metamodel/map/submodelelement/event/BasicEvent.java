package org.eclipse.basyx.submodel.metamodel.map.submodelelement.event;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.event.IBasicEvent;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;

/**
 * A BasicEvent element as defined in DAAS document
 * 
 * @author conradi
 *
 */
public class BasicEvent extends SubmodelElement implements IBasicEvent {

	public static final String MODELTYPE = "BasicEvent";
	public static final String OBSERVED = "observed";
	
	public BasicEvent() {
		// Add model type
		putAll(new ModelType(MODELTYPE));
	}
	
	public BasicEvent(IReference observed) {
		this();
		put(OBSERVED, observed);
	}
	
	@Override
	protected KeyElements getKeyElement() {
		return KeyElements.BASICEVENT;
	}
	
	/**
	 * Creates a BasicEvent object from a map
	 * 
	 * @param obj a BasicEvent object as raw map
	 * @return a BasicEvent object, that behaves like a facade for the given map
	 */
	public static BasicEvent createAsFacade(Map<String, Object> obj) {
		BasicEvent facade = new BasicEvent();
		facade.setMap(obj);
		return facade;
	}
	
	/**
	 * Returns true if the given submodel element map is recognized as an BasicEvent element
	 */
	public static boolean isBasicEvent(Map<String, Object> map) {
		String modelType = ModelType.createAsFacade(map).getName();
		// Either model type is set or the element type specific attributes are contained (fallback)
		return MODELTYPE.equals(modelType) || (modelType == null && map.containsKey(OBSERVED));
	}

	@Override
	@SuppressWarnings("unchecked")
	public IReference getObserved() {
		return Reference.createAsFacade((Map<String, Object>) get(OBSERVED));
	}
}
