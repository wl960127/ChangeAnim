# ChangeAnim
Android深度探索(2),关于修改开机动画。然并卵！！！！


- 根目录下先 source build/envsetup.sh  (否则没有mmm指令)

- 编译App
  - 在新建项目的目录下输入make 项目名
  - 或者切换到Android源码根目录下执行下面任意一条命令即可：mmm packages/apps/项目名

- 直接推到已经刷过源码镜像的对应的分支的手机

  -  挂载手机磁盘，并设置可读写
  -  adb shell
  -  su 
  -  mount -o remount,rw /system  
  -  退出adb指令
  -  adb push 本地路径  手机app路径（/sdcard）
  -  再用 复制或者移动到 (/system/app)  
  - chmod 777 ****.apk (操作过程中，没有执行这一步导致，开机没有被安装)
  -  adb shell reboot  
