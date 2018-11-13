MKDIR = -@mkdir $(subst /,\,$(1))
RM = -rmdir $(subst /,\,$(1)) /S /Q ||:
PLATFORM_EXTENSION = .exe