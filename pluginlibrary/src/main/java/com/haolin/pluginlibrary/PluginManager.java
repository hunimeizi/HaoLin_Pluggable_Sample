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
