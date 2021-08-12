/*******************************************************************************
* Copyright (C) 2021 the Eclipse BaSyx Authors
* 
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/

* 
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/

package org.eclipse.basyx.extensions.aas.aggregator.aasxupload;

import java.io.InputStream;
import java.util.Set;

import org.eclipse.basyx.aas.aggregator.AASAggregator;
import org.eclipse.basyx.aas.bundle.AASBundle;
import org.eclipse.basyx.aas.bundle.AASBundleHelper;
import org.eclipse.basyx.aas.factory.aasx.AASXToMetamodelConverter;
import org.eclipse.basyx.extensions.aas.aggregator.aasxupload.api.IAASAggregatorAASXUpload;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;

/**
 * An implementation of the IAASAggregatorAASXUpload interface using maps internally
 * with the support of AASX upload via {@link InputStream}
 * 
 * @author haque
 *
 */
public class AASAggregatorAASXUpload extends AASAggregator implements IAASAggregatorAASXUpload {

    @Override
    public void uploadAASX(InputStream aasxStream) {
        try {
            AASXToMetamodelConverter converter = new AASXToMetamodelConverter(aasxStream);
            Set<AASBundle> bundles = converter.retrieveAASBundles();
            AASBundleHelper.integrate(this, bundles);
        } catch (Exception e) {
            throw new MalformedRequestException("invalid request to aasx path without valid aasx input stream");
        }
    }
}
