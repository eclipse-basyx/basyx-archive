


all: tests

TOP := ../../../../../../..

tests:
	gcc test_BaSyxNativeFrameProcessor.cpp  $(TOP)/src/basysid/BaSysID.cpp $(TOP)/backends/protocols/provider/basyx/frame/BaSyxNativeFrameProcessor.cpp -W -Wall -g  $(TOP)/src/json/JSONTools.cpp -std=gnu++11 -o test_BaSyxNativeFrameProcessor.exe -I$(TOP)/src -I$(TOP)/ -I$(TOP)/regression/support -lstdc++ 
	./test_BaSyxNativeFrameProcessor.exe