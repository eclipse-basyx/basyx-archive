package org.eclipse.basyx.submodel.metamodel.map.submodelelement.entity;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.facade.submodelelement.SubmodelElementFacadeFactory;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.vab.model.VABModelMap;


/**
 * Container class for holding the value of Entity
 * 
 * @author conradi
 *
 */
public class EntityValue extends VABModelMap<Object> {

	private EntityValue() {}
	
	public EntityValue(Collection<ISubmodelElement> statements, IReference asset) {
		put(Entity.STATEMENT, statements);
		put(Entity.ASSET, asset);
	}
	
	/**
	 * Creates a EntityValue object from a map
	 * 
	 * @param obj a EntityValue object as raw map
	 * @return a EntityValue object, that behaves like a facade for the given map
	 */
	public static EntityValue createAsFacade(Map<String, Object> obj) {
		EntityValue facade = new EntityValue();
		facade.setMap(obj);
		return facade;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isEntityValue(Object value) {
		
		// Given Object must be a Map
		if(!(value instanceof Map<?, ?>)) {
			return false;
		}
		
		Map<String, Object> map = (Map<String, Object>) value;
		
		// Given Map must contain all necessary Entries
		if(!(map.get(Entity.STATEMENT) instanceof Collection<?> 
				&& Reference.isReference(map.get(Entity.ASSET)))) {
			return false;
		}
		
		try {
			// Try to create a Facade for each Element
			// If one of the Objects in STATEMENT is not a smElement,
			// SubmodelElementFacadeFactory throws an Exception
			((Collection<Map<String, Object>>) map.get(Entity.STATEMENT)).stream()
				.forEach(SubmodelElementFacadeFactory::createSubmodelElement);
		} catch (RuntimeException e) {
			return false;
		}
		
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ISubmodelElement> getStatement() {
		Collection<Map<String, Object>> elements = (Collection<Map<String, Object>>) get(Entity.STATEMENT);
		return elements.stream().map(e -> SubmodelElementFacadeFactory.createSubmodelElement(e)).collect(Collectors.toList());
	}
	
	@SuppressWarnings("unchecked")
	public IReference getAsset() {
		return Reference.createAsFacade((Map<String, Object>) get(Entity.ASSET));
	}
}
