package com.example.darkdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.darkdemo.listener.UIModeListener;
import com.example.darkdemo.manager.UIModeManager;
import com.example.darkdemo.utils.LayoutUtils;

public class MainActivity extends AppCompatActivity implements UIModeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UIModeManager.getInstance().registerUIModeListener(this);
    }

    @Override
    public void uiModeChanged(boolean isLight) {
        Log.e(MainActivity.class.getSimpleName() + "------->", "uiModeChanged: isLight=" + isLight);
        LayoutUtils.updateResUI(MainApplication.instance, R.layout.activity_main, getWindow().getDecorView());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UIModeManager.getInstance().unRegisterUIModeListener(this);
    }
}