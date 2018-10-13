TARGET_NAME ?= tests_basysid

MODULE_SRC := test_basysid_1.cc test_basysid_2.cc

# automatic processing
_MKFILE_PATH := $(subst $(CURDIR)/,,$(dir $(abspath $(lastword $(MAKEFILE_LIST)))))
SRC += $(addprefix $(_MKFILE_PATH), $(MODULE_SRC))

include $(_MKFILE_PATH)/../test_support.mk