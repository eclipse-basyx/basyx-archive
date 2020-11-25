#include <gtest/gtest.h>

#include <BaSyx/controlcomponent/headers/ControlComponent.h>
#include <BaSyx/controlcomponent/enumerations/ExecutionOrder.h>
#include "support/ControlComponentChangeListenerMock.hpp"

using namespace basyx::controlcomponent;

class ControlComponentTest: public ::testing::Test{
protected:
	std::unique_ptr<ControlComponent> control_component;
	std::shared_ptr<ControlComponentChangeListenerMock> change_listener, change_listener_2;
protected:
	void SetUp() override
	{
	  control_component = util::make_unique<ControlComponent>();
	  change_listener = std::make_shared<ControlComponentChangeListenerMock>();
    change_listener_2 = std::make_shared<ControlComponentChangeListenerMock>(2);

	  control_component->addControlComponentChangeListener(change_listener);
    control_component->addControlComponentChangeListener(change_listener_2);
	}

	void TearDown() override
	{
	}
};

TEST_F(ControlComponentTest, TestListener)
{
  control_component->removeControlComponentChangeListener(change_listener);
  // getUniqueID should have been called twice (see SetUp() above)
  ASSERT_EQ(2, change_listener->call_counter_getUniqueID);

  //only teh second change listener should have been notified
  control_component->setLastOccupierID("");
  ASSERT_EQ(0, change_listener->call_counter_onLastOccupier);
  ASSERT_EQ(1, change_listener_2->call_counter_onLastOccupier);
}

TEST_F(ControlComponentTest, TestAddClearOrder)
{
  //intially the empty list should be empty
  auto list = control_component->getOrderList();
  ASSERT_FALSE(list.IsNull());
  ASSERT_TRUE(list.InstanceOf<basyx::object::list_t<std::string>>());

  //add some orders
  control_component->addOrder("order_1");
  control_component->addOrder("order_2");

  //check if they are in list
  ASSERT_EQ(list.size(), 2);

  //clear list
  control_component->clearOrder();
  ASSERT_EQ(list.size(), 0);
}

TEST_F(ControlComponentTest, TestPut)
{
  control_component->addControlComponentChangeListener(change_listener_2);

  control_component->put("key", "test");
  //key value pair should be in map
  ASSERT_EQ("test", control_component->getMap().getProperty("key").GetStringContent());
  //changeListeners onVariableChange should be called for each put
  ASSERT_EQ(1, change_listener->call_counter_onVariableChange);
  ASSERT_EQ(1, change_listener_2->call_counter_onVariableChange);
  change_listener->reset();
  change_listener_2->reset();

  //If key cmd and value is a valid ExecutionOrder, ExecutionState should have changed
  control_component->put("cmd", ExecutionOrder_::to_string(ExecutionOrder::reset));
  ASSERT_EQ(1, change_listener->call_counter_onChangedExecutionState);
  ASSERT_EQ(1, change_listener_2->call_counter_onChangedExecutionState);
  ASSERT_EQ(ExecutionState::resetting ,control_component->getExecutionState());
  change_listener->reset();
  change_listener_2->reset();

  //If key is localOverwrite occupier should be local
  control_component->put(ControlComponentConstants_::to_string(ControlComponentConstants::localOverwrite), "test");
  ASSERT_EQ(1, change_listener->call_counter_onNewOccupationState);
  ASSERT_EQ(1, change_listener->call_counter_onNewOccupier);
  ASSERT_EQ(ControlComponentConstants_::to_string(ControlComponentConstants::LOCAL), control_component->getOccupierID());
  ASSERT_EQ(OccupationState::local, control_component->getOccupationState());

  control_component->put(ControlComponentConstants_::to_string(ControlComponentConstants::localOverwriteFree), "test");
  ASSERT_EQ("", control_component->getOccupierID());
}

TEST_F(ControlComponentTest, TestOccupationState)
{
  OccupationState inital = control_component->getOccupationState();
  ASSERT_EQ(OccupationState::free, inital);

  control_component->setOccupationState(OccupationState::occupied);
  OccupationState next = control_component->getOccupationState();
  ASSERT_EQ(OccupationState::occupied, next);
  ASSERT_EQ(1, change_listener->call_counter_onNewOccupationState);
  ASSERT_EQ(1, change_listener_2->call_counter_onNewOccupationState);
}

TEST_F(ControlComponentTest, TestOccupierId)
{
  std::string initial = control_component->getOccupierID();
  ASSERT_EQ(initial, "");

  control_component->setOccupierID("Some occupier");
  std::string id = control_component->getOccupierID();
  ASSERT_EQ("Some occupier", id);
  ASSERT_EQ(1, change_listener->call_counter_onNewOccupier);
  ASSERT_EQ(1, change_listener_2->call_counter_onNewOccupier);
}

