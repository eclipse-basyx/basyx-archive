/*
 * IFile.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IFile_H_
#define BASYX_METAMODEL_IFile_H_

#include <string>

namespace basyx {
namespace submodel {

class IFile
{
public:
	struct Path
	{
		static constexpr char MIMEType[] = "mimeType";
		static constexpr char Value[] = "value";
		static constexpr char ModelType[] = "file";
	};
public:
	virtual ~IFile() = default;

	virtual const std::string & getValue() const = 0;
	virtual const std::string & getMimeType() const = 0;
};

}
}

#endif

