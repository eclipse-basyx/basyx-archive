#ifndef SUBMODEL_METAMODEL_SUBMODELEMENT_BLOB_H_
#define SUBMODEL_METAMODEL_SUBMODELEMENT_BLOB_H_

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/types.h>

#include <BaSyx/submodel/api/submodelelement/property/blob/IBlob.h>
#include <BaSyx/submodel/map/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map/submodelelement/DataElement.h>


namespace basyx {
namespace submodel {

class Blob :
	public virtual IBlob,
	public virtual ModelType,
	public virtual DataElement,
	public virtual vab::ElementMap
{
public:
	using Path = IBlob::Path;
public:
	Blob() : ModelType{Path::ModelType}
	{
		map.insertKey(Path::Value, basyx::object::make_null());
	};

	Blob(const IBlob & other) 
		: ModelType{Path::ModelType}
		, vab::ElementMap{}
	{
	}

	virtual const std::string & getValue() const override;
	virtual const std::string & getMimeType() const override;

	void setValue(const std::string & bytes);
	void setValue(const char * c, std::size_t length);

	void setMimeType(const std::string & mimeType);
};

}
}

#endif