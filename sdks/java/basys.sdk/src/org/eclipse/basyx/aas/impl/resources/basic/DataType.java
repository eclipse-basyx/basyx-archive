package org.eclipse.basyx.aas.impl.resources.basic;


/**
 * Defines the permitted data type for properties
 * 
 * @author schoeffler
 *
 */
public enum DataType {
    REFERENCE,           // IElement reference
    OBJECT,              // Serializable object
    BOOLEAN,             // Primitive: boolean
    INTEGER,             // Primitive: integer
    FLOAT,               // Primitive: float
    DOUBLE,              // Primitive: double
    CHARACTER,           // Primitive: character
    STRING,              // Primitive: string
    VOID,                // Primitive: void
    COLLECTION,          // Primitive: collection
    MAP;                 // Primitive: map
}
