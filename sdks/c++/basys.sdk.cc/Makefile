BUILD_DIR := build/$(notdir $(MKFILE))/
OBJ_DIR := $(BUILD_DIR)objects/



# container variables
CFLAGS :=
LFLAGS :=
INC_DIR := src/ .
SRC :=


include $(MKFILE)
include src/basyx.mk

CPP_SRC := $(filter %.cpp, $(SRC))
CC_SRC := $(filter %.cc, $(SRC))

OBJS = $(addprefix $(OBJ_DIR), $(CPP_SRC:.cpp=.o) $(CC_SRC:.cc=.o))
INC = $(patsubst %,-I%,$(INC_DIR))

CC = gcc
LD = gcc
CFLAGS += -O0 -Wall -std=gnu++11 $(INC) -g
LFLAGS += -lstdc++ 

# include header dependencies
# if they do not exist, they will be created by the -MMD flag in the same folder as the corresponding .o file
DEPS := $(OBJS:.o=.d)
-include $(DEPS)

.PHONY: all
all: $(BUILD_DIR)$(EXE_NAME).exe 

$(BUILD_DIR)$(EXE_NAME).exe: $(OBJS)
	$(LD) -o $@ $^ $(LFLAGS)	

$(OBJ_DIR)%.o:: %.cpp
	-@mkdir $(subst /,\\, $(@D)) ||: 
	$(CC) -c $< $(CFLAGS) -MMD -MP  -o $@
	
$(OBJ_DIR)%.o:: %.cc
	-@mkdir $(subst /,\\, $(@D)) ||: 
	$(CC) -c $< $(CFLAGS) -MMD -MP  -o $@

.PHONY: clean
clean:
	-rmdir $(subst /,\\,$(OBJ_DIR)) /S /Q ||:
	del $(subst /,\\,$(BUILD_DIR))$(EXE_NAME).exe