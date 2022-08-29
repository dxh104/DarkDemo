package com.example.app2.utils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import com.example.app2.R;
import com.example.app2.manager.SkinManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author XHD
 * Date 2022/08/25
 * Description:View上必须设置Id，src textColor background如果引用了资源文件会重新更新
 */
public class LayoutUtils {

    private static String[] pluginApkUrls = {"plugin_light.apk", "plugin_night.apk", "plugin_res1.apk"};
    private static final String TAG = LayoutUtils.class.getSimpleName() + "-------->";

    public static void updateResUI(Context context, int layoutId, View rootView, int uiMode) {
        XmlResourceParser layoutXmlResourceParser = context.getResources().getLayout(layoutId);
        Resources resources = getResources(context, copyAssetToFile(context, pluginApkUrls[uiMode], context.getFilesDir().getAbsolutePath(), "plugin.apk"));
        try {
            int event = layoutXmlResourceParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.i(TAG, "xml解析开始");
                        break;
                    case XmlPullParser.START_TAG:
                        String id = layoutXmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
                        if (checkAttr(id)) {
                            id = id.replace("@", "");
                            View view = rootView.findViewById(Integer.parseInt(id));
                            String background = layoutXmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "background");
                            String src = layoutXmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "src");
                            String textColor = layoutXmlResourceParser.getAttributeValue("http://schemas.android.com/apk/res/android", "textColor");
                            if (checkAttr(background)) {
                                background = background.replace("@", "");
                                Drawable drawable = resources.getDrawable(Integer.parseInt(background));
                                view.setBackground(drawable);
                                Log.e(TAG, "update background");
                            }
                            if (view instanceof ImageView && checkAttr(src)) {
                                src = src.replace("@", "");
                                Drawable drawable = resources.getDrawable(Integer.parseInt(src));
                                ((ImageView) view).setImageDrawable(drawable);
                                Log.e(TAG, "update src");
                            }
                            if (view instanceof TextView && checkAttr(textColor)) {
                                textColor = textColor.replace("@", "");
                                int color = resources.getColor(Integer.parseInt(textColor));
                                ((TextView) view).setTextColor(color);
                                Log.e(TAG, "update textColor");
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        Log.d(TAG, "Text:" + layoutXmlResourceParser.getText());
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                event = layoutXmlResourceParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkAttr(String attr) {
        if (!TextUtils.isEmpty(attr) && !attr.equals("null") && attr.contains("@")) {
            return true;
        }
        return false;
    }

    private static SkinManager mSkinManager = new SkinManager();


    private static Resources getResources(Context context, File apkFile) {
        mSkinManager.loadSkinRes(context, apkFile.getAbsolutePath());
        return mSkinManager.getSkinResource();
    }

    /**
     * 复制assets下的文件到本地文件
     * assetName assets内的文件名称
     * savepath 本地文件夹路径
     * savename 保存的文件名称需带后缀文件类型 如.pdf
     *
     * @throws IOException
     */
    public static File copyAssetToFile(Context context, String assetName, String savepath, String savename) {
        File dbf = null;
        try {
            InputStream myInput;
            File dir = new File(savepath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            dbf = new File(savepath + savename);
            if (dbf.exists()) {
                dbf.delete();
            }
            String outFileName = savepath + savename;
            OutputStream myOutput = new FileOutputStream(outFileName);
            myInput = context.getAssets().open(assetName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myInput.close();
            myOutput.close();
        } catch (IOException ioException) {

        }
        return dbf;
    }
}


