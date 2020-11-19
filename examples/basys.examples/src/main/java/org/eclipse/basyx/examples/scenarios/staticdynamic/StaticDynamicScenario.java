package org.eclipse.basyx.examples.scenarios.staticdynamic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.aggregator.proxy.AASAggregatorProxy;
import org.eclipse.basyx.aas.aggregator.restapi.AASAggregatorProvider;
import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.descriptor.AASDescriptor;
import org.eclipse.basyx.aas.metamodel.map.descriptor.SubmodelDescriptor;
import org.eclipse.basyx.aas.registration.api.IAASRegistryService;
import org.eclipse.basyx.aas.registration.proxy.AASRegistryProxy;
import org.eclipse.basyx.components.IComponent;
import org.eclipse.basyx.components.aas.AASServerComponent;
import org.eclipse.basyx.components.aas.aasx.AASXPackageManager;
import org.eclipse.basyx.components.configuration.BaSyxContextConfiguration;
import org.eclipse.basyx.components.registry.RegistryComponent;
import org.eclipse.basyx.components.registry.configuration.BaSyxRegistryConfiguration;
import org.eclipse.basyx.components.registry.configuration.RegistryBackend;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.support.bundle.AASBundle;
import org.eclipse.basyx.support.bundle.AASBundleIntegrator;
import org.eclipse.basyx.vab.exception.provider.ResourceAlreadyExistsException;
import org.eclipse.basyx.vab.modelprovider.VABPathTools;
import org.xml.sax.SAXException;

/**
 * Example scenario showcasing how to enrich AAS data provided as AASX with static data <br>
 * For this, a previously defined AASX package is loaded and a static
 * submodel is added to the already defined submodels <br>
 * For a detailed description of the scenario, see:<br>
 * https://wiki.eclipse.org/BaSyx_/_Scenarios_/_Static_Dynamic_Extension 
 * 
 * @author schnicke, conradi
 *
 */
public class StaticDynamicScenario {
	
	public static final String REGISTRY_URL = "http://localhost:4000/registry";
	public static final String SERVER_URL = "http://localhost:4001/aasx/shells";

	public static final String AAS_ID = "smart.festo.com/demo/aas/1/1/454576463545648365874"; 
	public static final String AAS_ID_SHORT = "Festo_3S7PM0CP4BD";
	public static final String AAS_ENDPOINT = SERVER_URL + "shells/smart.festo.com%2Fdemo%2Faas%2F1%2F1%2F454576463545648365874/aas";
	
	private List<IComponent> startedComponents = new ArrayList<>();
	
	
	
	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, URISyntaxException {
		new StaticDynamicScenario();
	}

	public StaticDynamicScenario() throws IOException, ParserConfigurationException, SAXException, URISyntaxException {
		
		// Startup the registry server
		startRegistry();
		
		// Startup the server
		startAASServer();
		
		// Load Bundles from .aasx file
		AASXPackageManager packageManager = new AASXPackageManager("aasx/01_Festo.aasx");
		Set<AASBundle> bundles = packageManager.retrieveAASBundles();
		
		// Create static SubModel
		SubModel sm = new ExampleDynamicSubmodel();

		// Get the correct Bundle from the Set
		AASBundle bundle = findBundle(bundles, AAS_ID_SHORT);
		
		// Add the new SubModel to the Bundle
		bundle.getSubmodels().add(sm);
		
		// Load the new Bundles to the Server
		AASBundleIntegrator.integrate(new AASAggregatorProxy(SERVER_URL), bundles);
		
		// Get a RegistryProxy and register all Objects contained in the Bundles
		AASRegistryProxy proxy = new AASRegistryProxy(REGISTRY_URL);
		registerBundles(bundles, proxy, SERVER_URL);
				
	}
	
	/**
	 * Starts an empty registry at "http://localhost:4000"
	 */
	private void startRegistry() {
		// Load a registry context configuration using a .properties file
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration();
		contextConfig.loadFromResource("RegistryContext.properties");
		BaSyxRegistryConfiguration registryConfig = new BaSyxRegistryConfiguration(RegistryBackend.INMEMORY);
		RegistryComponent registry = new RegistryComponent(contextConfig, registryConfig);
		registry.startComponent();
		startedComponents.add(registry);
	}

	/**
	 * Startup an empty server at "http://localhost:4001/aasx/"
	 */	
	private void startAASServer() {
		// Create a server at port 4001 with the endpoint "/aasx"
		BaSyxContextConfiguration contextConfig = new BaSyxContextConfiguration(4001, "/aasx");
		AASServerComponent aasServer = new AASServerComponent(contextConfig);
		
		// Start the created server
		aasServer.startComponent();
		startedComponents.add(aasServer);
	}
	
	
	/**
	 * Finds the Bundle containing the AAS with the specified IdShort
	 * 
	 * @param bundles the Set of Bundles
	 * @param aasID the Id of the AAS of the wanted Bundle
	 * @return the Bundle containing the AAS with the specified Id or null if it does not exist
	 */
	private AASBundle findBundle(Set<AASBundle> bundles, String aasIdShort) {
		for (AASBundle aasBundle : bundles) {
			if(aasBundle.getAAS().getIdShort().equals(aasIdShort))
				return aasBundle;
		}
		return null;
	}
	
	/**
	 * Registers all AASs and SMs contained in the given Bundles
	 * 
	 * @param bundles the Bundles to be registered
	 * @param registry the registry to be used
	 * @param serverURL the URL of the server, that holds the AASs/SMs
	 */
	private void registerBundles(Set<AASBundle> bundles, IAASRegistryService registry, String serverURL) {
		for(AASBundle bundle: bundles) {
			IAssetAdministrationShell aas = bundle.getAAS();
			String encodedAASId = VABPathTools.encodePathElement(aas.getIdentification().getId());
			String aasEndpoint = VABPathTools.concatenatePaths(serverURL, AASAggregatorProvider.PREFIX, encodedAASId, "aas");
			AASDescriptor aasDescriptor = new AASDescriptor(aas, aasEndpoint);
			try {
				registry.register(aasDescriptor);
			} catch(ResourceAlreadyExistsException e) {
				// Does not matter; AAS was already registered
			}
			
			for(ISubModel sm: bundle.getSubmodels()) {
				String encodedSMId = VABPathTools.encodePathElement(sm.getIdShort());
				String smEndpoint = VABPathTools.concatenatePaths(aasEndpoint, "submodels", encodedSMId);
				SubmodelDescriptor smDescriptor = new SubmodelDescriptor(sm, smEndpoint);
				try {
					registry.register(aas.getIdentification(), smDescriptor);
				} catch(ResourceAlreadyExistsException e) {
					// Does not matter; SM was already registered
				}	
			}
		}
	}
	
	/**
	 * Shuts down all started IComponent servers
	 */
	public void stop() {
		startedComponents.stream().forEach(IComponent::stopComponent);
	}
}
