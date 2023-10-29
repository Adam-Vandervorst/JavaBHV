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

JNIEXPORT jlong JNICALL Java_BHV_active(JNIEnv *env, jclass, jbyteArray ja) {
    word_t *a = (word_t *) env->GetPrimitiveArrayCritical(ja, nullptr);
    uint32_t count = bhv::active(a);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
    return count;
}

JNIEXPORT void JNICALL Java_BHV_orInto(JNIEnv *env, jclass, jbyteArray ja, jbyteArray jb, jbyteArray jc) {
    word_t *a = (word_t *)env->GetPrimitiveArrayCritical(ja, nullptr);
    word_t *b = (word_t *)env->GetPrimitiveArrayCritical(jb, nullptr);
    word_t *c = (word_t *)env->GetPrimitiveArrayCritical(jc, nullptr);
    bhv::or_into(a, b, c);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
    env->ReleasePrimitiveArrayCritical(jb, b, 0);
    env->ReleasePrimitiveArrayCritical(jc, c, 0);
}

JNIEXPORT void JNICALL Java_BHV_andInto(JNIEnv *env, jclass, jbyteArray ja, jbyteArray jb, jbyteArray jc) {
    word_t *a = (word_t *)env->GetPrimitiveArrayCritical(ja, nullptr);
    word_t *b = (word_t *)env->GetPrimitiveArrayCritical(jb, nullptr);
    word_t *c = (word_t *)env->GetPrimitiveArrayCritical(jc, nullptr);
    bhv::and_into(a, b, c);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
    env->ReleasePrimitiveArrayCritical(jb, b, 0);
    env->ReleasePrimitiveArrayCritical(jc, c, 0);
}

JNIEXPORT void JNICALL Java_BHV_xorInto(JNIEnv *env, jclass, jbyteArray ja, jbyteArray jb, jbyteArray jc) {
    word_t *a = (word_t *)env->GetPrimitiveArrayCritical(ja, nullptr);
    word_t *b = (word_t *)env->GetPrimitiveArrayCritical(jb, nullptr);
    word_t *c = (word_t *)env->GetPrimitiveArrayCritical(jc, nullptr);
    bhv::xor_into(a, b, c);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
    env->ReleasePrimitiveArrayCritical(jb, b, 0);
    env->ReleasePrimitiveArrayCritical(jc, c, 0);
}

JNIEXPORT void JNICALL Java_BHV_majorityInto(JNIEnv *env, jclass, jobjectArray jxs, jbyteArray jy) {
    word_t *y = (word_t *)env->GetPrimitiveArrayCritical(jy, nullptr);
    size_t n_vectors = env->GetArrayLength(jxs);
    size_t even = 1 - n_vectors % 2;

    word_t **vs = (word_t **) malloc((n_vectors + even) * sizeof(word_t *));

    for (size_t i = 0; i < n_vectors; ++i) {
        jobject v_i = env->GetObjectArrayElement(jxs, i);
        vs[i] = (word_t *)env->GetPrimitiveArrayCritical((jarray)v_i, nullptr);
    }

    if (even) vs[n_vectors] = bhv::rand();

    bhv::true_majority_into(vs, n_vectors + even, y);
}

JNIEXPORT jlong JNICALL Java_BHV_hamming(JNIEnv *env, jclass, jbyteArray ja, jbyteArray jb) {
    word_t *a = (word_t *)env->GetPrimitiveArrayCritical(ja, nullptr);
    word_t *b = (word_t *)env->GetPrimitiveArrayCritical(jb, nullptr);
    jlong count = bhv::hamming(a, b);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
    env->ReleasePrimitiveArrayCritical(jb, b, 0);
    return count;
}

JNIEXPORT void JNICALL Java_BHV_permuteInto(JNIEnv *env, jclass, jbyteArray ja, jlong perm, jbyteArray jb) {
    word_t *a = (word_t *)env->GetPrimitiveArrayCritical(ja, nullptr);
    word_t *b = (word_t *)env->GetPrimitiveArrayCritical(jb, nullptr);
    bhv::permute_into(a, perm, b);
    env->ReleasePrimitiveArrayCritical(ja, a, 0);
    env->ReleasePrimitiveArrayCritical(jb, b, 0);
}

}