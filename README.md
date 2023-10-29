## Info

A port of https://github.com/Adam-Vandervorst/PyBHV/

Status: Only the very basics (RAND, XOR, PERMUTE, MAJORITY, HAMMING) are implemented at this point, adding the rest should be straightforward.

## Instructions
Assuming Linux, a modern GCC version, and Java 9 or newer:

```
export JAVA_HOME=/path/to/your/java/home 

git clone --recurse-submodules https://github.com/Adam-Vandervorst/JavaBHV.git
cd JavaBHV/c-src
bash build.sh
cd ..
bash run.sh
```
