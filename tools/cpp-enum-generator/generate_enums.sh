#!/bin/sh

CPP_SDK_DIR=../../sdks/c++/basys.sdk.cc
TARGET_SRC_DIR=$CPP_SDK_DIR/src/submodel/submodel/enumerations
TARGET_HEADER_DIR=$CPP_SDK_DIR/include/BaSyx/submodel/enumerations/

ENUMS_DIR=enums_submodel
OUT_DIR=generated

TEMPLATE=template.txt

mkdir -p $OUT_DIR
mkdir -p $TARGET_SRC_DIR
mkdir -p $TARGET_HEADER_DIR

for f in $ENUMS_DIR/*; do
    python3 generator.py -t $TEMPLATE -o $OUT_DIR $f
done


mv $OUT_DIR/*.cpp $TARGET_SRC_DIR
mv $OUT_DIR/*.h $TARGET_HEADER_DIR


TARGET_SRC_DIR=$CPP_SDK_DIR/src/controlcomponent/enumerations
TARGET_HEADER_DIR=$CPP_SDK_DIR/include/BaSyx/controlcomponent/enumerations/

ENUMS_DIR=enums_control-components
OUT_DIR=generated

TEMPLATE=template_control-components.txt

mkdir -p $OUT_DIR
mkdir -p $TARGET_SRC_DIR
mkdir -p $TARGET_HEADER_DIR

for f in $ENUMS_DIR/*; do
    python3 generator.py -t $TEMPLATE -o $OUT_DIR $f
done


mv $OUT_DIR/*.cpp $TARGET_SRC_DIR
mv $OUT_DIR/*.h $TARGET_HEADER_DIR
