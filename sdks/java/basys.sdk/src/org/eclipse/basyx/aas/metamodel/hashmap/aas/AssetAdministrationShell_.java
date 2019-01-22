package org.eclipse.basyx.aas.metamodel.hashmap.aas;


import java.util.HashMap;
import java.util.HashSet;

import org.eclipse.basyx.aas.api.resources.IElement;
import org.eclipse.basyx.aas.api.resources.ISubModel;
import org.eclipse.basyx.aas.impl.reference.VABURNElementRef;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.Body;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.parts.Header;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.HasTemplate;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identifiable;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Packageable;
import org.eclipse.basyx.vab.core.ref.VABElementRef;



/**
 * AssetAdministrationShell class
 * 
 * FIXME: Add dictionary and securityAttributes
 * 
 * Does not implement IAssetAdministrationShell since there are only references stored in this map
 * 
 * @author kuhn
 *
 */

public class AssetAdministrationShell_ extends HashMap<String, Object> implements IElement { 


	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;

	
	
	/**
	 * Asset Administration Shell header
	 */
	protected Header header = null;

	
	/**
	 * Asset Administration Shell body
	 */
	protected Body body = null;
	

		
	
	/**
	 * Constructor
	 */
	public AssetAdministrationShell_() {
		// Create header and body
		header = new Header();
		body   = new Body();
		
		// Add qualifiers
		putAll(new HasTemplate());
		putAll(new Identifiable());
		putAll(new Packageable());

		// Default values
		put("predecessor", null);
		
		// Header and body
		put("header",  header);
		put("body",    body);
	}
	
	
	
	/**
	 * Get AAS header
	 */
	public Header getHeader() {
		return header;
	}

	
	/**
	 * Get AAS body
	 */
	public Body getBody() {
		return body;
	}
	
	/**
	 * Get Submodels
	 */
	@SuppressWarnings("unchecked")
	public HashSet<VABElementRef> getSubModels(){
		return (HashSet<VABElementRef>) body.get("submodels");
	}
	

	/**
	 * Add a submodel as reference
	 */
	public void addSubModel(ISubModel subModel) {
		System.out.println("adding Submodel "+ subModel.getId());
		
		getSubModels().add(new VABURNElementRef(subModel));
		
		System.out.println("added Submodel "+ getSubModels());
		
	}


	/**
	 * Get AAS Id
	 */
	@Override
	public String getId() {
		return (String) get("idShort");
	}


	/**
	 * Set AAS Id
	 */
	@Override
	public void setId(String id) {
		put("idShort", id);
	}


}
