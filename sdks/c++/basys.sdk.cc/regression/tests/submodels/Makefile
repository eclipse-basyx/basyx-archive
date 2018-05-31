


all: tests


tests: test1


test1:
	gcc test_devicestatussm.cc DeviceStatusSubModel.cc ../../../src/types/BObject.cc  -std=gnu++11  -o test_devicestatussm.exe -I../../../src -I../.. -I../../support -lstdc++
	./test_devicestatussm.exe


