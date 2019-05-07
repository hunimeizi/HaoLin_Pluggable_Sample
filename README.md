# HaoLin_Pluggable_Sample -- 插件化

 ## 什么是插件化

- 每个组件业务就是一个独立的APK，然后通过主app动态部署业务组件APK


## 插件化的好处

    ① 业务组件解耦，能够实现业务组件热插拔

    ② 更改产品迭代模式，可分为主app以及次业务app

    ③ 改善产品更新过程，可以再不影响产品业务的情况下实现组件更新以及bug修复

### 插件化需要的思想

- 主app被系统安装调用，这个过程由系统调用，而插件apk并非被系统调用，简言之，需要将插件apk看作一个非apk文件，
    只是一个结构略显复杂的文件而已，调用插件即用某种方式打开这个文件


### 插件化步骤

- 分析主APP

    ① 主APP打包完成后，会形成dex,image,xml资源

    ② Dex靠PathClassLoader加载

    ③ 图片以及xml资源靠Resource加载

## 建议

- 代码实现

     ① 创建DexClassLoader加载代码

     ② 创建Resource 加载文件

     ③ 管理插件activity的生命周期


  ```java

     package com.haolin.pluginlibrary;

     import android.annotation.SuppressLint;
     import android.content.Context;
     import android.content.pm.PackageInfo;
     import android.content.pm.PackageManager;
     import android.content.res.AssetManager;
     import android.content.res.Resources;

     import java.io.File;
     import java.lang.reflect.Method;

     import dalvik.system.DexClassLoader;

     /**
      * 作者：haoLin_Lee on 2019/05/07 16:00
      * 邮箱：Lhaolin0304@sina.com
      * class:
      */
     public class PluginManager {

         private static PluginManager instance = new PluginManager();

         public static PluginManager getInstance() {
             return instance;
         }

         private PluginManager() {
         }

         private Context context;
         private PluginAPk pluginAPk;

         public PluginAPk getPluginAPk() {
             return pluginAPk;
         }

         public void init(Context context) {
             this.context = context;
         }

         // apk下载到本地后 进行加载
         public void loadAPk(String apkPath) {
             PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(apkPath,
                     PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
             if (packageInfo == null) return;
             DexClassLoader dexClassLoader = createDexClassLoader(apkPath);
             AssetManager assetManager = createAssetManager(apkPath);
             Resources resources = createResources(assetManager);
             pluginAPk = new PluginAPk(packageInfo, resources, dexClassLoader);
         }


         private DexClassLoader createDexClassLoader(String apkPath) {
             File file = context.getDir("dex", Context.MODE_PRIVATE);
             return new DexClassLoader(apkPath, file.getAbsolutePath(), null, context.getClassLoader());
         }

         @SuppressLint("PrivateApi")
         private AssetManager createAssetManager(String apkPath) {
             try {
                 AssetManager assetManager = AssetManager.class.newInstance();
                 Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
                 method.invoke(assetManager, apkPath);
                 return assetManager;
             } catch (Exception e) {
                 e.printStackTrace();
             }
             return null;
         }


         private Resources createResources(AssetManager assetManager) {
             Resources resources = context.getResources();
             return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
         }
     }

  ```
  - 以show()和hide()方式控制Fragment显示隐藏，别忘了重写onHiddenChanged方法，如下
  
  ```java
       @Override
       public void onHiddenChanged(boolean hidden) {
           super.onHiddenChanged(hidden);
           if (!hidden && mImmersionBar != null)
              mImmersionBar.init();
       }
    ```
