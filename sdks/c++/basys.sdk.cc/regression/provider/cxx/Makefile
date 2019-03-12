


all: test1 test2


test1:
	gcc test_cxxprovider.cc ../../../../src/basysid/BaSysID.cpp ../../../../src/types/BObject.cc -std=gnu++11 -o test_cxxprovider.exe -I../../../../src -I../../../.. -I../../../support -lstdc++ -Wall -g
	./test_cxxprovider.exe
	
test2:
	gcc test_submodelprovider.cc ../../../../src/basysid/BaSysID.cpp ../../submodels/DeviceStatusSubModel.cc ../../../../src/types/BObject.cc -std=gnu++11 -o test_submodelprovider.exe -I../../../../src -I../../../.. -I../../../support -lstdc++ -Wall -g 
	./test_submodelprovider.exe
	

