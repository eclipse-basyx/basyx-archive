#ifndef SUBMODEL_METAMODEL_SUBMODELEMENT_PROPERTY_H_
#define SUBMODEL_METAMODEL_SUBMODELEMENT_PROPERTY_H_

#include <BaSyx/shared/object.h>
#include <BaSyx/shared/types.h>

#include <BaSyx/submodel/api/submodelelement/property/file/IFile.h>
#include <BaSyx/submodel/api/submodelelement/property/ISingleProperty.h>
#include <BaSyx/submodel/map/submodelelement/SubmodelElement.h>
#include <BaSyx/submodel/map/submodelelement/DataElement.h>


namespace basyx {
namespace submodel {

class File :
	public virtual IFile,
	public virtual ModelType,
	public virtual DataElement,
	public virtual vab::ElementMap
{
public:
	virtual const std::string & getValue() const override;
	virtual const std::string & getMimeType() const override;

	void setValue(const std::string & bytes);
	void setMimeType(const std::string & mimeType);
};

}
}

#endif
