package com.haolin.pluginlibrary;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * 作者：haoLin_Lee on 2019/05/07 16:43
 * 邮箱：Lhaolin0304@sina.com
 * class: 代理activity 管理activity的生命周期
 */
public class ProxyActivity extends Activity {

    private String mClassName;
    private PluginAPk pluginAPk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className");
        pluginAPk = PluginManager.getInstance().getPluginAPk();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (pluginAPk == null) {
            throw new RuntimeException("loading apk file first please！");
        }
        try {
            Class<?> clazz = pluginAPk.mDexClassLoader.loadClass(mClassName);
            Object object = clazz.newInstance();
            if (object instanceof IPlugin) {
                IPlugin iPlugin = (IPlugin) object;
                iPlugin.attach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM", IPlugin.FROM_EXTERNAL);
                iPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return pluginAPk != null ? pluginAPk.mResources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return pluginAPk != null ? pluginAPk.mAssetManager :super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return pluginAPk != null ? pluginAPk.mDexClassLoader :super.getClassLoader();
    }
}
