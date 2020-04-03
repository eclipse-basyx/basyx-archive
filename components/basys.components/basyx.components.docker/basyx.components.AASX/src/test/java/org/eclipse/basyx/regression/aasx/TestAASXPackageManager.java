package org.eclipse.basyx.regression.aasx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.metamodel.api.IAssetAdministrationShell;
import org.eclipse.basyx.components.aasx.AASXPackageManager;
import org.eclipse.basyx.submodel.metamodel.api.ISubModel;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.IKey;
import org.eclipse.basyx.submodel.metamodel.api.reference.IReference;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.support.bundle.AASBundle;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * J-Unit tests for AASx package explorer. This test checks the parsing of aas,
 * submodels, assets and concept-descriptions. it also checks whether the aas
 * have correct references to the asets and submodels
 * 
 * @author zhangzai
 *
 */
public class TestAASXPackageManager {
	/**
	 * path to the aasx package
	 */
	private String aasxPath = "aasx/01_Festo.aasx";

	/**
	 * the aasx package converter
	 */
	private AASXPackageManager packageConverter;

	/**
	 * this string array is used to check the refs to submodels of aas
	 */
	private String[] submodelids = { "www.company.com/ids/sm/6053_5072_7091_5102", "smart.festo.com/demo/sm/instance/1/1/13B7CCD9BF7A3F24", "www.company.com/ids/sm/4343_5072_7091_3242", "www.company.com/ids/sm/2543_5072_7091_2660",
			"www.company.com/ids/sm/6563_5072_7091_4267"

	};

	/**
	 * AAS bundle which will be generated by the XMLAASBundleFactory
	 */
	private Set<AASBundle> aasBundles;

	/**
	 * Submodels parsed by the converter
	 */
	private Set<ISubModel> submodels;

	/**
	 * Initialize the AASX package converter
	 */
	@Before
	public void setup() {
		// Create the aasx package converter with the path to the aasx package
		packageConverter = new AASXPackageManager(aasxPath);
	}

	/**
	 * Test parsing of aas, assets, submodels and concept-descriptions
	 */
	@Test
	public void testCheckAasxConverter() {
		// Parse aas from the XML and create the AAS Bundle with refs to submodels
		try {
			aasBundles = packageConverter.retrieveAASBundles();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}

		// check the information in the aas bundles
		checkAASs(aasBundles);

		// Check the submodels
		checkSubmodels(submodels);
	}

	/**
	 * Check the parsed aas with expected ones
	 * 
	 * @param aasList
	 */
	private void checkAASs(Set<AASBundle> aasBundles) {
		assertEquals(2, aasBundles.size());

		IAssetAdministrationShell aas = null;

		// select the AAS with a specific ID from the list
		Optional<AASBundle> testAASBundleOptional = aasBundles.stream().filter(b -> b.getAAS().getIdShort().equals("Festo_3S7PM0CP4BD")).findFirst();
		// verify there exist one aas with this ID
		assertTrue(testAASBundleOptional.isPresent());

		// Get the aasbundle from the filtered results
		AASBundle testAASBundle = testAASBundleOptional.get();
		aas = testAASBundle.getAAS();

		// get submodels of this aas
		submodels = testAASBundle.getSubmodels();

		// verify short id
		assertEquals("Festo_3S7PM0CP4BD", aas.getIdShort());
		assertEquals("CONSTANT", aas.getCategory());

		assertEquals("", aas.getDescription().get("EN"));
		assertEquals("", aas.getDescription().get("DE"));

		// verify id and id-type
		assertEquals("smart.festo.com/demo/aas/1/1/454576463545648365874", aas.getIdentification().getId());
		assertEquals(IdentifierType.IRI, aas.getIdentification().getIdType());


		// Get submodel references
		Collection<IReference> references = aas.getSubmodelReferences();

		// this aas has 5 submodels
		assertEquals(5, references.size());
		List<IReference> referencelist = new ArrayList<>();
		referencelist.addAll(references);

		// sort the list for later assertion
		// list is sorted by the last two characters of the id
		referencelist.sort((x, y) -> {
			String idx = x.getKeys().get(0).getValue();
			String idy = y.getKeys().get(0).getValue();

			String idx_end = idx.substring(idx.length() - 2);
			int idxint = Integer.parseInt(idx_end);
			String idy_end = idy.substring(idy.length() - 2);
			int idyint = Integer.parseInt(idy_end);

			return idxint - idyint;

		});

		// get First submodel reference
		for (int i = 0; i < referencelist.size(); i++) {
			IReference ref = referencelist.get(i);
			List<IKey> refKeys = ref.getKeys();

			// assert the submodel id
			assertEquals(submodelids[i], refKeys.get(0).getValue());
			// assert the id type
			assertEquals("IRI", refKeys.get(0).getIdType().name());
			// assert the model type
			assertEquals("SUBMODEL", refKeys.get(0).getType().name());
			// submodels are local
			assertEquals(true, refKeys.get(0).isLocal());
		}

	}

