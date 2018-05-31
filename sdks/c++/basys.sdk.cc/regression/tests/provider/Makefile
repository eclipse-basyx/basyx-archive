


all: tests


tests:
	gcc test_cxxprovider.cc ../../../src/types/BObject.cc -std=gnu++11 -o test_cxxprovider.exe -I../../../src -I../../.. -I../../support -lstdc++ 
	./test_cxxprovider.exe
	
test3:
	gcc test_submodelprovider.cc ../submodels/DeviceStatusSubModel.cc ../../../src/types/BObject.cc -std=gnu++11 -o test_submodelprovider.exe -I../../../src -I../../.. -I../../support -lstdc++ 
	./test_submodelprovider.exe
	

