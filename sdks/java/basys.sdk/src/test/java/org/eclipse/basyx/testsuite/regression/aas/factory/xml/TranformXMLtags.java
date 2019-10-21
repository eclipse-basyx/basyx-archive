package org.eclipse.basyx.testsuite.regression.aas.factory.xml;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.basyx.aas.factory.xml.TransformAsset;
import org.eclipse.basyx.aas.factory.xml.TransformAssetAdministrationShell;
import org.eclipse.basyx.aas.factory.xml.TransformConceptDescription;
import org.eclipse.basyx.aas.metamodel.map.AssetAdministrationShell;
import org.eclipse.basyx.aas.metamodel.map.parts.Asset;
import org.eclipse.basyx.aas.metamodel.map.parts.ConceptDescription;
import org.eclipse.basyx.submodel.factory.xml.TransformSubmodel;
import org.eclipse.basyx.submodel.metamodel.map.SubModel;
import org.eclipse.basyx.vab.factory.xml.XmlParser;
import org.junit.Before;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xml.sax.SAXException;

/**
 * This is the class which has APIs for converting XML tags to corresponding
 * objects.
 * 
 * @author rajashek
 *
 */
public class TranformXMLtags {
	
	private static Logger logger = LoggerFactory.getLogger(TranformXMLtags.class);
	
	private Map<String, Object> rootObj = new HashMap<>();
	String xmlTestContent;
	
	@Before
	public void TestBuildXmlMap() throws Exception {
		String filePath = "src/test/resources/aas/factory/xml/detail.xml";
		  try
	        {
			  xmlTestContent = new String ( Files.readAllBytes( Paths.get(filePath) ) );
			  rootObj.putAll(XmlParser.buildXmlMap(xmlTestContent));
	        }
	        catch (IOException e)
	        {
	            logger.error("[TEST] Exception in TestBuildXmlMap", e);
	        }
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void populateAssetAdministrationShell() throws ParserConfigurationException, SAXException, IOException {
		HashSet<AssetAdministrationShell> assetSet=new HashSet<AssetAdministrationShell>();
		//get the Asset admin shell info from root and start processing it one by one 
		Object administrationShellObj = TransformAssetAdministrationShell.getAssetAdminShellsFromRootObj(rootObj).get("aas:assetAdministrationShell");
		
		if (administrationShellObj instanceof Collection<?>){
			ArrayList<Object> administrationShellArrayList=(ArrayList<Object>)administrationShellObj;
			for (Object object : administrationShellArrayList) {
				assetSet.add(TransformAssetAdministrationShell.transformAssetAdministrationShell((Map<String, Object>) object));
			}
		}
		else{
			Map<String, Object>  administrationShellMapObj = (Map<String, Object>) administrationShellObj;	
			assetSet.add(TransformAssetAdministrationShell.transformAssetAdministrationShell(administrationShellMapObj));
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void populateAsset() throws ParserConfigurationException, SAXException, IOException {
		HashSet<Asset> assetSet=new HashSet<Asset>();
		Object assetObject = TransformAsset.getAssetFromRootObj(rootObj).get("aas:asset");
		if (assetObject instanceof Collection<?>){
			ArrayList<Object> assetArrayList=(ArrayList<Object>)assetObject;
			for (Object object : assetArrayList) {
				assetSet.add(TransformAsset.transformAsset((Map<String, Object>) object));
			}
		}
		else {
			Map<String, Object>  assetMapObj = (Map<String, Object>) assetObject;
			assetSet.add(TransformAsset.transformAsset(assetMapObj));


			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void populateSubmodel() throws ParserConfigurationException, SAXException, IOException {
		HashSet<SubModel> submodelSet=new HashSet<SubModel>();
		Object submodelObj = TransformSubmodel.getSubmodelFromRootObj(rootObj).get("aas:submodel");
		if (submodelObj instanceof Collection<?>){
			ArrayList<Object> submodelArrayList=(ArrayList<Object>)submodelObj;
			for (Object object : submodelArrayList) {
				submodelSet.add(TransformSubmodel.transformSubmodel((Map<String, Object>) object));
			}
		}
		else {
			Map<String, Object>  subModelMapObj = (Map<String, Object>) submodelObj;
			submodelSet.add(TransformSubmodel.transformSubmodel(subModelMapObj));

		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Test
	public void populateconceptDescription() throws ParserConfigurationException, SAXException, IOException {
		HashSet<ConceptDescription> ConceptDescriptionSet=new HashSet<ConceptDescription>();
		Object conceptDescriptionObj = TransformConceptDescription.getconceptDescriptionFromRootObj(rootObj).get("aas:conceptDescription");
		if(conceptDescriptionObj instanceof Collection<?> ) {
			ArrayList<Object> conceptDescriptionArrayList=(ArrayList<Object>)conceptDescriptionObj;
			for (Object object : conceptDescriptionArrayList) {
				ConceptDescriptionSet.add(TransformConceptDescription.transformConceptDescription((Map<String, Object>) object));
			}
		}else {
			Map<String, Object>  conceptDescriptionMapObj = (Map<String, Object>) conceptDescriptionObj;
			ConceptDescriptionSet.add(TransformConceptDescription.transformConceptDescription(conceptDescriptionMapObj));
		}
		
		
	}
	



	

	


}
