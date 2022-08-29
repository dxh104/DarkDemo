package com.example.darkdemo;

import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.darkdemo.manager.UIModeManager;

/**
 * @author XHD
 * Date 2022/08/26
 * Description:
 */
public class MainApplication extends Application {
    public static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int currentNightMode = newConfig.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                log("onConfigurationChanged: uiMode=白天模式");
                UIModeManager.getInstance().broadCastUiModeChanged(true);
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                log("onConfigurationChanged: uiMode=黑夜模式");
                UIModeManager.getInstance().broadCastUiModeChanged(false);
                break;
        }
    }

    private void log(String msg) {
        Log.e(MainApplication.class.getSimpleName() + "-------->", msg);
    }
}
