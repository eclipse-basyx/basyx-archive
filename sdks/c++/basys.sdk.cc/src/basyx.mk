MODULE_SRC := types/BObject.cc json/JSONTools.cpp basysid/BaSysID.cpp

# automatic processing
_MKFILE_PATH := $(subst $(CURDIR)/,,$(dir $(abspath $(lastword $(MAKEFILE_LIST)))))
SRC += $(addprefix $(_MKFILE_PATH), $(MODULE_SRC))