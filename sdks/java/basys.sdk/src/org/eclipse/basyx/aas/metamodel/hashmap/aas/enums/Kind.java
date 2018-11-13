package org.eclipse.basyx.aas.metamodel.hashmap.aas.enums;

public enum Kind {

	Type(0),
    Instance(1);
	
	private int id;

	Kind(int id) {
        this.id = id;
    }

    public int getId() { 
        return id;
    }
}