TEST_F(ControlComponentTest, TestLastOccupierId)
{
  std::string initial = control_component->getLastOccupierID();
  ASSERT_EQ(initial, "");

  control_component->setLastOccupierID("Some last occupier");
  std::string id = control_component->getLastOccupierID();
  ASSERT_EQ("Some last occupier", id);
  ASSERT_EQ(1, change_listener->call_counter_onLastOccupier);
  ASSERT_EQ(1, change_listener_2->call_counter_onLastOccupier);
}

TEST_F(ControlComponentTest, TestExecutionMode)
{
  ExecutionMode initial = control_component->getExecutionMode();
  ASSERT_EQ(initial, ExecutionMode::Auto);

  control_component->setExecutionMode(ExecutionMode::Reserved);
  ExecutionMode ex_mode = control_component->getExecutionMode();
  ASSERT_EQ(ex_mode, ExecutionMode::Reserved);
  ASSERT_EQ(1, change_listener->call_counter_onChangedExecutionMode);
  ASSERT_EQ(1, change_listener_2->call_counter_onChangedExecutionMode);
}

TEST_F(ControlComponentTest, TestExecutionState)
{
  ExecutionState initial = control_component->getExecutionState();
  ASSERT_EQ(initial, ExecutionState::idle);

  control_component->setExecutionState(ExecutionState::clearing);
  ExecutionState ex_state = control_component->getExecutionState();
  ASSERT_EQ(ex_state, ExecutionState::clearing);
  ASSERT_EQ(1, change_listener->call_counter_onChangedExecutionState);
  ASSERT_EQ(1, change_listener_2->call_counter_onChangedExecutionState);
}

TEST_F(ControlComponentTest, TestOperationMode)
{
  std::string initial = control_component->getOperationMode();
  ASSERT_EQ(initial, "");

  control_component->setOperationMode("Special operation");
  std::string op_mode = control_component->getOperationMode();
  ASSERT_EQ(op_mode, "Special operation");
  ASSERT_EQ(1, change_listener->call_counter_onChangedOperationMode);
  ASSERT_EQ(1, change_listener_2->call_counter_onChangedOperationMode);
}

TEST_F(ControlComponentTest, TestCommand)
{
  std::string initial = control_component->getCommand();
  ASSERT_EQ(initial, "");

  control_component->setCommand("Some command");
  std::string command = control_component->getCommand();
  ASSERT_EQ(command, "Some command");
}

TEST_F(ControlComponentTest, TestLocalOverwrite)
{
  std::string initial = control_component->getLocalOverwrite();
  ASSERT_EQ(initial, "");

  control_component->setLocalOverwrite("Some overwrite");
  std::string command = control_component->getLocalOverwrite();
  ASSERT_EQ(command, "Some overwrite");
}

TEST_F(ControlComponentTest, TestLocalOverwriteFree)
{
  std::string initial = control_component->getLocalOverwriteFree();
  ASSERT_EQ(initial, "");

  control_component->setLocalOverwriteFree("Some overwrite free");
  std::string command = control_component->getLocalOverwriteFree();
  ASSERT_EQ(command, "Some overwrite free");
}

TEST_F(ControlComponentTest, TestServiceMap)
{
  basyx::object service_map = control_component->getServiceOperationMap();

  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::semiauto)).invoke();
  ExecutionMode ex_mode = control_component->getExecutionMode();
  ASSERT_EQ(ex_mode, ExecutionMode::Semiauto);

  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::start)).invoke();
  ExecutionState ex_state = control_component->getExecutionState();
  ASSERT_EQ(ex_state, ExecutionState::starting);

  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::bstate)).invoke();
  std::string op_mode = control_component->getOperationMode();
  ASSERT_EQ(op_mode, "BSTATE");
}

TEST_F(ControlComponentTest, TestFreeControlComponent)
{
  basyx::object service_map = control_component->getServiceOperationMap();

  //set occupierID and last occupierID
  control_component->setOccupierID("It's me");
  control_component->setLastOccupierID("It's not anymore me");

  // Try to free the controlcomponent
  basyx::object who("It's me");
  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::free)).invoke(who);
  ASSERT_EQ(1, change_listener->call_counter_onNewOccupationState);
  ASSERT_EQ(1, change_listener_2->call_counter_onNewOccupationState);

  ASSERT_EQ("It's not anymore me", control_component->getOccupierID());
  OccupationState oc_state = control_component->getOccupationState();
  ASSERT_EQ(oc_state, OccupationState::occupied);

  // Try to free controlcomponent without permission
  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::free)).invoke(who);
  //should not have changed
  ASSERT_EQ("It's not anymore me", control_component->getOccupierID());
  ASSERT_EQ(2, change_listener->call_counter_onNewOccupier);
  ASSERT_EQ(2, change_listener_2->call_counter_onNewOccupier);

  basyx::object has_permission("It's not anymore me");
  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::free)).invoke(has_permission);
  // should be empty now
  ASSERT_EQ("", control_component->getOccupierID());
  // ... and free
  oc_state = control_component->getOccupationState();
  ASSERT_EQ(oc_state, OccupationState::free);
  ASSERT_EQ(3, change_listener->call_counter_onNewOccupier);
  ASSERT_EQ(3, change_listener_2->call_counter_onNewOccupier);
}

