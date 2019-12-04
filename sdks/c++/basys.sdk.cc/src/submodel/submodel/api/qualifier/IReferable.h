/*
 * IReferable.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IREFERABLE_H_
#define BASYX_METAMODEL_IREFERABLE_H_

#include "submodel/api/reference/IReference.h"
#include "submodel/map/qualifier/Description.h"

#include <string>
#include <memory>

namespace basyx {
namespace submodel {

class IReferable
{
public:
	struct Path {
		static constexpr char IdShort[] = "idShort";
		static constexpr char Category[] = "category";
		static constexpr char Description[] = "description";
		static constexpr char Parent[] = "parent";
	};
public:
	virtual ~IReferable() = default;

	virtual std::string getIdShort() const = 0;
	virtual std::string getCategory() const = 0;
	virtual Description getDescription() const = 0;
	virtual std::shared_ptr<IReference> getParent() const = 0;
};


}
}

#endif
