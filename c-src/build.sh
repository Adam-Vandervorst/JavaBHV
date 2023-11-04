#!/usr/bin/env bash

# Determine the architecture
ARCH=$(uname)

compile_common() {
    # Compile BHV.cpp
    "$1" -c -fPIC -I"${JAVA_HOME}/include" "$2" BHV.cpp -o BHV.o \
      -D DIMENSION=8192 -O3 -std=c++20 -march=native -Wall -Wpedantic -Wextra -g -ffast-math

    # Compile TurboSHAKE.cpp
    "$1" -c -fPIC -I"${JAVA_HOME}/include" "$2" CBHV/TurboSHAKE_opt/TurboSHAKE.cpp -o TurboSHAKE.o \
      -D DIMENSION=8192 -O3 -std=c++20 -march=native -Wall -Wpedantic -Wextra -g -ffast-math

    # Compile KeccakP-1600-opt64.cpp
    "$1" -c -fPIC -I"${JAVA_HOME}/include" "$2" CBHV/TurboSHAKE_opt/KeccakP-1600-opt64.cpp -o KeccakP1600.o \
      -D DIMENSION=8192 -O3 -std=c++20 -march=native -Wall -Wpedantic -Wextra -g -ffast-math
}

if [ "$ARCH" = "Darwin" ]; then
    echo "Building for macOS"

    # Compile for macOS
    compile_common "g++-13" "-I${JAVA_HOME}/include/darwin"

    # Link the object files to create the shared library for macOS
    g++-13 -shared -fPIC -o libBHV.dylib BHV.o TurboSHAKE.o KeccakP1600.o -lc

elif [ "$ARCH" = "Linux" ]; then
    echo "Building for Linux"

    # Compile for Linux
    compile_common "g++" "-I${JAVA_HOME}/include/linux"

    # Link to create the shared library for Linux
    g++ -shared -fPIC -o libBHV.so BHV.o TurboSHAKE.o KeccakP1600.o -lc

else
    echo "Unsupported architecture: $ARCH"
    exit 1
fi
