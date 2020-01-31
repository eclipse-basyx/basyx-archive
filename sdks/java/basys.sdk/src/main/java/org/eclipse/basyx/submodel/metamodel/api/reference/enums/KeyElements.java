package org.eclipse.basyx.submodel.metamodel.api.reference.enums;

/**
 * KeyElements, ReferableElements, IdentifiableElements as defined in DAAS
 * document <br/>
 * <br />
 * Since there's no enum inheritance in Java, all enums are merged into a single
 * class
 * 
 * @author schnicke
 *
 */
public enum KeyElements {
	/**
	 * Enum values of KeyElements
	 */
	GLOBALREFERENCE("GlobalReference"),
	FRAGMENTREFERENCE("FragmentReference"),
	
	/**
	 * Enum values of ReferableElements
	 */
	ACCESSPERMISSIONRULE("AccessPermissionRule"),
	ANNOTATEDRELATIONSHIPELEMENT("AnnotatedRelationshipElement"),
	BASICEVENT("BasicEvent"),
	BLOB("Blob"),
	CAPABILITY("Capability"),
	CONCEPTDICTIONARY("ConceptDictionary"),
	DATAELEMENT("DataElement"),
	FILE("File"),
	ENTITY("Entity"),
	EVENT("Event"),
	MULTILANGUAGEPROPERTY("MultiLanguageProperty"),
	OPERATION("Operation"),
	PROPERTY("Property"),
	RANGE("Range"),
	REFERENCEELEMENT("ReferenceElement"),
	RELATIONSHIPELEMENT("RelationshipElement"),
	SUBMODELELEMENT("SubmodelElement"),
	SUBMODELELEMENTCOLLECTION("SubmodelElementCollection"),
	VIEW("View"),
	
	/**
	 * Enum values of IdentifiableElements
	 */
	ASSET("Asset"),
	ASSETADMINISTRATIONSHELL("AssetAdministrationShell"),
	CONCEPTDESCRIPTION("ConceptDescription"),
	SUBMODEL("Submodel");
	

	private String name;

	private KeyElements(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static KeyElements fromString(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}

		for (KeyElements elem : KeyElements.values()) {
			if (elem.toString().equals(str)) {
				return elem;
			}
		}

		throw new RuntimeException("Unknown KeyElements: " + str);
	}
}
