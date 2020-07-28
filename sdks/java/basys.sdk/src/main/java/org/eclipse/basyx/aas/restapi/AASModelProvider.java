package org.eclipse.basyx.aas.restapi;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.vab.exception.provider.NotAnInvokableException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProvider;

/**
 * Model provider explicitely meant to implement the access to the AAS object. This excludes access to the submodels,
 * that are wrapped into their own provider.
 * 
 * @author espen
 *
 */
public class AASModelProvider implements IModelProvider {

	private IModelProvider modelProvider;

	/**
	 * Constructor based on the model provider containing the AAS model
	 */
	public AASModelProvider(IModelProvider modelProvider) {
		this.modelProvider = modelProvider;
	}

	/**
	 * Creates a AASModelProvider based on a lambda provider and a given model
	 */
	public AASModelProvider(Map<String, Object> model) {
		this.modelProvider = new VABLambdaProvider(model);
	}

	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		return modelProvider.getModelPropertyValue(path);
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		modelProvider.setModelPropertyValue(path, newValue);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		VABPathTools.checkPathForNull(path);
		path = VABPathTools.stripSlashes(path);
		if (path.equals("submodels")) {
			Map<String, Object> smMap = (Map<String, Object>) newEntity;
			SubModel sm = SubModel.createAsFacade(smMap);
			modelProvider.createValue(path, sm.getReference());
		}
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		modelProvider.deleteValue(path);
	}

	@SuppressWarnings("unchecked")
	protected void removeSubmodelReference(String idShort, IIdentifier smId) {
		Collection<Map<String, Object>> smReferences = (Collection<Map<String, Object>>) modelProvider
				.getModelPropertyValue(AssetAdministrationShell.SUBMODELS);
		// Reference to submodel could be either by idShort (=> local) or directly via its identifier
		for (Iterator<Map<String, Object>> iterator = smReferences.iterator(); iterator.hasNext();) {
			Map<String, Object> smRefMap = iterator.next();
			IReference ref = Reference.createAsFacade(smRefMap);
			List<IKey> keys = ref.getKeys();
			IKey lastKey = keys.get(keys.size() - 1);
			KeyType idType = lastKey.getIdType();
			String idValue = lastKey.getValue();
			// remove this reference, if the last key points to the submodel
			// => the key either points to the idShort of the submodel or its identifier
			if ((idType.equals(KeyType.IDSHORT) && idValue.equals(idShort))
					|| (idType.toString().equals(smId.getIdType().toString()) && idValue.equals(smId.getId()))) {
				modelProvider.deleteValue(AssetAdministrationShell.SUBMODELS, ref);
				break;
			}
		}
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		modelProvider.deleteValue(path, obj);
	}

	/**
	 * Operations that can be invoked are not contained inside of AAS, but inside of submodels
	 */
	@Override
	public Object invokeOperation(String path, Object... parameter) throws ProviderException {
		throw new NotAnInvokableException("An AAS does not contain any operations that can be invoked");
	}
}
