EXE_NAME ?= tests_json

MODULE_SRC := test_json_deserialize.cc test_json_serialize.cc 

# automatic processing
_MKFILE_PATH := $(subst $(CURDIR)/,,$(dir $(abspath $(lastword $(MAKEFILE_LIST)))))
SRC += $(addprefix $(_MKFILE_PATH), $(MODULE_SRC))

include $(_MKFILE_PATH)/../test_support.mk