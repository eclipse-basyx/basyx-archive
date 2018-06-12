


all: tests


tests: test1


test1:
	gcc test_deviceaas.cc DeviceAAS.cc ../submodels/DeviceStatusSubModel.cc ../../../src/types/BObject.cc  -std=gnu++11  -o test_deviceaas.exe -I../../../src -I../.. -I../../support -lstdc++ -W -Wall
	./test_deviceaas.exe