TEST_F(ControlComponentTest, TestOccupyControlComponent)
{
  basyx::object service_map = control_component->getServiceOperationMap();

  basyx::object who("me");
  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::occupy)).invoke(who);

  // should be occupied by "me"
  ASSERT_EQ(who.GetStringContent(), control_component->getOccupierID());
  ASSERT_EQ(OccupationState::occupied, control_component->getOccupationState());
  ASSERT_EQ(1, change_listener->call_counter_onNewOccupationState);
  ASSERT_EQ(1, change_listener_2->call_counter_onNewOccupationState);

  //If occupied, no one else should be able to occupy component
  basyx::object not_permitted("Someone else");
  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::occupy)).invoke();
  ASSERT_EQ(who.GetStringContent(), control_component->getOccupierID());
  ASSERT_EQ(OccupationState::occupied, control_component->getOccupationState());
  ASSERT_EQ(1, change_listener->call_counter_onNewOccupier);
  ASSERT_EQ(1, change_listener_2->call_counter_onNewOccupier);
}

TEST_F(ControlComponentTest, TestPriorityOccupation)
{
  basyx::object service_map = control_component->getServiceOperationMap();

  basyx::object who("me");
  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::occupy)).invoke(who);
  basyx::object priority_occupier("priority");
  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::priority)).invoke(priority_occupier);

  //Component should be in prioirity mode, last occupier "me" and actual occupier "priority"
  ASSERT_EQ(OccupationState::priority, control_component->getOccupationState());
  ASSERT_EQ("me", control_component->getLastOccupierID());
  ASSERT_EQ("priority", control_component->getOccupierID());
  ASSERT_EQ(2, change_listener->call_counter_onNewOccupier);
  ASSERT_EQ(2, change_listener_2->call_counter_onNewOccupier);

  //"me" should not be able to free component
  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::free)).invoke(who);
  ASSERT_EQ("priority", control_component->getOccupierID());
  ASSERT_EQ(2, change_listener->call_counter_onNewOccupationState);
  ASSERT_EQ(2, change_listener_2->call_counter_onNewOccupationState);

  //but "prioirity" should be
  service_map.getProperty(ControlComponentConstants_::to_string(ControlComponentConstants::free)).invoke(priority_occupier);
  ASSERT_EQ("me", control_component->getOccupierID());
  ASSERT_EQ(OccupationState::occupied, control_component->getOccupationState());
  ASSERT_EQ(3, change_listener->call_counter_onNewOccupier);
  ASSERT_EQ(3, change_listener_2->call_counter_onNewOccupier);
}

TEST_F(ControlComponentTest, TestFinishState)
{
  // test transitions according to packml state machine
  control_component->setExecutionState(ExecutionState::starting);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::execute, control_component->getExecutionState());
  ASSERT_EQ(2, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::execute);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::completing, control_component->getExecutionState());
  ASSERT_EQ(4, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::completing);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::complete, control_component->getExecutionState());
  ASSERT_EQ(6, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::resetting);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::idle, control_component->getExecutionState());
  ASSERT_EQ(8, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::holding);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::held, control_component->getExecutionState());
  ASSERT_EQ(10, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::unholding);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::execute, control_component->getExecutionState());
  ASSERT_EQ(12, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::suspending);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::suspended, control_component->getExecutionState());
  ASSERT_EQ(14, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::unsuspending);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::execute, control_component->getExecutionState());
  ASSERT_EQ(16, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::stopping);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::stopped, control_component->getExecutionState());
  ASSERT_EQ(18, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::stopped);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::idle, control_component->getExecutionState());
  ASSERT_EQ(20, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::aborting);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::aborted, control_component->getExecutionState());
  ASSERT_EQ(22, change_listener->call_counter_onChangedExecutionState);

  control_component->setExecutionState(ExecutionState::clearing);
  control_component->finishState();
  ASSERT_EQ(ExecutionState::stopped, control_component->getExecutionState());
  ASSERT_EQ(24, change_listener->call_counter_onChangedExecutionState);
}