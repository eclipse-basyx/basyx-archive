package org.eclipse.basyx.aas.metamodel.factory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.AssetAdministrationShell_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel_;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.ComplexDataProperty;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.Property;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty.PropertySingleValued;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty.ValueType;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.operation.Operation;
import org.eclipse.basyx.vab.core.ref.VABElementRef;
import org.eclipse.basyx.vab.provider.lambda.VABLambdaProviderHelper;

/**
 * Creates meta model entities <br />
 * Allows passing preconfigured entities to be copied
 * 
 * @author schnicke
 *
 */
public class MetaModelElementFactory {

	/**
	 * Create Property
	 * 
	 * @param prop
	 * @param get
	 * @param set
	 * @return
	 */
	public PropertySingleValued create(PropertySingleValued prop, Object value) {
		PropertySingleValued ret = new PropertySingleValued();
		ret.putAll(prop);
		ret.put("value", value);
		ret.put("valueType", new ValueType(value));
		return ret;
	}

	/**
	 * Create VABProperty
	 * 
	 * @param prop
	 * @param get
	 * @param set
	 * @return
	 */
	public PropertySingleValued create(PropertySingleValued prop, Supplier<Object> get, Consumer<Object> set) {
		PropertySingleValued ret = new PropertySingleValued();
		ret.putAll(prop);
		Map<String, Object> value = VABLambdaProviderHelper.createSimple(get, set);
		ret.put("value", value);
		ret.put("valueType", new ValueType(get.get()));
		return ret;
	}

	/**
	 * Create VABOperations
	 * 
	 * @param operation
	 * @param function
	 * @return
	 */
	public Operation createOperation(Operation operation, Function<Object[], Object> function) {
		Operation ret = new Operation();
		ret.putAll(operation);
		ret.put("invokable", function);
		return ret;
	}

	/**
	 * Create ComplexDataProperty
	 * 
	 * @param container
	 * @param object
	 */
	@SuppressWarnings("unchecked")
	public ComplexDataProperty createContainer(ComplexDataProperty property, List<Property> properties, List<Operation> operations) {
		ComplexDataProperty ret = new ComplexDataProperty();
		ret.putAll(property);

		((Map<String, Object>) ret.get("properties")).putAll(createElemMap(properties));
		((Map<String, Object>) ret.get("operations")).putAll(createElemMap(operations));
		return ret;
	}

	@SuppressWarnings("unchecked")
	public SubModel_ create(SubModel_ subModel, List<Property> properties, List<Operation> operations) {
		SubModel_ ret = new SubModel_();
		ret.putAll(subModel);
		((Map<String, Object>) ret.get("properties")).putAll(createElemMap(properties));
		((Map<String, Object>) ret.get("operations")).putAll(createElemMap(operations));
		return ret;
	}

	@SuppressWarnings("unchecked")
	public AssetAdministrationShell_ create(AssetAdministrationShell_ shell, Set<VABElementRef> submodels) {
		AssetAdministrationShell_ ret = new AssetAdministrationShell_();
		ret.putAll(shell);
		Map<String, Object> bodies = (Map<String, Object>) ret.get("body");
		Set<VABElementRef> refs = (Set<VABElementRef>) bodies.get("submodels");
		refs.addAll(submodels);
		return ret;
	}

	private <T extends IElement> Map<String, T> createElemMap(List<T> elem) {
		Map<String, T> ret = new HashMap<>();
		for (T o : elem) {
			ret.put(o.getId(), o);
		}
		return ret;
	}
}
