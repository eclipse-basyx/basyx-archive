package org.eclipse.basyx.aas.metamodel.hashmap.aas.enums;

public enum EntityTypes {
	
	None(0),
    Primitive(1),
    Object(2);

	private int id;

	EntityTypes(int id) {
        this.id = id;
    }

    public int getId() { 
        return id;
    }
}
