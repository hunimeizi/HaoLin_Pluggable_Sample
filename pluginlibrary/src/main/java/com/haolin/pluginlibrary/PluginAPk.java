package com.haolin.pluginlibrary;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * 作者：haoLin_Lee on 2019/05/07 15:56
 * 邮箱：Lhaolin0304@sina.com
 * class: 插件apk实体对象
 */
public class PluginAPk {
    //插件apk获取实体对象
    public PackageInfo mPackageInfo;
    public Resources mResources;
    public AssetManager mAssetManager;
    public DexClassLoader mDexClassLoader;

    public PluginAPk(PackageInfo mPackageInfo, Resources mResources, DexClassLoader mDexClassLoader) {
        this.mPackageInfo = mPackageInfo;
        this.mResources = mResources;
        this.mDexClassLoader = mDexClassLoader;
        mAssetManager = mResources.getAssets();
    }
}
