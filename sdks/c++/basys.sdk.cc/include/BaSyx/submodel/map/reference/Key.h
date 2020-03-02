/*
 * Key.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_KEY_H_
#define AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_KEY_H_


#include <BaSyx/submodel/api/reference/IKey.h>

#include <BaSyx/shared/object.h>

#include <BaSyx/vab/ElementMap.h>

namespace basyx {
namespace submodel {

class Key 
  : public IKey
  , public virtual vab::ElementMap
{
public:
	struct KeyType {
		static constexpr char IRDI[] = "IRDI";
	};

	struct KeyElements {
		static constexpr char ConceptDictionary[] = "ConceptDictionary";
	};
public:
	virtual ~Key() = default;

	//constructor 
	Key(const std::string & type, const bool & local, const std::string & value, const std::string & idType);
  Key(basyx::object obj);

	// Inherited via IKey
	virtual std::string getType() const override;
	virtual bool isLocal() const override;
	virtual std::string getValue() const override;
	virtual std::string getidType() const override;

	// not inherited
	void setType(const std::string & type);
	void setLocal(const bool & local);
	void setValue(const std::string & value);
	void setIdType(const std::string & idType);

  friend bool operator==(const IKey & left, const IKey & right);
};

}
}

#endif
