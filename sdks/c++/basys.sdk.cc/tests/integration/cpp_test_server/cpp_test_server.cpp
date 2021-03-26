#include <BaSyx/server/TCPServer.h>
#include <BaSyx/vab/provider/hashmap/VABHashmapProvider.h>
#include <vab/stub/elements/SimpleVABElement.h>

using namespace basyx;

class IntegrationTestModelProvider : public vab::provider::VABModelProvider {
public:
    void Reset()
    {
        this->elements = tests::support::make_simple_vab_element();
    }

    virtual basyx::object invokeOperation(const std::string& path, basyx::object parameters) override
    {
        log.info("[Integration] reset called");
        if (path == "reset") {
            Reset();
            return basyx::object::make_null();
        };

        return VABModelProvider::invokeOperation(path, parameters);
    };
};

int main(int argc, char* argv[])
{
    int port = 6000;

    if (argc > 1)
        port = std::atoi(argv[1]);

    IntegrationTestModelProvider modelProvider;
    modelProvider.Reset();
	server::TCPServer<IntegrationTestModelProvider> tcpServer { &modelProvider, port };

    while (true) {
        tcpServer.update();
    };

    return 0;
};
