#include <jni.h>
#include "CBHV/core.h"

extern "C" {

JNIEXPORT void JNICALL Java_BHV_randInto(JNIEnv *env, jclass, jbyteArray ja) {
    word_t *a = (word_t *) env->GetPrimitiveArrayCritical(ja, nullptr);
    bhv::rand_into(a);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
}

JNIEXPORT void JNICALL Java_BHV_randomInto(JNIEnv *env, jclass, jbyteArray ja, jfloat jp) {
    word_t *a = (word_t *) env->GetPrimitiveArrayCritical(ja, nullptr);
    bhv::random_into(a, jp);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
}

JNIEXPORT jlong JNICALL Java_BHV_active(JNIEnv * env, jobject, jbyteArray ja) {
    word_t *a = (word_t *) env->GetPrimitiveArrayCritical(ja, nullptr);
    uint32_t count = bhv::active(a);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
    return count;
}

JNIEXPORT void JNICALL Java_BHV_orInto(JNIEnv * env, jobject, jbyteArray ja, jbyteArray jb, jbyteArray jc) {
    word_t *a = (word_t *)env->GetPrimitiveArrayCritical(ja, nullptr);
    word_t *b = (word_t *)env->GetPrimitiveArrayCritical(jb, nullptr);
    word_t *c = (word_t *)env->GetPrimitiveArrayCritical(jc, nullptr);
    bhv::or_into(a, b, c);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
    env->ReleasePrimitiveArrayCritical(jb, b, 0);
    env->ReleasePrimitiveArrayCritical(jc, c, 0);
}

JNIEXPORT void JNICALL Java_BHV_andInto(JNIEnv * env, jobject, jbyteArray ja, jbyteArray jb, jbyteArray jc) {
    word_t *a = (word_t *)env->GetPrimitiveArrayCritical(ja, nullptr);
    word_t *b = (word_t *)env->GetPrimitiveArrayCritical(jb, nullptr);
    word_t *c = (word_t *)env->GetPrimitiveArrayCritical(jc, nullptr);
    bhv::and_into(a, b, c);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
    env->ReleasePrimitiveArrayCritical(jb, b, 0);
    env->ReleasePrimitiveArrayCritical(jc, c, 0);
}

JNIEXPORT void JNICALL Java_BHV_xorInto(JNIEnv * env, jobject, jbyteArray ja, jbyteArray jb, jbyteArray jc) {
    word_t *a = (word_t *)env->GetPrimitiveArrayCritical(ja, nullptr);
    word_t *b = (word_t *)env->GetPrimitiveArrayCritical(jb, nullptr);
    word_t *c = (word_t *)env->GetPrimitiveArrayCritical(jc, nullptr);
    bhv::xor_into(a, b, c);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
    env->ReleasePrimitiveArrayCritical(jb, b, 0);
    env->ReleasePrimitiveArrayCritical(jc, c, 0);
}
}
//
//JNIEXPORT jlong JNICALL Java_com_example_BHV_new(JNIEnv* env, jclass) {
//    word_t* data = bhv::empty();
//    return reinterpret_cast<jlong>(data);
//}
//
//JNIEXPORT void JNICALL Java_com_example_BHV_free(JNIEnv* env, jclass, jlong handle) {
//    word_t* data = reinterpret_cast<word_t*>(handle);
//    free(data);
//}
//
//JNIEXPORT jlong JNICALL Java_com_example_BHV_rand(JNIEnv* env, jclass, jlong handle) {
//    word_t* data = reinterpret_cast<word_t*>(handle);
//    bhv::rand_into(data);
//    return handle;
//}
//
//JNIEXPORT jlong JNICALL Java_com_example_BHV_random(JNIEnv* env, jclass, jlong handle, jfloat p) {
//    word_t* data = reinterpret_cast<word_t*>(handle);
//    bhv::random_into(data, p);
//    return handle;
//}
//
//JNIEXPORT jint JNICALL Java_com_example_BHV_hamming(JNIEnv* env, jclass, jlong handle1, jlong handle2) {
//    word_t* data1 = reinterpret_cast<word_t*>(handle1);
//    word_t* data2 = reinterpret_cast<word_t*>(handle2);
//    return bhv::hamming(data1, data2);
//}
//
//JNIEXPORT jlong JNICALL Java_com_example_BHV_majority(JNIEnv* env, jclass, jlongArray handles) {
//    // Assuming bhv::true_majority_into has a similar function signature
//    // Convert jlongArray to word_t**
//    // Do the majority operation
//    // Return the resulting handle
//}
//
//JNIEXPORT jlong JNICALL Java_com_example_BHV_xor(JNIEnv* env, jclass, jlong handle1, jlong handle2) {
//    word_t* data1 = reinterpret_cast<word_t*>(handle1);
//    word_t* data2 = reinterpret_cast<word_t*>(handle2);
//    word_t* result = bhv::empty();
//    bhv::xor_into(data1, data2, result);
//    return reinterpret_cast<jlong>(result);
//}
//
//JNIEXPORT jlong JNICALL Java_com_example_BHV_invert(JNIEnv* env, jclass, jlong handle) {
//    word_t* data = reinterpret_cast<word_t*>(handle);
//    word_t* result = bhv::empty();
//    bhv::invert_into(data, result);
//    return reinterpret_cast<jlong>(result);
//}
//
//JNIEXPORT jboolean JNICALL Java_com_example_BHV_eq(JNIEnv* env, jclass, jlong handle1, jlong handle2) {
//    word_t* data1 = reinterpret_cast<word_t*>(handle1);
//    word_t* data2 = reinterpret_cast<word_t*>(handle2);
//    return bhv::eq(data1, data2);
//}
//
//JNIEXPORT jlong JNICALL Java_com_example_BHV_permute(JNIEnv* env, jclass, jlong handle, jint perm) {
//    word_t* data = reinterpret_cast<word_t*>(handle);
//    word_t* result = bhv::empty();
//    bhv::permute_into(data, perm, result);
//    return reinterpret_cast<jlong>(result);
//}
//
//JNIEXPORT jlong JNICALL Java_com_example_BHV_rehash(JNIEnv* env, jclass, jlong handle) {
//    word_t* data = reinterpret_cast<word_t*>(handle);
//    word_t* result = bhv::empty();
//    bhv::rehash_into(data, result);
//    return reinterpret_cast<jlong>(result);
//}
//
//JNIEXPORT jlong JNICALL Java_com_example_BHV_swapHalves(JNIEnv* env, jclass, jlong handle) {
//    word_t* data = reinterpret_cast<word_t*>(handle);
//    word_t* result = bhv::empty();
//    bhv::swap_halves_into(data, result);
//    return reinterpret_cast<jlong>(result);
//}
//
//JNIEXPORT jbyteArray JNICALL Java_com_example_BHV_toBytes(JNIEnv* env, jclass, jlong handle) {
//    word_t* data = reinterpret_cast<word_t*>(handle);
//    jbyteArray bytes = env->NewByteArray(BYTES);
//    env->SetByteArrayRegion(bytes, 0, BYTES, reinterpret_cast<jbyte*>(data));
//    return bytes;
//}
//
//JNIEXPORT jlong JNICALL Java_com_example_BHV_fromBytes(JNIEnv* env, jclass, jbyteArray bytes) {
//    word_t* result = bhv::empty();
//    jbyte* elements = env->GetByteArrayElements(bytes, NULL);
//    std::memcpy(result, elements, BYTES);
//    env->ReleaseByteArrayElements(bytes, elements, 0);
//    return reinterpret_cast<jlong>(result);
//}
//
//}
//
