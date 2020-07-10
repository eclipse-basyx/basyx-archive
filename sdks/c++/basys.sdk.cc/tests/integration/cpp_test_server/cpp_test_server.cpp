#include <BaSyx/vab/provider/hashmap/VABHashmapProvider.h>
#include <BaSyx/server/TCPServer.h>
#include <vab/stub/elements/SimpleVABElement.h>

using namespace basyx;

int main(int argc, char * argv[])
{
	int port = 7001;

	if (argc > 1)
		port = std::atoi(argv[1]);

	vab::provider::VABModelProvider modelProvider{ tests::support::make_simple_vab_element() };
	server::TCPServer<vab::provider::VABModelProvider> tcpServer{ &modelProvider, port };


	while (true)
	{
		tcpServer.update();
	};

	return 0;
};
