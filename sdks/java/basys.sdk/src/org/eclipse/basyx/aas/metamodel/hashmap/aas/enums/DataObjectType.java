package org.eclipse.basyx.aas.metamodel.hashmap.aas.enums;

public enum DataObjectType {
	
    None(-1),
    Null(0),
    String(1),
	Bool(2),
    Float(3),
    Double(4),
    Number(5),
    UInt8(6),
    UInt16(7),
    UInt32(8),
    UInt64(9),
    Int8(10),
    Int16(11),
    Int32(12),
    Int64(13),
    Object(14),
    Void(15),
    Decimal(16),
    
    DateTime(100),
    Uri(101),
    Guid(102),
    
    Map(103),
    Collection(104),
    
    Reference(200),

    Property(300),

    AtomicDataProperty(310),
    PropertySingleValued(311),
    BlobProperty(312),
    FileProperty(313),
    ReferenceProperty(314),

    ComplexDataProperty(320),
    PropertyCollection(321),
    PropertyContainer(322),
    CompositeProperty(323);
    
    
	private int id;

	DataObjectType(int id) {
        this.id = id;
    }

    public int getId() { 
        return id;
    }
}
