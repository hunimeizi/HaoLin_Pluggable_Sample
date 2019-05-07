package com.haolin.pluginlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * 作者：haoLin_Lee on 2019/05/07 16:18
 * 邮箱：Lhaolin0304@sina.com
 * class:
 */
public interface IPlugin {

    int FROM_INTERNAL = 0; //从内部来的
    int FROM_EXTERNAL = 1; //从外部来的

    void attach(Activity proxyActivity);

    void onCreate(Bundle saveInstanceState);

    void onStart();

    void onReStart();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();
}
