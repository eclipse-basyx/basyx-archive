package org.eclipse.basyx.regression.support.processengine.submodel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.eclipse.basyx.regression.support.processengine.stubs.ICoilcar;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.operation.Operation;


public class DeviceSubmodelFactory {
	public SubModel create(String id, ICoilcar coilcar) {
		// create a single value property
		Property property1 = new Property(0);
		property1.setIdShort("currentPosition");
		
		Property property2 = new Property(0);
		property2.setIdShort("lifterPosition");
		
		Property property3 = new Property(false);
		property3.setIdShort("physicalSpeed");
		
		// create 2 opertations
		Operation op1 = new Operation((Function<Object[], Object>) obj -> {
			return coilcar.liftTo((int)obj[0]);
		});
		op1.setIdShort("liftTo");
		
		Operation op2 = new Operation((Function<Object[], Object>) obj -> {
			coilcar.moveTo((int)obj[0]);
			return true;
		});
		op2.setIdShort("moveTo");
		
		// create a list for defined operations
		List<Operation> opList = new ArrayList<>();
		opList.add(op1);
		opList.add(op2);
		// create a list for defined properties
		List<Property> propList = new ArrayList<>();
		propList.add(property1);
		propList.add(property2);
		propList.add(property3);
		// create the sub-model and add the property and operations to the sub-model
		SubModel sm = new SubModel(propList, opList);
		sm.setIdentification(new Identifier(IdentifierType.CUSTOM, id + "Custom"));
		sm.setIdShort(id);
		return sm;
	}
}
