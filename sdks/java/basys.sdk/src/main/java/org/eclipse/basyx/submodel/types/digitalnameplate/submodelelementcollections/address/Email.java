package org.eclipse.basyx.submodel.types.digitalnameplate.submodelelementcollections.address;

import java.util.Map;

import org.eclipse.basyx.aas.metamodel.exception.MetamodelConstructionException;
import org.eclipse.basyx.submodel.metamodel.api.identifier.IdentifierType;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyElements;
import org.eclipse.basyx.submodel.metamodel.api.reference.enums.KeyType;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IMultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.api.submodelelement.dataelement.IProperty;
import org.eclipse.basyx.submodel.metamodel.facade.SubmodelElementMapCollectionConverter;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangString;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.reference.Key;
import org.eclipse.basyx.submodel.metamodel.map.reference.Reference;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.MultiLanguageProperty;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.valuetypedef.PropertyValueTypeDef;
import org.eclipse.basyx.submodel.types.digitalnameplate.enums.MailType;

/**
 * Email as defined in the AAS Digital Nameplate Template document <br/>
 * It is a submodel element collection which contains email address and encryption method
 * 
 * @author haque
 *
 */
public class Email extends SubmodelElementCollection {
	public static final String EMAILADDRESSID = "EmailAddress";
	public static final String PUBLICKEYID = "PublicKey";
	public static final String TYPEOFEMAILADDRESSID = "TypeOfEmailAddress";
	public static final String TYPEOFPUBLICKEYID = "TypeOfPublickKey";
	public static final Reference SEMANTICID = new Reference(new Key(KeyElements.CONCEPTDESCRIPTION, false, "0173-1#02-AAQ836#005", KeyType.IRDI));
	
	private Email() {
	}
	
	/**
	 * Constructor with mandatory attributes
	 * @param idShort
	 * @param emailAddress
	 */
	public Email(String idShort, Property emailAddress) {
		super(idShort);
		setSemanticID(SEMANTICID);
		setEmailAddress(emailAddress);
	}
	
	/**
	 * Constructor with mandatory attributes
	 * @param idShort
	 * @param emailAddress
	 */
	public Email(String idShort, String emailAddress) {
		super(idShort);
		setSemanticID(SEMANTICID);
		setEmailAddress(emailAddress);
	}
	
	/**
	 * Creates a Email SMC object from a map
	 * 
	 * @param obj a Email SMC object as raw map
	 * @return a Email SMC object, that behaves like a facade for the given map
	 */
	public static Email createAsFacade(Map<String, Object> obj) {
		if (obj == null) {
			return null;
		}
		
		if (!isValid(obj)) {
			throw new MetamodelConstructionException(Email.class, obj);
		}
		
		Email email = new Email();
		email.setMap((Map<String, Object>)SubmodelElementMapCollectionConverter.mapToSmECollection(obj));
		return email;
	}
	
	/**
	 * Creates a Email SMC object from a map without validation
	 * 
	 * @param obj a Email SMC object as raw map
	 * @return a Email SMC object, that behaves like a facade for the given map
	 */
	private static Email createAsFacadeNonStrict(Map<String, Object> obj) {
		if (obj == null) {
			return null;
		}
		
		Email email = new Email();
		email.setMap((Map<String, Object>)SubmodelElementMapCollectionConverter.mapToSmECollection(obj));
		return email;
	}
	
	/**
	 * Check whether all mandatory elements for Email SMC
	 * exist in the map
	 * 
	 * @param obj
	 * 
	 * @return true/false
	 */
	public static boolean isValid(Map<String, Object> obj) {
		return SubmodelElementCollection.isValid(obj)
				&& createAsFacadeNonStrict(obj).getEmailAddress() != null;
	}
	
	/**
	 * sets electronic mail address of a business partner
	 * @param emailAddress Property
	 */
	public void setEmailAddress(Property emailAddress) {
		addSubModelElement(emailAddress);
	}
	
