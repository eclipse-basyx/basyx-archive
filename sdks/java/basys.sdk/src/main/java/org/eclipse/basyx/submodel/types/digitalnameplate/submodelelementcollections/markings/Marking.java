package org.eclipse.basyx.submodel.types.digitalnameplate.submodelelementcollections.markings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.basyx.aas.metamodel.exception.MetamodelConstructionException;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.ISubmodelElement;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IFile;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.facade.SubmodelElementMapCollectionConverter;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.File;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.types.digitalnameplate.helper.DigitalNameplateSubmodelHelper;

/**
 * Marking as defined in the AAS Digital Nameplate Template document <br/>
 * It is a submodel element collection which
 * contains information about the marking labelled on the device
 * 
 * @author haque
 *
 */
public class Marking extends SubmodelElementCollection {
	public static final String MARKINGNAMEID = "MarkingName";
	public static final String MARKINGFILEID = "MarkingFile";
	public static final String MARKINGADDITIONALTEXTPREFIX = "MarkingAdditionalText";
	public static final Reference SEMANTICID = new Reference(new Key(KeyElements.CONCEPTDESCRIPTION, false, "https://admin-shell.io/zvei/nameplate/0/1/Nameplate/Markings/Marking", KeyType.IRI));
	
	private Marking() {
	}
	
	/**
	 * Constructor with mandatory attributes
	 * 
	 * @param idShort
	 * @param markingName
	 * @param markingFile
	 */
	public Marking(String idShort, Property markingName, File markingFile) {
		super(idShort);
		setSemanticID(SEMANTICID);
		setMarkingAdditionalText(new ArrayList<Property>());
		setMarkingName(markingName);
		setMarkingFile(markingFile);
	}
	
	/**
	 * Constructor with mandatory attributes
	 * 
	 * @param idShort
	 * @param markingName
	 * @param markingFile
	 */
	public Marking(String idShort, String markingName, File markingFile) {
		super(idShort);
		setSemanticID(SEMANTICID);
		setMarkingAdditionalText(new ArrayList<Property>());
		setMarkingName(markingName);
		setMarkingFile(markingFile);
	}
	
	/**
	 * Creates a Marking SMC object from a map
	 * 
	 * @param obj a Marking SMC object as raw map
	 * @return a Marking SMC object, that behaves like a facade for the given map
	 */
	public static Marking createAsFacade(Map<String, Object> obj) {
		if (obj == null) {
			return null;
		}
		
		if (!isValid(obj)) {
			throw new MetamodelConstructionException(Marking.class, obj);
		}
		
		Marking marking = new Marking();
		marking.setMap((Map<String, Object>)SubmodelElementMapCollectionConverter.mapToSmECollection(obj));
		return marking;
	}
	
	/**
	 * Creates a Marking SMC object from a map without validation
	 * 
	 * @param obj a Marking SMC object as raw map
	 * @return a Marking SMC object, that behaves like a facade for the given map
	 */
	private static Marking createAsFacadeNonStrict(Map<String, Object> obj) {
		if (obj == null) {
			return null;
		}
		
		Marking marking = new Marking();
		marking.setMap((Map<String, Object>)SubmodelElementMapCollectionConverter.mapToSmECollection(obj));
		return marking;
	}
	
	/**
	 * Check whether all mandatory elements for Marking SMC
	 * exist in the map
	 * 
	 * @param obj
	 * 
	 * @return true/false
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValid(Map<String, Object> obj) {
		Marking marking = createAsFacadeNonStrict(obj);
		return SubmodelElementCollection.isValid(obj)
				&& Property.isValid((Map<String, Object>) marking.getMarkingName())
				&& File.isValid((Map<String, Object>) marking.getMarkingFile());
	}
	
	/**
	 * sets common name of the marking
	 * 
	 * Note: CE marking is declared as mandatory according to EU
     * Machine Directive 2006/42/EC.
	 * @param markingName
	 */
	public void setMarkingName(Property markingName) {
		addSubModelElement(markingName);
	}
	
	/**
	 * sets common name of the marking
	 * 
	 * Note: CE marking is declared as mandatory according to EU
     * Machine Directive 2006/42/EC.
	 * @param markingName
	 */
	public void setMarkingName(String markingName) {
		Property markingNameProp = new Property(MARKINGNAMEID, PropertyValueTypeDef.String);
		markingNameProp.setSemanticID(new Reference(new Key(KeyElements.CONCEPTDESCRIPTION, false, "https://admin-shell.io/zvei/nameplate/1/0/Nameplate/Markings/Marking/MarkingName", IdentifierType.IRDI)));
		markingNameProp.set(markingName);
		setMarkingName(markingNameProp);
	}
	
	/**
	 * gets common name of the marking
	 * 
	 * Note: CE marking is declared as mandatory according to EU
     * Machine Directive 2006/42/EC.
	 * @return
	 */
	public IProperty getMarkingName() {
		return (IProperty) getSubmodelElement(MARKINGNAMEID);
	}
	
	/**
	 * sets picture of the marking
     * 
     * Note: CE marking is declared as mandatory according to EU
     * Machine Directive 2006/42/EC.
	 * @param markingFile
	 */
	public void setMarkingFile(File markingFile) {
		addSubModelElement(markingFile);
	}
	
	/**
	 * gets picture of the marking
     * 
     * Note: CE marking is declared as mandatory according to EU
     * Machine Directive 2006/42/EC.
	 * @return
	 */
	public IFile getMarkingFile() {
		return (IFile) getSubmodelElement(MARKINGFILEID);
	}
	
	/**
	 * sets where applicable, additional information on the marking in
     * plain text
	 * @param markingAdditionalText
	 */
	public void setMarkingAdditionalText(List<Property> markingAdditionalText) {
		if (markingAdditionalText != null && markingAdditionalText.size() > 0) {
			for (Property markingAdditionalSingle : markingAdditionalText) {
				addSubModelElement(markingAdditionalSingle);
			}
		}
	}
	
	/**
	 * gets where applicable, additional information on the marking in
     * plain text
	 * @return
	 */
	public List<IProperty> getMarkingAdditionalText() {
		List<IProperty> ret = new ArrayList<IProperty>();
		List<ISubmodelElement> elements = DigitalNameplateSubmodelHelper.getSubmodelElementsByIdPrefix(MARKINGADDITIONALTEXTPREFIX, getSubmodelElements());
		
		for (ISubmodelElement element: elements) {
			ret.add((IProperty) element);
		}
		return ret;
	}
}
