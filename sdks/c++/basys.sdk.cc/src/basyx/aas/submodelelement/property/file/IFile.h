/*
 * IFile.h
 *
 *      Author: wendel
 */ 

#ifndef BASYX_METAMODEL_IFile_H_
#define BASYX_METAMODEL_IFile_H_


#include "PathType.h"
#include "MimeType.h"

class IFile
{
public:
	

	virtual ~IFile() = default;

	virtual void setValue(PathType value) = 0;
	virtual PathType getValue() = 0;

	virtual void setMimeType(MimeType mimeType) = 0;
	virtual MimeType getMimeType() = 0;
};

#endif