	/**
	 * Check parsed submodels with expected ones
	 * 
	 * @param submodels
	 */
	private void checkSubmodels(Set<ISubModel> submodels) {
		assertEquals(5, submodels.size());

		// filter the submodel with id "Nameplate"
		Optional<ISubModel> sm1Optional = submodels.stream().filter(s -> s.getIdShort().equals("Nameplate")).findFirst();
		assertTrue(sm1Optional.isPresent());
		ISubModel sm1 = sm1Optional.get();

		// verify short id, id-type, id and model-kind of the submodel
		assertEquals("Nameplate", sm1.getIdShort());
		assertEquals("IRI", sm1.getIdentification().getIdType().name());
		assertEquals("www.company.com/ids/sm/4343_5072_7091_3242", sm1.getIdentification().getId());
		assertEquals("Instance", sm1.getModelingKind().toString());

		// ---------------------------------------------
		// get 1st submodel element
		// Get submodel elements
		Map<String, ISubmodelElement> smElements = sm1.getSubmodelElements();

		// get element manufacturing name
		ISubmodelElement sele = smElements.get("ManufacturerName");

		// verify short id
		assertEquals("ManufacturerName", sele.getIdShort());

		// verify category and model-kind, value and value-type
		assertEquals("PARAMETER", sele.getCategory());
		assertTrue(sele.getModelingKind().name().equalsIgnoreCase("Instance"));
		Property prop = (Property) sele;
		assertEquals("Festo AG & Co. KG", prop.get());
		assertEquals("string", prop.getValueType());

		// get semantic id
		IReference semantic = sele.getSemanticId();

		IKey semanticKey = semantic.getKeys().get(0);
		assertTrue(semanticKey.getType().name().equalsIgnoreCase("ConceptDescription"));
		assertEquals("IRDI", semanticKey.getIdType().name());
		assertEquals("0173-1#02-AAO677#002", semanticKey.getValue());
		assertEquals(true, semanticKey.isLocal());

		/// ---------------------------------------------
		// get 2nd submodel element
		// Get submodel elements
		sele = smElements.get("ManufacturerProductDesignation");
		assertEquals("ManufacturerProductDesignation", sele.getIdShort());
		assertEquals("PARAMETER", sele.getCategory());
		assertTrue(sele.getModelingKind().name().equalsIgnoreCase("Instance"));
		prop = (Property) sele;
		assertEquals("OVEL Vacuum generator", prop.get());
		assertEquals("string", prop.getValueType());

		// get semantic id
		semantic = sele.getSemanticId();
		semanticKey = semantic.getKeys().get(0);
		assertTrue(semanticKey.getType().name().equalsIgnoreCase("ConceptDescription"));
		assertEquals("IRDI", semanticKey.getIdType().name());
		assertEquals("0173-1#02-AAW338#001", semanticKey.getValue());
		assertEquals(true, semanticKey.isLocal());

		// ---------------------------------------------
		// get 3rd submodel element
		// Get submodel elements
		sele = smElements.get("PhysicalAddress");
		assertEquals("PhysicalAddress", sele.getIdShort());
		assertEquals("PARAMETER", sele.getCategory());
		assertTrue(sele.getModelingKind().name().equalsIgnoreCase("Instance"));

		// get semantic id
		semantic = sele.getSemanticId();
		semanticKey = semantic.getKeys().get(0);
		assertTrue(semanticKey.getType().name().equalsIgnoreCase("ConceptDescription"));
		assertEquals("IRI", semanticKey.getIdType().name());
		assertEquals("https://www.hsu-hh.de/aut/aas/physicaladdress", semanticKey.getValue());
		assertEquals(true, semanticKey.isLocal());

		// get values
		assertTrue(sele.getModelType().equalsIgnoreCase("SubmodelElementCollection"));
		SubmodelElementCollection collection = (SubmodelElementCollection) sele;
		Collection<ISubmodelElement> subelements = collection.getValue();

		assertEquals(5, subelements.size());
		Iterator<ISubmodelElement> iterator = subelements.iterator();
		Property prop1 = (Property) (iterator.next());
		assertEquals("CountryCode", prop1.getIdShort());
		assertEquals("DE", prop1.get());

		Property prop2 = (Property) (iterator.next());
		assertEquals("Street", prop2.getIdShort());
		assertEquals("Ruiter Straße 82", prop2.get());
	}

}