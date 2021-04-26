#include <BaSyx/opcua/aas/api/AASApiMetamodelHelpers.h>

namespace basyx
{
    namespace opcua
    {
        namespace aas
        {
            bool basyx::opcua::aas::AASApiMetamodelHelpers::IsProperty(ISubmodelElement * t_subModelElement)
            {
                if (t_subModelElement != nullptr)
                {
                    return (dynamic_cast<IProperty*>(t_subModelElement) != nullptr);
                }
                return false;
            }

            bool AASApiMetamodelHelpers::IsOperation(ISubmodelElement * t_subModelElement)
            {
                if (t_subModelElement != nullptr)
                {
                    return (dynamic_cast<IOperation*>(t_subModelElement) != nullptr);
                }
                return false;
            }

            inline std::string AASApiMetamodelHelpers::getPropertyValueType(ISubmodelElement * t_subModelElement)
            {
                if (t_subModelElement != nullptr)
                {
                    if (auto property = dynamic_cast<IProperty*>(t_subModelElement))
                    {
                        return property->getValueType();
                    }
                }
                return std::string();
            }

            std::vector<Key> AASApiMetamodelHelpers::getReferenceKeys(ISubmodelElement * t_subModelElement)
            {
                std::vector<Key> keys;

                if (t_subModelElement != nullptr)
                {
                    if (dynamic_cast<const IProperty*>(t_subModelElement)->getValueId() == nullptr)
                    {
                        return keys;
                    }
                    else
                    {
                        return dynamic_cast<const IProperty*>(t_subModelElement)->getValueId()->getKeys();
                    }
                }

                return keys;
            }

            inline bool AASApiMetamodelHelpers::IsOperationVariable(IOperationVariable * t_operationVariable)
            {
                if (t_operationVariable != nullptr)
                {
                    return (dynamic_cast<const IOperationVariable*>(t_operationVariable) != nullptr);
                }
                return false;
            }

            inline const std::type_info & AASApiMetamodelHelpers::getOperationTypeId(ISubmodelElement * t_subModelElement)
            {
                if (t_subModelElement == nullptr)
                {
                    return typeid(nullptr);
                }

                if (dynamic_cast<OperationVariable_t<bool>*>(t_subModelElement) != nullptr)
                {
                    return typeid(bool);
                }
                else if (dynamic_cast<OperationVariable_t<int32_t>*>(t_subModelElement) != nullptr)
                {
                    return typeid(int32_t);
                }
                else if (dynamic_cast<OperationVariable_t<float>*>(t_subModelElement) != nullptr)
                {
                    return typeid(float);
                }
                else if (dynamic_cast<OperationVariable_t<double>*>(t_subModelElement) != nullptr)
                {
                    return typeid(double);
                }
                else if (dynamic_cast<OperationVariable_t<std::string>*>(t_subModelElement) != nullptr)
                {
                    return typeid(std::string);
                }
                else
                {
                    return typeid(nullptr);
                }
            }
        }
    }
}
