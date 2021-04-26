#pragma once

#include <BaSyx/submodel/api_v2/aas/IAssetAdministrationShell.h>

#include <memory>

namespace basyx {
namespace tests {
namespace opcua {

class support {
public:
	static std::unique_ptr<submodel::api::IAssetAdministrationShell> support::buildTestAAS();
};

}
}
}