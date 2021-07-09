#include <BaSyx/opcua/aas/api_v2/ApiMetamodelUtilities.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            bool ApiMetamodelUtilities::isPropertyType(const ISubmodelElement_t * t_submodelElement)
            {
                if (t_submodelElement != nullptr)
                    return (dynamic_cast<const basyx::submodel::api::IProperty*>(t_submodelElement) != nullptr);

                return false;
            }
        }
    }
}