	/**
	 * sets electronic mail address of a business partner
	 * @param emailAddress String
	 */
	public void setEmailAddress(String emailAddress) {
		Property emailProp = new Property(EMAILADDRESSID, PropertyValueTypeDef.String);
		emailProp.setSemanticID(new Reference(new Key(KeyElements.CONCEPTDESCRIPTION, false, "0173-1#02-AAO198#002", IdentifierType.IRDI)));
		emailProp.set(emailAddress);
		setEmailAddress(emailProp);
	}
	
	/**
	 * gets electronic mail address of a business partner
	 * @return
	 */
	public IProperty getEmailAddress() {
		return (IProperty) getSubmodelElement(EMAILADDRESSID);
	}
	
	/**
	 * sets public part of an unsymmetrical key pair to sign or encrypt text or messages
	 * @param key {@link MultiLanguageProperty}
	 */
	public void setPublicKey(MultiLanguageProperty key) {
		addSubModelElement(key);
	}
	
	/**
	 * sets public part of an unsymmetrical key pair to sign or encrypt text or messages
	 * @param key {@link LangString}
	 */
	public void setPublicKey(LangString key) {
		MultiLanguageProperty publicKey = new MultiLanguageProperty(PUBLICKEYID);
		publicKey.setSemanticID(new Reference(new Key(KeyElements.CONCEPTDESCRIPTION, false, "0173-1#02-AAO200#002", IdentifierType.IRDI)));
		publicKey.setValue(new LangStrings(key));
		setPublicKey(publicKey);
	}
	
	/**
	 * gets public part of an unsymmetrical key pair to sign or encrypt text or messages
	 * @return
	 */
	public IMultiLanguageProperty getPublicKey() {
		return (IMultiLanguageProperty) getSubmodelElement(PUBLICKEYID);
	}
	
	/**
	 * sets characterization of an e-mail address according to its location or usage
	 * enumeration
	 * @param type {@link Property}
	 */
	public void setTypeOfEmailAddress(Property type) {
		addSubModelElement(type);
	}
	
	/**
	 * sets characterization of an e-mail address according to its location or usage
	 * enumeration
	 * @param type {@link MailType}
	 */
	public void setTypeOfEmailAddress(MailType type) {
		Property mailTypeProp = new Property(TYPEOFEMAILADDRESSID, PropertyValueTypeDef.String);
		mailTypeProp.setSemanticID(new Reference(new Key(KeyElements.CONCEPTDESCRIPTION, false, "0173-1#02-AAO199#003", IdentifierType.IRDI)));
		mailTypeProp.set(type.toString());
		setTypeOfEmailAddress(mailTypeProp);
	}
	
	/**
	 * gets characterization of an e-mail address according to its location or usage
	 * enumeration
	 * @return
	 */
	public IProperty getTypeOfEmailAddress() {
		return (IProperty) getSubmodelElement(TYPEOFEMAILADDRESSID);
	}
	
	/**
	 * sets characterization of a public key according to its encryption process
	 * @param key {@link MultiLanguageProperty}
	 */
	public void setTypeOfPublicKey(MultiLanguageProperty key) {
		addSubModelElement(key);
	}
	
	/**
	 * sets characterization of a public key according to its encryption process
	 * @param key {@link LangString}
	 */
	public void setTypeOfPublicKey(LangString key) {
		MultiLanguageProperty typeOfPublicKey = new MultiLanguageProperty(TYPEOFPUBLICKEYID);
		typeOfPublicKey.setSemanticID(new Reference(new Key(KeyElements.CONCEPTDESCRIPTION, false, "0173-1#02-AAO201#002", IdentifierType.IRDI)));
		typeOfPublicKey.setValue(new LangStrings(key));
		setTypeOfPublicKey(typeOfPublicKey);
	}
	
	/**
	 * gets characterization of a public key according to its encryption process
	 * @return
	 */
	public IMultiLanguageProperty getTypeOfPublicKey() {
		return (IMultiLanguageProperty) getSubmodelElement(TYPEOFPUBLICKEYID);
	}
}
