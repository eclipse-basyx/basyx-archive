package org.eclipse.basyx.dashboard;

import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.vab.modelprovider.lambda.VABLambdaProviderHelper;

/**
 * Dummy Submodel with two dynamic values. A configurable temperature value that randomly
 * returns values in a specific range. And a random boolean value.
 * 
 * @author espen
 *
 */
public class DashboardSubmodel extends SubModel {
	private int minValue;
	private int maxValue;

	public DashboardSubmodel() {
		setIdShort("DashboardSubmodel");
		setIdentification(IdentifierType.CUSTOM, "DashboardTemperatureSubmodel");
		setSemanticId(new Reference(
				new Key(KeyElements.CONCEPTDESCRIPTION, true, "0112/2///61360_4#AAF891#001", KeyType.IRDI)));
		setTemperatureProperty();
		setDummyProperty();

		DashboardSubmodelConfiguration config = new DashboardSubmodelConfiguration();
		config.loadFromDefaultSource();
		minValue = config.getMin();
		maxValue = config.getMax();
	}

	private void setTemperatureProperty() {
		Property temperatureProperty = new Property();
		temperatureProperty.setIdShort("temperature");
		temperatureProperty.set(VABLambdaProviderHelper.createSimple(() -> {
			return Math.random() * (maxValue - minValue) + minValue;
		}, null), PropertyValueTypeDef.Double);
		// Adds a reference to a semantic ID to specify the property semantics (see CDD)
		// Ref by identifier:
		Key key = new Key(KeyElements.CONCEPTDESCRIPTION, true, "0112/2///61360_4#AAF891#001", KeyType.IRDI);
		IReference refByIdentifier = new Reference(key);
		temperatureProperty.setSemanticID(refByIdentifier);
		addSubModelElement(temperatureProperty);
	}

	private void setDummyProperty() {
		Property dummyProperty = new Property();
		dummyProperty.setIdShort("dummy");
		dummyProperty.set(VABLambdaProviderHelper.createSimple(() -> {
			return (Math.random() > 0.5);
		}, null), PropertyValueTypeDef.Boolean);
		addSubModelElement(dummyProperty);
	}
}
