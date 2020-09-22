#include <BaSyx/submodel/map_v2/dataspecification/ValueList.h>


namespace basyx {
namespace submodel {
namespace map {

constexpr char ValueList::Path::Value[];
constexpr char ValueList::Path::ValueId[];

ValueList::ValueList()
  : vab::ElementMap(basyx::object::make_object_list())
{}

ValueList::ValueList(const std::vector<simple::ValueReferencePair> &list)
    : vab::ElementMap()
{}

void ValueList::addValueReferencePair(const simple::ValueReferencePair & valueRefPair)
{
  object obj = object::make_map();
  obj.insertKey(Path::Value, valueRefPair.getValue());
  obj.insertKey(Path::ValueId, map::Reference(valueRefPair.getValueId()).getMap());
  this->map.insert(obj);
}

std::vector<simple::ValueReferencePair> ValueList::getValueReferencePairs()
{
  std::vector<simple::ValueReferencePair> list;
  for (auto & pair_obj : this->map.Get<object::object_list_t&>())
  {
    std::string value = pair_obj.getProperty(Path::Value).GetStringContent();
    auto reference = simple::Reference(map::Reference(pair_obj.getProperty(Path::ValueId)));
    simple::ValueReferencePair pair(value, reference);
    list.push_back(pair);
  }
  return list;
}

}
}
}
