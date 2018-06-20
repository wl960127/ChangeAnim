LOCAL_PATH:= $(call my-dir)
#清理缓存变量
include $(CLEAR_VARS)
#表示源文件编译路径 这个应用里面只有java源文件
LOCAL_SRC_FILES := $(call all-java-files-under, src)
#表示项目包名也就是模块名，在项目中唯一  指定apk的名字     比较重要
LOCAL_PACKAGE_NAME := ChangeAnim
# 指定平台签名                           		         比较重要
LOCAL_CERTIFICATE := platform
#指定混淆文件                                 	     比较重要
#LOCAL_PROGUARD_FLAG_FILES :=proguard.flags
# 使用该指令编译目标Apk.
include $(BUILD_PACKAGE)
