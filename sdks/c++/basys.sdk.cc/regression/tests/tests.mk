EXE_NAME ?= all_test

TESTS := 	aas/tests_aas.mk \
			basysid/tests_basysid.mk \
			bref/tests_bref.mk \
			ielement/tests_ielement.mk \
			json/tests_json.mk \
			parameter/tests_parameter.mk \
			provider/cxx/tests_provider_cxx.mk \
			provider/json/tests_provider_json.mk \
			submodels/tests_parameter.mk \
			typertti/tests_typertti.mk

# automatic processing 
_MKFILE_PATH := $(subst $(CURDIR)/,,$(dir $(abspath $(lastword $(MAKEFILE_LIST)))))
_INCLUDES := $(_MKFILE_PATH)/test_support.mk $(addprefix $(_MKFILE_PATH), $(TESTS))

SRC += $(addprefix $(_MKFILE_PATH), $(MODULE_SRC))
include $(_INCLUDES)