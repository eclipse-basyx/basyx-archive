


all: tests

TOP := ../../../../../../..

tests:
	gcc test_BaSyxNativeFrameBuilder.cpp  $(TOP)/src/basysid/BaSysID.cpp $(TOP)/backends/protocols/connector/basyx/frame/BaSyxNativeFrameBuilder.cpp -W -Wall -g  $(TOP)/src/json/JSONTools.cpp -std=gnu++11 -o test_BaSyxNativeFrameBuilder.exe -I$(TOP)/src -I$(TOP)/ -I$(TOP)/regression/support -lstdc++ 
	./test_BaSyxNativeFrameBuilder.exe