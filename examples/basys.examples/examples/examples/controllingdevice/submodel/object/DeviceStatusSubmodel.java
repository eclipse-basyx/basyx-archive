package examples.controllingdevice.submodel.object;

import java.util.Map;
import org.eclipse.basyx.aas.api.resources.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.factory.MetaModelElementFactory;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.SubModel;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.property.atomicdataproperty.PropertySingleValued;
import org.eclipse.basyx.aas.metamodel.hashmap.aas.qualifier.Identification;



/**
 * Implement a sub model that describes a device status
 * 
 * @author kuhn
 *
 */
public class DeviceStatusSubmodel extends SubModel {


	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = 1L;



	/**
	 * Constructor
	 */
	@SuppressWarnings("unchecked")
	public DeviceStatusSubmodel() {

		// Meta model element factory
		MetaModelElementFactory factory = new MetaModelElementFactory();

		// Initialize this sub model
		((Map<String, Object>) this.get("identification")).put("id", "urn:de.FHG:devices.es.iese:statusSM:1.0:3:x-509#002");
		((Map<String, Object>) this.get("identification")).put("idType", Identification.URI);

		
		// This sub model only defines two property named "device status" and "mode"
		PropertySingleValued deviceStatusProperty = factory.create(new PropertySingleValued(), "offline");
		deviceStatusProperty.setId("deviceStatus");
		PropertySingleValued deviceModeProperty = factory.create(new PropertySingleValued(), "idle");
		deviceModeProperty.setId("mode");

		// Add properties to sub model properties
		((Map<String, Object>) get("properties")).put("deviceStatus", deviceStatusProperty);
		((Map<String, Object>) get("properties")).put("mode", deviceModeProperty);
	}


	/**
	 * Constructor
	 */
	public DeviceStatusSubmodel(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();

		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
