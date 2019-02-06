#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_anad_mobile_post_Utils_Constants_getNativeApiKey(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env,
                                "efba826b-c6c9-44cb-9a6b-d23b2add24d6f9507580-2304-4bb4-81f5-c6295acccb11");
}

JNIEXPORT jstring JNICALL
Java_com_anad_mobile_post_Utils_Constants_getNativeKey(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env,
                                "57448329-b36c-4d72-a474-c2e9ded8573257448329-b36c-4d72-a474-c2e9ded85732");
}

JNIEXPORT jstring JNICALL
Java_com_anad_mobile_post_Utils_Util_getNativeKey(JNIEnv *env, jobject instance) {

    return (*env)->NewStringUTF(env,
                                "57448329-b36c-4d72-a474-c2e9ded8573257448329-b36c-4d72-a474-c2e9ded85732");
}

JNIEXPORT jstring JNICALL
Java_com_anad_mobile_post_Utils_Constants_getMapServerAddress(JNIEnv *env,jobject instance){
//    return (*env)->NewStringUTF(env,"http://130.185.78.110:9897/geoserver/ows?service=wms&version=1.1.1&request=GetCapabilities");
    return (*env)->NewStringUTF(env,"http://91.207.138.90:9897/geoserver/ows?service=wms&version=1.1.1&request=GetCapabilities");//for post application
}