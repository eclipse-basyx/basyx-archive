


all: tests

TOP := ../../../../..

SRC :=	test_BaSyxNative.cpp  \
		$(TOP)/src/basysid/BaSysID.cpp \
		$(TOP)/backends/protocols/connector/basyx/frame/BaSyxNativeFrameBuilder.cpp \
		$(TOP)/backends/protocols/connector/basyx/BaSyxNativeConnector.cpp \
		$(TOP)/backends/protocols/provider/basyx/frame/BaSyxNativeFrameProcessor.cpp \
		$(TOP)/src/json/JSONTools.cpp

tests:
	gcc $(SRC) -W -Wall -g  -std=gnu++11 -o test_BaSyxNative.exe -I$(TOP)/src -I$(TOP)/ -I$(TOP)/regression/support -lstdc++ -lws2_32
	./test_BaSyxNative.exe