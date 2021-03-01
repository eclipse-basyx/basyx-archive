/*******************************************************************************
 * Copyright (C) 2021 the Eclipse BaSyx Authors
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.basyx.wrapper.provider.aas;

import java.util.Collection;

import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.restapi.AASModelProvider;
import org.eclipse.basyx.aas.restapi.VABMultiSubmodelProvider;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.submodel.metamodel.map.identifier.Identifier;
import org.eclipse.basyx.submodel.restapi.SubModelProvider;
import org.eclipse.basyx.vab.exception.provider.MalformedRequestException;
import org.eclipse.basyx.wrapper.provider.IWrapperProvider;
import org.eclipse.basyx.wrapper.receiver.IPropertyWrapperService;
import org.eclipse.basyx.wrapper.receiver.configuration.PropertyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AAS Provider for the wrapper that exposes the GET-Part of the AAS interface
 * 
 * @author espen
 *
 */
public class AASWrapperProvider implements IWrapperProvider {
	private static final Logger logger = LoggerFactory.getLogger(AASWrapperProvider.class);
	public static final String TYPE = "AAS";
	
	private final String host;
	private final int port;

	private VABMultiSubmodelProvider provider;

	private String providerPath = "/aas";

	/**
	 * Standard constructor for the AASWrapperProvider. Needs to know the wrapper endpoint to be able to register the
	 * created AAS interface
	 * 
	 * @param host Host of the wrapper
	 * @param port Port for the wrapper
	 */
	public AASWrapperProvider(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void initialize(IPropertyWrapperService wrapperService, IAASRegistryService registry,
			Collection<PropertyConfiguration> configs) {
		logger.info("Initializing provider '" + this.getClass().getSimpleName() + "' on path " + this.getProviderPath());

		// Create the VABMultiSubmodelProvider
		AssetAdministrationShell aas = new WrapperAssetAdministrationShell();
		Asset asset = new WrapperAsset();
		aas.setAsset(asset);
		SubModel sm = new WrapperSubmodel("Wrapper", new Identifier(IdentifierType.CUSTOM, "WrapperSubmodel"),
				wrapperService,
				configs);
		aas.addSubModel(sm);

		provider = new VABMultiSubmodelProvider(new AASModelProvider(aas));
		provider.addSubmodel(new SubModelProvider(sm));

		// Register the aas
		AASDescriptor desc = createDescriptor(aas, sm);
		registerAAS(registry, desc);
	}

	private AASDescriptor createDescriptor(AssetAdministrationShell aas, SubModel... submodels) {
		AASDescriptor descriptor = new AASDescriptor(aas, "http://" + host + ":" + port + "/aas");
		for (SubModel sm : submodels) {
			descriptor.addSubmodelDescriptor(
					new SubmodelDescriptor(sm,
							"http://" + host + ":" + port + "/aas/submodels/" + sm.getIdShort() + "/"));
		}
		return descriptor;
	}

	private static void registerAAS(IAASRegistryService registry, AASDescriptor descriptor) {
		// Quick & dirty, try to register until registry is up
		for (int i = 0; i < 10; i++) {
			try {
				registry.register(descriptor);
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					logger.warn("Interrupted", e1);
					Thread.currentThread().interrupt();
				}
			}
		}
	}

	@Override
	public String getProviderPath() {
		return providerPath;
	}

	@Override
	public void setProviderPath(String path) {
		this.providerPath = path;
	}

	@Override
	public Object post(String path, Object data) {
		throw new MalformedRequestException("Creating elements is not supported");
	}

	/**
	 * Only expose the get-Part of the HTTP-REST Interface of the AAS
	 */
	@Override
	public Object get(String path) {
		return provider.getModelPropertyValue(path);
	}

	@Override
	public Object delete(String path) {
		throw new MalformedRequestException("Deleting elements is not supported");
	}

	@Override
	public Object put(String path, Object data) {
		throw new MalformedRequestException("Setting elements is not supported");
	}

	@Override
	public Object patch(String path, Object data) {
		throw new MalformedRequestException("Patching elements is not supported");
	}
}