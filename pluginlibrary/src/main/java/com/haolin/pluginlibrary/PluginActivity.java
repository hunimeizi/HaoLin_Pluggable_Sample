package com.haolin.pluginlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * 作者：haoLin_Lee on 2019/05/07 16:28
 * 邮箱：Lhaolin0304@sina.com
 * class:
 */
public class PluginActivity extends Activity implements IPlugin {

    private int mFrom = FROM_INTERNAL;
    private Activity proxyActivity;

    @Override
    public void attach(Activity proxyActivity) {
        this.proxyActivity = proxyActivity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (saveInstanceState != null) {
            mFrom = saveInstanceState.getInt("FROM");
        }
        if (mFrom == FROM_INTERNAL) {
            super.onCreate(saveInstanceState);
            proxyActivity = this;
        }
    }

    @Override
    public void setContentView(View view) {
        if (mFrom == FROM_INTERNAL) {
            super.setContentView(view);
        }else {
            proxyActivity.setContentView(view);
        }
    }

    @Override
    public void onStart() {
        if (mFrom == FROM_INTERNAL) {
            super.onStart();
        }
    }

    @Override
    public void onReStart() {
        if (mFrom == FROM_INTERNAL) {
            super.onRestart();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFrom == FROM_INTERNAL) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResume() {
        if (mFrom == FROM_INTERNAL) {
            super.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mFrom == FROM_INTERNAL) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (mFrom == FROM_INTERNAL) {
            super.onStop();
        }
    }

    @Override
    public void onDestroy() {
        if (mFrom == FROM_INTERNAL) {
            super.onDestroy();
        }
    }
}
