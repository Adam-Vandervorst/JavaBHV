g++ -c -fPIC -I/usr/lib/jvm/java-17-openjdk/include -I/usr/lib/jvm/java-17-openjdk/include/linux BHV.cpp -o BHV.o \
  -D DIMENSION=8192 -O3 -std=c++20 -march=native -Wall -Wpedantic -Wextra -g -ffast-math
g++ -shared -fPIC -o libBHV.so BHV.o -lc
