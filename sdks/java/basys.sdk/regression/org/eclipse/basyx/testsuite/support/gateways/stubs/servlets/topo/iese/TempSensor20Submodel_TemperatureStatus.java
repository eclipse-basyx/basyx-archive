package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.iese;

import org.eclipse.basyx.aas.api.annotation.AASProperty;
import org.eclipse.basyx.aas.api.resources.basic.IAssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic.AssetKind;
import org.eclipse.basyx.aas.impl.resources.basic.DataType;
import org.eclipse.basyx.aas.impl.resources.basic.Property;
import org.eclipse.basyx.aas.impl.resources.basic.SubModel;



/**
 * Implement a submodel for a temperature sensor
 * 
 * @author kuhn
 *
 */
public class TempSensor20Submodel_TemperatureStatus extends SubModel {


	/**
	 * Sub model property "currentTemperature"
	 */
	@AASProperty public int currentTemperature = -17;

	
	
	/**
	 * Constructor
	 */
	public TempSensor20Submodel_TemperatureStatus() {
		// Initialize this submodel
	    this.setAssetKind(AssetKind.INSTANCE);
	    this.setName("example temperature sensor AAS");
	    this.setId("temperatureStatusSM");
	    this.setTypeDefinition("smType");

	    // Initialize dummy AAS
	    AssetAdministrationShell aas = new AssetAdministrationShell();
	    aas.setId("tempsensor20");
	    aas.setName("tempsensor20");
	    this.setParent(aas);

		
		// Create and add properties
		Property property1 = new Property();
		property1.setName("currentTemperature");
		property1.setId("currentTemperature");
		property1.setDataType(DataType.INTEGER);
		this.addProperty(property1);
	}


	/**
	 * Constructor
	 */
	public TempSensor20Submodel_TemperatureStatus(IAssetAdministrationShell aas) {
		// Invoke default constructor
		this();
		
		// Add sub model to AAS
		aas.addSubModel(this);
	}
}
