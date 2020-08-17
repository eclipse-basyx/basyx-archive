package org.eclipse.basyx.submodel.restapi.vab;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.SubmodelElementProvider;
import org.eclipse.basyx.submodel.restapi.api.ISubmodelAPI;
import org.eclipse.basyx.vab.modelprovider.VABElementProxy;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Implements the Submodel API by mapping it to VAB paths
 * 
 * @author schnicke
 *
 */
public class VABSubmodelAPI implements ISubmodelAPI {

	// The VAB model provider containing the model this API implementation is based
	// on
	private IModelProvider modelProvider;

	/**
	 * Creates a VABSubmodelAPI that wraps an IModelProvider
	 * 
	 * @param modelProvider
	 *            providing the Submodel
	 */
	public VABSubmodelAPI(IModelProvider modelProvider) {
		super();
		this.modelProvider = modelProvider;
	}

	/**
	 * Creates an IModelProvider for handling accesses to the elements within the
	 * submodel
	 * 
	 * @return returns the SubmodelElementProvider pointing to the contained
	 *         submodelelements
	 */
	private SubmodelElementProvider getElementProvider() {
		IModelProvider elementProxy = new VABElementProxy(SubModel.SUBMODELELEMENT, modelProvider);
		return new SubmodelElementProvider(elementProxy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISubModel getSubmodel() {
		// For access on the container property root, return the whole model
		Map<String, Object> map = (Map<String, Object>) modelProvider.getModelPropertyValue("");

		// Only return a copy of the Submodel
		Map<String, Object> smCopy = new HashMap<>();
		smCopy.putAll(map);
		return SubModel.createAsFacade(smCopy);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ISubmodelElement> getElements() {
		Collection<Map<String, Object>> operations = (Collection<Map<String, Object>>) getElementProvider().getModelPropertyValue(SubmodelElementProvider.ELEMENTS);
		return operations.stream().map(e -> SubmodelElement.createAsFacade(e)).collect(Collectors.toList());
	}

	@Override
	public void addSubmodelElement(ISubmodelElement elem) {
		getElementProvider().createValue(SubmodelElementProvider.ELEMENTS + "/" + elem.getIdShort(), elem);
	}

	@Override
	public void deleteSubmodelElement(String idShort) {
		getElementProvider().deleteValue(SubmodelElementProvider.ELEMENTS + "/" + idShort);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<IOperation> getOperations() {
		Collection<Map<String, Object>> operations = (Collection<Map<String, Object>>) getElementProvider().getModelPropertyValue(SubmodelElementProvider.OPERATIONS);
		return operations.stream().map(e -> Operation.createAsFacade(e)).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<IProperty> getProperties() {
		Collection<Map<String, Object>> props = (Collection<Map<String, Object>>) getElementProvider().getModelPropertyValue(SubmodelElementProvider.PROPERTIES);
		return props.stream().map(e -> Property.createAsFacade(e)).collect(Collectors.toList());
	}

	@Override
	public void updateProperty(String idShort, Object newValue) {
		getElementProvider().setModelPropertyValue(buildValuePathForProperty(idShort), newValue);
	}

	@Override
	public Object getPropertyValue(String idShort) {
		return getElementProvider().getModelPropertyValue(buildValuePathForProperty(idShort));
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISubmodelElement getSubmodelElement(String idShort) {
		return SubmodelElement.createAsFacade((Map<String, Object>) getElementProvider().getModelPropertyValue(SubmodelElementProvider.ELEMENTS + "/" + idShort));
	}

	@Override
	public Object invokeOperation(String idShort, Object... params) {
		return getElementProvider().invokeOperation(SubmodelElementProvider.OPERATIONS + "/" + idShort, params);
	}

	private String buildValuePathForProperty(String idShort) {
		return SubmodelElementProvider.PROPERTIES + "/" + idShort + "/" + Property.VALUE;
	}

	@Override
	public Object getNestedPropertyValue(List<String> idShorts) {
		return getElementProvider().getModelPropertyValue(buildNestedElementPath(idShorts) + "/" + Property.VALUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISubmodelElement getNestedSubmodelElement(List<String> idShorts) {
		Map<String, Object> map = (Map<String, Object>) getElementProvider().getModelPropertyValue(buildNestedElementPath(idShorts));
		return SubmodelElement.createAsFacade(map);
	}

	/**
	 * @param idShorts
	 * @return
	 */
	private String buildNestedElementPath(List<String> idShorts) {
		return SubmodelElementProvider.ELEMENTS + "/" + VABPathTools.concatenatePaths(idShorts.toArray(new String[1]));
	}
}
