package org.eclipse.basyx.submodel.metamodel.api.submodelelement.entity;

/**
 * Enumeration for denoting whether an entity is a self-managed entity or a
 * comanaged entity.
 * 
 * @author schnicke
 *
 */
public enum EntityType {
	/**
	 * For co-managed entities there is no separate AAS. Co-managed entities need to
	 * be part of a self-managed entity.
	 */
	COMANAGEDENTITY("CoManagedEntity"),
	
	/**
	 * Self-Managed Entities have their own AAS but can be part of the bill of
	 * material of a composite self-managed entity. The asset of an I4.0 Component
	 * is a self-managed entity per definition.
	 */
	SELFMANAGEDENTITY("SelfManagedEntity");
	
	private String name;

	private EntityType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public static EntityType fromString(String str) {
		if (str == null || str.isEmpty()) {
			return null;
		}

		if (str.equals(EntityType.COMANAGEDENTITY.toString())) {
			return COMANAGEDENTITY;
		} else if (str.equals(EntityType.SELFMANAGEDENTITY.toString())) {
			return SELFMANAGEDENTITY;
		}
		throw new RuntimeException("Unknown EntityType: " + str);
	}
}
