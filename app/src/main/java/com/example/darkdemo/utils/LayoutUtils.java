package com.example.darkdemo.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * @author XHD
 * Date 2022/08/25
 * Description:View上必须设置Id，src textColor background如果引用了资源文件会重新更新
 */
public class LayoutUtils {
    private static final String TAG = LayoutUtils.class.getSimpleName() + "-------->";

    public static void updateResUI(Context context, @LayoutRes int layoutId, View rootView) {
        XmlResourceParser xmlParser = context.getResources().getLayout(layoutId);
        try {
            int event = xmlParser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.i(TAG, "xml解析开始");
                        break;
                    case XmlPullParser.START_TAG:
                        String id = xmlParser.getAttributeValue("http://schemas.android.com/apk/res/android", "id");
                        if (checkAttr(id)) {
                            id = id.replace("@", "");
                            View view = rootView.findViewById(Integer.parseInt(id));
                            String background = xmlParser.getAttributeValue("http://schemas.android.com/apk/res/android", "background");
                            String src = xmlParser.getAttributeValue("http://schemas.android.com/apk/res/android", "src");
                            String textColor = xmlParser.getAttributeValue("http://schemas.android.com/apk/res/android", "textColor");
                            if (checkAttr(background)) {
                                background = background.replace("@", "");
                                view.setBackground(context.getResources().getDrawable(Integer.parseInt(background)));
                                Log.e(TAG, "update background");
                            }
                            if (view instanceof ImageView && checkAttr(src)) {
                                src = src.replace("@", "");
                                ((ImageView) view).setImageDrawable(context.getResources().getDrawable(Integer.parseInt(src)));
                                Log.e(TAG, "update src");
                            }
                            if (view instanceof TextView && checkAttr(textColor)) {
                                textColor = textColor.replace("@", "");
                                ((TextView) view).setTextColor(context.getResources().getColor(Integer.parseInt(textColor)));
                                Log.e(TAG, "update textColor");
                            }
                        }
                        break;
                    case XmlPullParser.TEXT:
                        Log.d(TAG, "Text:" + xmlParser.getText());
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                event = xmlParser.next();
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
}


