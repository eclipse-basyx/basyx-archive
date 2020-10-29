package org.eclipse.basyx.submodel.restapi.vab;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.operation.IOperation;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;
import org.eclipse.basyx.submodel.restapi.MultiSubmodelElementProvider;
import org.eclipse.basyx.submodel.restapi.OperationProvider;
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
	private MultiSubmodelElementProvider getElementProvider() {
		IModelProvider elementProxy = new VABElementProxy(SubModel.SUBMODELELEMENT, modelProvider);
		return new MultiSubmodelElementProvider(elementProxy);
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
		Collection<Map<String, Object>> operations = (Collection<Map<String, Object>>) getElementProvider().getModelPropertyValue(MultiSubmodelElementProvider.ELEMENTS);
		return operations.stream().map(e -> SubmodelElement.createAsFacade(e)).collect(Collectors.toList());
	}

	@Override
	public void addSubmodelElement(ISubmodelElement elem) {
		getElementProvider().createValue(MultiSubmodelElementProvider.ELEMENTS, elem);
	}

	@Override
	public void addSubmodelElement(List<String> idShorts, ISubmodelElement elem) {
		getElementProvider().createValue(buildNestedElementPath(idShorts), elem);
	}

	@Override
	public void deleteSubmodelElement(String idShort) {
		getElementProvider().deleteValue(MultiSubmodelElementProvider.ELEMENTS + "/" + idShort);
	}

	@Override
	public void deleteNestedSubmodelElement(List<String> idShorts) {
		getElementProvider().deleteValue(buildNestedElementPath(idShorts));
	}

	@Override
	public Collection<IOperation> getOperations() {
		return getElements().stream().filter(e -> e instanceof IOperation).map(e -> (IOperation) e).collect(Collectors.toList());
	}

	@Override
	public Collection<ISubmodelElement> getSubmodelElements() {
		return getElements().stream().filter(e -> e instanceof ISubmodelElement).map(e -> (ISubmodelElement) e).collect(Collectors.toList());
	}

	@Override
	public void updateSubmodelElement(String idShort, Object newValue) {
		getElementProvider().setModelPropertyValue(buildValuePathForProperty(idShort), newValue);
	}

	@Override
	public void updateNestedSubmodelElement(List<String> idShorts, Object newValue) {
		getElementProvider().setModelPropertyValue(buildNestedElementPath(idShorts) + "/" + Property.VALUE, newValue);
	}

	@Override
	public Object getSubmodelElementValue(String idShort) {
		return getElementProvider().getModelPropertyValue(buildValuePathForProperty(idShort));
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISubmodelElement getSubmodelElement(String idShort) {
		return SubmodelElement.createAsFacade((Map<String, Object>) getElementProvider().getModelPropertyValue(MultiSubmodelElementProvider.ELEMENTS + "/" + idShort));
	}

	@Override
	public Object invokeOperation(String idShort, Object... params) {
		return getElementProvider().invokeOperation(MultiSubmodelElementProvider.ELEMENTS + "/" + idShort, params);
	}
	
	@Override
	public Object invokeNestedOperation(List<String> idShorts, Object... params) {
		return getElementProvider().invokeOperation(buildNestedElementPath(idShorts), params);
	}
	
	@Override
	public Object invokeNestedOperationAsync(List<String> idShorts, Object... params) {
		return getElementProvider().invokeOperation(buildNestedElementPath(idShorts) + "/" + Operation.INVOKE + OperationProvider.ASYNC, params);
	}

	private String buildValuePathForProperty(String idShort) {
		return MultiSubmodelElementProvider.ELEMENTS + "/" + idShort + "/" + Property.VALUE;
	}

	@Override
	public Object getNestedSubmodelElementValue(List<String> idShorts) {
		return getElementProvider().getModelPropertyValue(buildNestedElementPath(idShorts) + "/" + Property.VALUE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ISubmodelElement getNestedSubmodelElement(List<String> idShorts) {
		Map<String, Object> map = (Map<String, Object>) getElementProvider().getModelPropertyValue(buildNestedElementPath(idShorts));
		return SubmodelElement.createAsFacade(map);
	}
	
	@Override
	public Object getOperationResult(List<String> idShorts, String requestId) {
		return getElementProvider().getModelPropertyValue(buildNestedElementPath(idShorts) + "/" + OperationProvider.INVOCATION_LIST + "/" + requestId);
	}

	/**
	 * @param idShorts
	 * @return
	 */
	private String buildNestedElementPath(List<String> idShorts) {
		return MultiSubmodelElementProvider.ELEMENTS + "/" + VABPathTools.concatenatePaths(idShorts.toArray(new String[0]));
	}

}
