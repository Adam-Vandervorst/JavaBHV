#!/usr/bin/env bash
# Compile BHV.cpp, TurboSHAKE.cpp, and KeccakP-1600-opt64.cpp
g++-13 -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin BHV.cpp -o BHV.o \
  -D DIMENSION=8192 -O3 -std=c++20 -march=native -Wall -Wpedantic -Wextra -g -ffast-math

g++-13 -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin CBHV/TurboSHAKE_opt/TurboSHAKE.cpp -o TurboSHAKE.o \
  -D DIMENSION=8192 -O3 -std=c++20 -march=native -Wall -Wpedantic -Wextra -g -ffast-math

g++-13 -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/darwin CBHV/TurboSHAKE_opt/KeccakP-1600-opt64.cpp -o KeccakP1600.o \
  -D DIMENSION=8192 -O3 -std=c++20 -march=native -Wall -Wpedantic -Wextra -g -ffast-math

# Link the object files to create the shared library
g++-13 -shared -fPIC -o libBHV.dylib BHV.o TurboSHAKE.o KeccakP1600.o -lc
