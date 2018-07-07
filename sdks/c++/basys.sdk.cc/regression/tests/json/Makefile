


all: testserialize testdeserialize


testserialize:
	gcc test_json_serialize.cc ../../../src/json/JSONTools.cpp -o test_json_serialize.exe  -std=gnu++11  -I../../../src -I../../.. -I../../support -lstdc++
	./test_json_serialize.exe

testdeserialize:
	gcc test_json_deserialize.cc ../../../src/json/JSONTools.cpp -o test_json_deserialize.exe  -std=gnu++11  -I../../../src -I../../.. -I../../support -lstdc++
	./test_json_deserialize.exe



