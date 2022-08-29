package com.example.darkdemo.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.text.TextUtils;

import java.lang.reflect.Method;

/**
 * @author XHD
 * Date 2022/08/26
 * Description:
 */
public class SkinManager {
    private Resources mResources;
    /**
     * 获取APK资源
     * @param context 上下文
     * @param apkPath APK路径
     */
    public void loadSkinRes(Context context, String skinFilePath) {
        if (TextUtils.isEmpty(skinFilePath)) {
            return ;
        }
        try {
            AssetManager assetManager = createAssetManager(skinFilePath);
            mResources = createResources(context, assetManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private AssetManager createAssetManager(String skinFilePath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinFilePath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Resources createResources(Context context, AssetManager assetManager) {
        Resources superRes = context.getResources();
        Resources resources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
        return resources;
    }

    public Resources getSkinResource() {
        return mResources;
    }
}
