cmake_minimum_required(VERSION 3.27)
project(bhv)

find_package(JNI REQUIRED)

set(SOURCE_FILES BHV.cpp)

add_executable(bhv ${SOURCE_FILES})
target_link_libraries(bhv PRIVATE JNI::JNI)
