


all: tests


tests: test1 test2


test1:
	gcc test_basysid_1.cc ../../../src/basysid/BaSysID.cpp -o test_basysid_1.exe -I../../../src -I../.. -I../../support -lstdc++ -Wall -W -g
	./test_basysid_1.exe


test2:
	gcc test_basysid_2.cc ../../../src/basysid/BaSysID.cpp -o test_basysid_2.exe -I../../../src -I../.. -I../../support -lstdc++  -Wall -W -g
	./test_basysid_2.exe


