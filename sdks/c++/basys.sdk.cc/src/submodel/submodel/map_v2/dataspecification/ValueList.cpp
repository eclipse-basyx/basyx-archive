#include <BaSyx/submodel/map_v2/dataspecification/ValueList.h>

namespace basyx {
namespace submodel {
namespace map {

ValueList::ValueList()
  : vab::ElementMap(basyx::object::make_object_list())
{}

ValueList::ValueList(const std::vector<simple::ValueReferencePair> &list)
    : vab::ElementMap()
{}

void ValueList::addValueReferencePair(const simple::ValueReferencePair & valueRefPair)
{
  object obj = object::make_map();
  obj.insertKey("value", valueRefPair.getValue());
  obj.insertKey("valueId", map::Reference(valueRefPair.getValueId()).getMap());
  this->map.insert(obj);
}

std::vector<simple::ValueReferencePair> ValueList::getValueReferencePairs()
{
  std::vector<simple::ValueReferencePair> list;
  for (auto & pair_obj : this->map.Get<object::object_list_t&>())
  {
    std::string value = pair_obj.getProperty("value").GetStringContent();
    auto reference = simple::Reference(map::Reference(pair_obj.getProperty("valueId")));
    simple::ValueReferencePair pair(value, reference);
    list.push_back(pair);
  }
  return list;
}

}
}
}
