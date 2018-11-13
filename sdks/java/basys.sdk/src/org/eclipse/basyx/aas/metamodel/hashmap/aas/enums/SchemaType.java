package org.eclipse.basyx.aas.metamodel.hashmap.aas.enums;

public enum SchemaType {

	None(0),
	XSD(1),
	RDFS(2),
	JSchema(3);
	
	private int id;

	SchemaType(int id) {
        this.id = id;
    }

    public int getId() { 
        return id;
    }
}
