package org.eclipse.basyx.testsuite.support.gateways.stubs.servlets.topo.manufacturing;

import org.eclipse.basyx.aas.impl.resources.basic._AssetAdministrationShell;
import org.eclipse.basyx.aas.impl.resources.basic._ReferencedSubModel;

/**
 * Implement an Asset Administration Shell
 * 
 * @author kuhn
 *
 */
public class AAS_Line2Int_Device10 extends _AssetAdministrationShell {

	/**
	 * Constructor
	 */
	public AAS_Line2Int_Device10() {
		// Create Asset Administration Shell
		setId("device10");

		// Register sub models
		// - Description sub model with device descriptions
		_ReferencedSubModel descriptionSM = new _ReferencedSubModel();
		descriptionSM.setId("description");
		// descriptionSM.setTypeDefinition("smType");
		this.addSubModel(descriptionSM);

		// - Status sub model with device status
		_ReferencedSubModel statusSM = new _ReferencedSubModel();
		statusSM.setId("status");
		// descriptionSM.setTypeDefinition("smType");
		this.addSubModel(statusSM);
	}
}
