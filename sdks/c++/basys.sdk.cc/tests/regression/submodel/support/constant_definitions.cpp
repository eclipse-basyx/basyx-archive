#include "support/AdministrativeInformationMock.hpp"
#include "support/IdentifierMock.hpp"
#include "support/ReferableMock.hpp"
#include "support/KeyMock.hpp"

namespace basyx {
namespace testing {

constexpr char AdministrativeInformationMock::Path::revision[];
constexpr char AdministrativeInformationMock::Path::version[];

constexpr char IdentifierMock::Path::id[];
constexpr char IdentifierMock::Path::idType[];

constexpr char ReferableMock::Path::id[];
constexpr char ReferableMock::Path::category[];
constexpr char ReferableMock::Path::description_lang[];
constexpr char ReferableMock::Path::description_text[];

constexpr char KeyMock::Path::type[];
constexpr char KeyMock::Path::value[];
constexpr char KeyMock::Path::idType[];

}
}