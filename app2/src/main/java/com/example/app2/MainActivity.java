package com.example.app2;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app2.listener.UIModeListener;
import com.example.app2.manager.UIModeManager;
import com.example.app2.utils.LayoutUtils;

public class MainActivity extends AppCompatActivity implements UIModeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UIModeManager.getInstance().registerUIModeListener(this);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 100; i++) {
//                    try {
//                        Thread.sleep(1000);
//                        int finalI = i;
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                UIModeManager.getInstance().broadCastUiModeChanged((finalI % 2 == 0) ? true : false);
//                            }
//                        });
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
        LayoutUtils.updateResUI(MainApplication.instance, R.layout.activity_main, getWindow().getDecorView(),2);

    }

    @Override
    public void uiModeChanged(boolean isLight) {
        Log.e(MainActivity.class.getSimpleName() + "------->", "uiModeChanged: isLight=" + isLight);
        LayoutUtils.updateResUI(MainApplication.instance, R.layout.activity_main, getWindow().getDecorView(), isLight ? 0 : 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UIModeManager.getInstance().unRegisterUIModeListener(this);
    }
}