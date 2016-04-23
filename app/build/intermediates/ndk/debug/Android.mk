LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := appMylib
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	C:\Users\Xinzhe\AndroidStudioProjects\JNITestApp\app\src\main\jni\source_file.cpp \

LOCAL_C_INCLUDES += C:\Users\Xinzhe\AndroidStudioProjects\JNITestApp\app\src\main\jni
LOCAL_C_INCLUDES += C:\Users\Xinzhe\AndroidStudioProjects\JNITestApp\app\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
