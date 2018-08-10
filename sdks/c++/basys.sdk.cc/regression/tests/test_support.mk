MODULE_SRC := main.cpp ../support/gtest/gtest-all.cc

# automatic processing 
_MKFILE_PATH := $(subst $(CURDIR)/,,$(dir $(abspath $(lastword $(MAKEFILE_LIST)))))
SRC += $(addprefix $(_MKFILE_PATH), $(MODULE_SRC))