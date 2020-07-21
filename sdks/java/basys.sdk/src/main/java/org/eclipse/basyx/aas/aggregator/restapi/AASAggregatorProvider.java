package org.eclipse.basyx.aas.aggregator.restapi;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IIdentifier;
import org.eclipse.basyx.submodel.metamodel.map.modeltype.ModelType;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.vab.exception.provider.ProviderException;
import org.eclipse.basyx.vab.exception.provider.ResourceAlreadyExistsException;
import org.eclipse.basyx.vab.exception.provider.ResourceNotFoundException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.eclipse.basyx.vab.modelprovider.api.IModelProvider;

/**
 * Connects an IAASAggregator to the VAB
 * 
 * @author conradi
 *
 */
public class AASAggregatorProvider implements IModelProvider {
	
	private AASAggregator aggregator;
	
	private static final String PREFIX = "aasList";
	private static final String ENCODING_SCHEME = "UTF-8";
	
	public AASAggregatorProvider(AASAggregator aggregator) {
		this.aggregator = aggregator;
	}

	/**
	 * Check for correctness of path and returns a stripped path (i.e. no leading
	 * prefix)
	 * @param path
	 * @return
	 * @throws MalformedRequestException 
	 */
	private String stripPrefix(String path) throws MalformedRequestException {
		path = VABPathTools.stripSlashes(path);
		if (!path.startsWith(PREFIX)) {
			throw new MalformedRequestException("Path " + path + " not recognized as aggregator path. Has to start with " + PREFIX);
		}
		path = path.replace(PREFIX, "");
		path = VABPathTools.stripSlashes(path);
		return path;
	}
	
	/**
	 * Makes sure, that given Object is an AAS by checking its ModelType<br />
	 * Creates a new AAS with the content of the given Map
	 * 
	 * @param value the AAS Map object
	 * @return an AAS
	 * @throws MalformedRequestException 
	 */
	@SuppressWarnings("unchecked")
	private AssetAdministrationShell createAASFromMap(Object value) throws MalformedRequestException {
		
		//check if the given value is a Map
		if(!(value instanceof Map)) {
			throw new MalformedRequestException("Given newValue is not a Map");
		}

		Map<String, Object> map = (Map<String, Object>) value;
		
		//check if the given Map contains an AAS
		String type = ModelType.createAsFacade(map).getName();
		
		//have to accept Objects without modeltype information,
		//as modeltype is not part of the public metamodel
		if(!AssetAdministrationShell.MODELTYPE.equals(type) && type != null) {
			throw new MalformedRequestException("Given newValue map has not the correct ModelType");
		}
		
		AssetAdministrationShell aas = AssetAdministrationShell.createAsFacade(map);
		
		return aas;
	}
	
	
	
	@Override
	public Object getModelPropertyValue(String path) throws ProviderException {
		path = stripPrefix(path);
		
		if(path.isEmpty()) { //Return all AAS if path is empty
			return aggregator.getAASList();
		} else {
			String[] splitted = VABPathTools.splitPath(path);
			if (splitted.length == 1) { // A specific AAS was requested
				String id = decodePath(splitted[0]);
				IAssetAdministrationShell aas = aggregator.getAAS(new ModelUrn(id));
				return aas;
			} else {
				String id = decodePath(splitted[0]);
				String restPath = VABPathTools.skipEntries(path, 1);
				return aggregator.getProviderForAASId(id).getModelPropertyValue(restPath);
			}
		}
	}

	@Override
	public void setModelPropertyValue(String path, Object newValue) throws ProviderException {
		path = stripPrefix(path);
		
		if (!path.isEmpty()) { // Overwriting existing entry
			if (!path.contains("/")) { // Update of AAS

				AssetAdministrationShell aas = createAASFromMap(newValue);
				// Decode encoded path
				path = decodePath(path);
				ModelUrn identifier = new ModelUrn(path);

				if (!aas.getIdentification().getId().equals(path)) {
					throw new MalformedRequestException("Given aasID and given AAS do not match");
				}

				if (aggregator.getAAS(identifier) == null) {
					throw new ResourceAlreadyExistsException("Can not update non existing value '" + path + "'. Try create instead.");
				}

				aggregator.updateAAS(aas);
			} else { // Update of contained element
				String id = decodePath(VABPathTools.getEntry(path, 0));
				String restPath = VABPathTools.skipEntries(path, 1);
				aggregator.getProviderForAASId(id).setModelPropertyValue(restPath, newValue);
			}
		} else {
			throw new MalformedRequestException("Set with empty path is not supported by aggregator");
		}
	}

	@Override
	public void createValue(String path, Object newEntity) throws ProviderException {
		path = stripPrefix(path);
		
		if (path.isEmpty()) { // Creating new entry
			AssetAdministrationShell aas = createAASFromMap(newEntity);
			try {
				aggregator.getAAS(aas.getIdentification());
				throw new ResourceAlreadyExistsException("AAS with path (id) " + path + " exists already. Try update instead");
			} catch (ResourceNotFoundException e) {
				aggregator.createAAS(aas);
			}
			
		} else {
			String id = decodePath(VABPathTools.getEntry(path, 0));
			String restPath = VABPathTools.skipEntries(path, 1);
			aggregator.getProviderForAASId(id).createValue(restPath, newEntity);
		}
		
	}

	@Override
	public void deleteValue(String path) throws ProviderException {
		path = stripPrefix(path);

		if (!path.isEmpty()) { // Deleting an entry
			// Decode encoded path
			path = decodePath(path);
			
			if (!path.contains("/")) {
				IIdentifier identifier = new ModelUrn(path);

				if (aggregator.getAAS(identifier) == null) {
					throw new ResourceNotFoundException("Value '" + path + "' to be deleted does not exists.");
				}

				aggregator.deleteAAS(identifier);
			} else {
				String id = decodePath(VABPathTools.getEntry(path, 0));
				String restPath = VABPathTools.skipEntries(path, 1);
				aggregator.getProviderForAASId(id).deleteValue(restPath);
			}
		} else {
			throw new MalformedRequestException("Delete with empty path is not supported by registry");
		}
	}

	/**
	 * Decodes the given path using the default encoding scheme
	 * 
	 * @param path
	 * @return
	 */
	private String decodePath(String path) {
		try {
			path = URLDecoder.decode(path, ENCODING_SCHEME);
		} catch (UnsupportedEncodingException e) {
			// This should never happen
			throw new RuntimeException("Wrong encoding for " + path);
		}
		return path;
	}

	@Override
	public void deleteValue(String path, Object obj) throws ProviderException {
		throw new MalformedRequestException("DeleteValue with parameter not supported by aggregator");
	}

	@Override
	public Object invokeOperation(String path, Object... parameter) throws ProviderException {
		path = stripPrefix(path);
		String id = decodePath(VABPathTools.getEntry(path, 0));
		String restPath = VABPathTools.skipEntries(path, 1);
		return aggregator.getProviderForAASId(id).invokeOperation(restPath, parameter);
	}
	

}
