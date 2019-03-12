TARGET_NAME ?= tests_typertti

MODULE_SRC := test_array.cc test_null.cc test_primitives.cc test_string.cc
MODULE_INC_DIR += .

# automatic processing
_MKFILE_PATH := $(subst $(CURDIR)/,,$(dir $(abspath $(lastword $(MAKEFILE_LIST)))))
SRC += $(addprefix $(_MKFILE_PATH), $(MODULE_SRC))
INC_DIR += $(addprefix $(_MKFILE_PATH), $(MODULE_INC_DIR))

include $(_MKFILE_PATH)/../test_support.mk