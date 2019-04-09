package examples.controllingdevice.vab.dynamic;

import java.util.HashMap;
import java.util.Map;



/**
 * A device status VAB object that does not conform to the sub model meta model. 
 * 
 * @author kuhn
 *
 */
public class DeviceStatusVABObject extends HashMap<String, Object> {

	
	/**
	 * Version of serialized instances
	 */
	private static final long serialVersionUID = -1339664424451243526L;

	
	/**
	 * Constructor
	 */
	public DeviceStatusVABObject() {
		
		// Create contained property map
		Map<String, Object> propertyMap = new HashMap<>();
		// - Create device status and mode property
		propertyMap.put("deviceStatus", new String("offline"));
		propertyMap.put("mode", new String("idle"));

		// Put properties into 'elements' map of this provider
		put("properties", propertyMap);
	}
}
