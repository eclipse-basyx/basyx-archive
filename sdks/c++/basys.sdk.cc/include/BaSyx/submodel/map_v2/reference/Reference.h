#ifndef BASYX_SUBMODEL_MAP_V2_REFERENCE_REFERENCE_H
#define BASYX_SUBMODEL_MAP_V2_REFERENCE_REFERENCE_H

#include <BaSyx/submodel/api_v2/reference/IReference.h>
#include <BaSyx/submodel/simple/reference/Key.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {
namespace map {

class Reference : 
	public api::IReference,
	public virtual vab::ElementMap
{
public:
  struct Path {
    static constexpr char IdType[] = "idType";
    static constexpr char Type[] = "type";
    static constexpr char Value[] = "value";
    static constexpr char Local[] = "local";
    static constexpr char Keys[] = "keys";
  };
public:
	Reference();
public:
	using vab::ElementMap::ElementMap;

	Reference(const api::IReference & other);
	Reference(const Reference & other) = default;
	Reference(Reference && other) noexcept = default;
  Reference(basyx::object & object);

	Reference & operator=(const api::IReference & other);
	Reference & operator=(const Reference & other) = default;
	Reference & operator=(Reference && other) noexcept = default;

	Reference(const simple::Key & key);
	Reference(const std::vector<simple::Key> & keys);

	virtual ~Reference() = default;
public:
	std::vector<simple::Key> getKeys() const override;
	void addKey(const simple::Key & key) override;

	virtual bool empty() const override;


  bool operator!=(const Reference & other) const;
  inline bool operator==(const Reference & other) const { return !(*this != other); };

  bool operator!=(const api::IReference & other) const;
  inline bool operator==(const api::IReference & other) const { return !(*this != other); };
public:
//	static Reference FromIdentifiable(const std::string & keyElementType, bool local, const IIdentifiable & identifiable);

private:
  static simple::Key keyMap_to_key(basyx::object &keyMap);
  static std::vector<simple::Key> keyMapList_to_keyList(basyx::object::object_list_t &keyMapList);
};

}
}
}

#endif /* BASYX_SUBMODEL_MAP_V2_REFERENCE_REFERENCE_H */
