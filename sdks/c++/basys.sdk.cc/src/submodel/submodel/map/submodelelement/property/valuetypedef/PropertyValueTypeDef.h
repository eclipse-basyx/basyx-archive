/*
 * PropertyValueTypeDef.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_SUBMODELELEMENT_PROPERTY_VALUETYPEDEF_PROPERTYVALUETYPEDEF_H_
#define AAS_IMPL_SUBMODELELEMENT_PROPERTY_VALUETYPEDEF_PROPERTYVALUETYPEDEF_H_

namespace basyx {
namespace aas {
namespace impl {
namespace metamodel {

namespace PropertyValueTypeDef {
  static constexpr char Double[] = "double";
  static constexpr char Float[] = "float";
  static constexpr char Integer[] = "int";
  static constexpr char String[] = "string";
  static constexpr char Boolean[] = "boolean";
  static constexpr char Map[] = "map";
  static constexpr char Collection[] = "collection";
  static constexpr char Void[] = "void";
  static constexpr char Null[] = "null";
  static constexpr char Container[] = "container";
}

namespace PropertyValueTypeIdentifier {
  static constexpr char TYPE_NAME[] = "name";
  static constexpr char TYPE_OBJECT[] = "dataObjectType";
}

}
}
}
}

#endif
