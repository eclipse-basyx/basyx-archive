/*
 * IBlob.h
 *
 *      Author: wendel
 */

#ifndef BASYX_METAMODEL_IBlob_H_
#define BASYX_METAMODEL_IBlob_H_

#include <BaSyx/shared/types.h>

#include <BaSyx/submodel/api/submodelelement/IDataElement.h>

namespace basyx {
namespace submodel {


class IBlob
	: public IDataElement
{
public:
	struct Path
	{
		static constexpr char MIMEType[] = "mimeType";
		static constexpr char Value[] = "value";
		static constexpr char ModelType[] = "blob";
	};
public:
	virtual ~IBlob() = default;

	virtual const std::string & getValue() const = 0;
	virtual const std::string & getMimeType() const = 0;
};

}
}

#endif

