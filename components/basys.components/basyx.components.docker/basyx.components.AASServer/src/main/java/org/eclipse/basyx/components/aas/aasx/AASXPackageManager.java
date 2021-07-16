/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.components.aas.aasx;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.basyx.aas.factory.aasx.AASXToMetamodelConverter;


/**
 * @deprecated Renamed and moved to SDK. Please use AASXToMetamodelConverter
 * @author schnicke
 *
 */
@Deprecated
public class AASXPackageManager extends AASXToMetamodelConverter {

	public AASXPackageManager(String path) {
		super(path);
	}

	@Override
	protected Path getRootFolder() throws IOException, URISyntaxException {
		URI uri = AASXPackageManager.class.getProtectionDomain().getCodeSource().getLocation().toURI();
		URI parent = new File(uri).getParentFile().toURI();
		return Paths.get(parent);
	}
}
