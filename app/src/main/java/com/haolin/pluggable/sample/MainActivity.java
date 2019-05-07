package com.haolin.pluggable.sample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.haolin.pluginlibrary.PluginManager;
import com.haolin.pluginlibrary.ProxyActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PluginManager.getInstance().init(MainActivity.this);
    }

    /**
     * 加载APK包
     * @param view view
     */
    public void onLoadAPk(View view) {
        String apkPath = Utils.copyAssetAndWrite(MainActivity.this, "aa.apk");
        PluginManager.getInstance().loadAPk(apkPath);
    }

    /**
     * 跳转到插件apk的页面
     * @param view vi
     */
    public void onJump(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, ProxyActivity.class);
        intent.putExtra("className", "com.haolin.pluginapk.PluginApkActivity");
        startActivity(intent);
    }
}
