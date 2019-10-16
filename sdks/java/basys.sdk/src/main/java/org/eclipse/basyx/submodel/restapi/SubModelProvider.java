package org.eclipse.basyx.submodel.restapi;

import java.util.Map;

import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;

/**
 * Additional VAB provider specific for providing submodels together
 * with other SDKs by implementing the submodel API.
 *
 * The VAB provides generic models without considering high-level information.
 * For example, Operation submodel elements are realized as a Map. The function
 * is contained and can be accessed at /operations/opId/invokable. Therefore
 * invoking the Operation directly at /operations/opId/ attempts to invoke the
 * Map, which does not have any effect for the VABModelProvider.
 *
 * This provider maps these requests to the VAB primitives to generate the
 * desired behavior as it is described in the BaSys documentation.
 *
 * @author espen, schnicke
 *
 */
public class SubModelProvider extends ContainerPropertyProvider {
	/**
	 * Default constructor - based on an empty submodel with a lambda provider
	 */
	public SubModelProvider() {
		this(new SubModel());
	}

	/**
	 * Creates a SubModelProvider based on a lambda provider and a given model
	 */
	public SubModelProvider(SubModel model) {
		super(new VABLambdaProvider(model));
	}

	/**
	 * Creates a SubModelProvider based on a Map that is supposed to represent the submodel
	 */
	public SubModelProvider(Map<String, Object> model) {
		super(new VABLambdaProvider(model));
	}

	/**
	 * Creates a SubModelProvider based on a given IModelProvider. Should be a
	 * low-level VAB model provider, for example a VABHashmapProvider.
	 */
	public SubModelProvider(IModelProvider modelProvider) {
		super(modelProvider);
	}

	/**
	 * Strips submodel prefix if it exists
	 *
	 * @param path
	 * @return
	 */
	private String removeSubmodelPrefix(String path) {
		path = VABPathTools.stripSlashes(path);
		if (path.startsWith("submodel/")) {
			path = path.replaceFirst("submodel/", "");
		}
		path = VABPathTools.stripSlashes(path);
		return path;
	}

	@Override
	public Object getModelPropertyValue(String path) throws Exception {
		path = removeSubmodelPrefix(path);
		return super.getModelPropertyValue(path);
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws Exception {
		path = removeSubmodelPrefix(path);
		super.setModelPropertyValue(path, newValue);
	}

	@Override
	public void createValue(String path, Object newEntity) throws Exception {
		path = removeSubmodelPrefix(path);
		super.createValue(path, newEntity);
	}

	@Override
	public void deleteValue(String path) throws Exception {
		path = removeSubmodelPrefix(path);
		super.deleteValue(path);
	}

	@Override
	public void deleteValue(String path, Object obj) throws Exception {
		path = removeSubmodelPrefix(path);
		super.deleteValue(path, obj);
	}

	@Override
	public Object invokeOperation(String path, Object... parameters) throws Exception {
		path = removeSubmodelPrefix(path);
		return super.invokeOperation(path, parameters);
	}
}
