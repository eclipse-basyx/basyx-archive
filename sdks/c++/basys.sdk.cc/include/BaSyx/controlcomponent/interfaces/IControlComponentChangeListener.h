#ifndef BASYX_CONTROLCOMPONENT_ICONTROLCOMPONENTCHANGELISTENER_H
#define BASYX_CONTROLCOMPONENT_ICONTROLCOMPONENTCHANGELISTENER_H

#include <BaSyx/controlcomponent/enumerations/ExecutionMode.h>
#include <BaSyx/controlcomponent/enumerations/OccupationState.h>

namespace basyx {
namespace controlcomponent {

class IControlComponentChangeListener
{
public:
  virtual ~IControlComponentChangeListener() = 0;
  /**
 * Indicate change of a variable
 */
  virtual void onVariableChange(const std::string & varName, basyx::object newValue);


  /**
   * Indicate new occupier
   */
  virtual void onNewOccupier(const std::string & occupierId);


  /**
   * Indicate new occupation state
   */
  virtual void onNewOccupationState(const OccupationState & state);


  /**
   * Indicate a change of last occupier. This is probably not relevant for many sub classes, therefore this class
   * provides a default implementation. 
   */
  virtual void onLastOccupier(const std::string & lastOccupierId);


  /**
   * Indicate an execution mode change
   */
  virtual void onChangedExecutionMode(const ExecutionMode & newExecutionMode);


  /**
   * Indicate an execution state change
   */
  virtual void onChangedExecutionState(const ExecutionMode & newExecutionState);


  /**
   * Indicate an operation mode change
   */
  virtual void onChangedOperationMode(const std::string & newOperationMode);


  /**
   * Indicate an work state change
   */
  virtual void onChangedWorkState(const std::string & newWorkState);


  /**
   * Indicate an error state change
   */
  virtual void onChangedErrorState(const std::string & newWorkState);


  /**
   * Indicate an previous error state change. This is probably not relevant for many sub classes, therefore this class
   * provides a default implementation. 
   */
  virtual void onChangedPrevError(const std::string & newWorkState);
  
};

inline IControlComponentChangeListener::~IControlComponentChangeListener() = default;

}
}

#endif //BASYX_API_V2_SDK_ICONTROLCOMPONENTCHANGELISTENER_H
