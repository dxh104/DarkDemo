package com.example.darkdemo.manager;

import com.example.darkdemo.listener.UIModeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XHD
 * Date 2022/08/26
 * Description:
 */
public class UIModeManager {
    private List<UIModeListener> uiModeListenerList = new ArrayList<>();
    private static UIModeManager instance = new UIModeManager();

    public static UIModeManager getInstance() {
        return instance;
    }

    public void registerUIModeListener(UIModeListener uiModeListener) {
        boolean hava = false;
        for (int i = 0; i < uiModeListenerList.size(); i++) {
            if (uiModeListenerList.get(i) == uiModeListener) {
                hava = true;
            }
        }
        if (!hava) {
            uiModeListenerList.add(uiModeListener);
        }
    }

    public void unRegisterUIModeListener(UIModeListener uiModeListener) {
        uiModeListenerList.remove(uiModeListener);
    }

    public void broadCastUiModeChanged(boolean isLight) {
        for (int i = 0; i < uiModeListenerList.size(); i++) {
            uiModeListenerList.get(i).uiModeChanged(isLight);
        }
    }

}
