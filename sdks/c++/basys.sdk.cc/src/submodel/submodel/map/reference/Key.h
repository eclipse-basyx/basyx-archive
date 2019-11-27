/*
 * Key.h
 *
 *      Author: wendel
 */

#ifndef AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_KEY_H_
#define AAS_IMPL_METAMODEL_HASHMAP_REFERENCE_KEY_H_


#include "submodel/api/reference/IKey.h"

#include "basyx/object.h"

namespace basyx {
namespace submodel {

class Key : public IKey
{
public:
	struct KeyType {
		static constexpr char IRDI[] = "IRDI";
	};

	struct KeyElements {
		static constexpr char ConceptDictionary[] = "ConceptDictionary";
	};
private:
	mutable basyx::object map;
public:
	virtual ~Key() = default;

	//constructor 
	Key(const std::string & type, const bool & local, const std::string & value, const std::string & idType);
	Key(basyx::object obj) : map(obj) { // ToDo: check if really map 
	};

	// Inherited via IKey
	virtual std::string getType() const override;
	virtual bool isLocal() const override;
	virtual std::string getValue() const override;
	virtual std::string getidType() const override;
  
	basyx::object getMap() const { return map; };

	// not inherited
	void setType(const std::string & type);
	void setLocal(const bool & local);
	void setValue(const std::string & value);
	void setIdType(const std::string & idType);
};

}
}

#endif
